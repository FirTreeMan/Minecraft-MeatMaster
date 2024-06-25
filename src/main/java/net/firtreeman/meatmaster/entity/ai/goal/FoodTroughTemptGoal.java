package net.firtreeman.meatmaster.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;

import net.firtreeman.meatmaster.block.entity.FoodTroughStationBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FoodTroughTemptGoal extends Goal {
   protected final Animal mob;
   private final double speedModifier;
   private double px;
   private double py;
   private double pz;
//   private double pRotX;
//   private double pRotY;
   @Nullable
   protected FoodTroughStationBlockEntity blockEntity;
   @Nullable
   protected BlockPos blockPos;
   private int calmDown;
   private boolean isRunning;
   private final Ingredient items;
   private final boolean canScare;

   public FoodTroughTemptGoal(Animal pMob, double pSpeedModifier, Ingredient pItems, boolean pCanScare) {
      this.mob = pMob;
      this.speedModifier = pSpeedModifier;
      this.items = pItems;
      this.canScare = pCanScare;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean canUse() {
      if (this.calmDown > 0) {
         --this.calmDown;
         return false;
      } else if (this.mob.getAge() != 0 || this.mob.isInLove() || this.mob.isBaby()) {
         return false;
      } else {
         this.blockEntity = null;
         FoodTroughStationBlockEntity[] blockEntities = BlockPos.withinManhattanStream(this.mob.blockPosition(), 18, 4, 36)
                 .map(this.mob.level()::getBlockEntity)
                 .filter(this::shouldFollow)
                 .map(s -> (FoodTroughStationBlockEntity) s)
                 .toArray(FoodTroughStationBlockEntity[]::new);

         if (blockEntities.length == 0) return false;

         double distance = Integer.MAX_VALUE;
         for (FoodTroughStationBlockEntity be: blockEntities) {
            double newDistance = this.mob.distanceToSqr(be.getBlockPos().getCenter());
            if (newDistance < distance) {
               distance = newDistance;
               this.blockEntity = be;
            }
         }
         this.blockPos = this.blockEntity.getBlockPos();
         return this.blockEntity != null;
      }
   }

   private boolean shouldFollow(BlockEntity blockEntity) {
      if (blockEntity instanceof FoodTroughStationBlockEntity foodTrough)
         return this.items.test(foodTrough.getStack());
      return false;
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean canContinueToUse() {
      if (this.mob.getAge() != 0) return false;

      if (this.canScare()) {
         if (this.mob.distanceToSqr(this.blockPos.getCenter()) < 36.0D) {
            if (this.blockPos.getCenter().distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
               return false;
            }

//            if (Math.abs((double)this.block.getXRot() - this.pRotX) > 5.0D || Math.abs((double)this.block.getYRot() - this.pRotY) > 5.0D) {
//               return false;
//            }
         } else {
            this.px = this.blockPos.getX();
            this.py = this.blockPos.getY();
            this.pz = this.blockPos.getZ();
         }

//         this.pRotX = (double)this.block.getXRot();
//         this.pRotY = (double)this.block.getYRot();
//         this.pRotX = 0;
//         this.pRotY = 0;
      }

      return this.canUse();
   }

   protected boolean canScare() {
      return this.canScare;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void start() {
      this.px = this.blockPos.getX();
      this.py = this.blockPos.getY();
      this.pz = this.blockPos.getZ();
      this.isRunning = true;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void stop() {
      this.blockEntity = null;
      this.blockPos = null;
      this.mob.getNavigation().stop();
      this.calmDown = reducedTickDelay(100);
      this.isRunning = false;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.mob.getLookControl().setLookAt(this.blockPos.getCenter());
      if (this.mob.distanceToSqr(this.blockPos.getCenter()) < 6.25D) {
         this.mob.getNavigation().stop();
         this.blockEntity.eat();
         this.mob.setInLove(null);
      } else {
         this.mob.getNavigation().moveTo(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ(), this.speedModifier);
      }

   }

   /**
    * @see #isRunning
    */
   public boolean isRunning() {
      return this.isRunning;
   }
}