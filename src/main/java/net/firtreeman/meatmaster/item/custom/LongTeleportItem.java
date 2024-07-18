package net.firtreeman.meatmaster.item.custom;

import net.firtreeman.meatmaster.config.ServerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class LongTeleportItem extends Item {
    public LongTeleportItem(Properties pProperties) {
        super(pProperties);
    }

    public static void longTeleport(Level pLevel, LivingEntity pLivingEntity, double effective_tp_range) {
        longTeleport(null, pLevel, pLivingEntity, effective_tp_range);
    }

    // ripped from Chorus Fruit code
    // set pStack = null when calling outside function
    public static ItemStack longTeleport(@Nullable ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, double effective_tp_range) {
        if (!pLevel.isClientSide) {
            double tp_range = effective_tp_range * 2;

            double d0 = pLivingEntity.getX();
            double d1 = pLivingEntity.getY();
            double d2 = pLivingEntity.getZ();

            for(int i = 0; i < 16; ++i) {
                double d3 = pLivingEntity.getX() + (pLivingEntity.getRandom().nextDouble() - 0.5D) * tp_range;
                double d4 = Mth.clamp(pLivingEntity.getY() + (pLivingEntity.getRandom().nextInt((int) tp_range) - tp_range / 2), pLevel.getMinBuildHeight(), pLevel.getMinBuildHeight() + ((ServerLevel)pLevel).getLogicalHeight() - 1);
                double d5 = pLivingEntity.getZ() + (pLivingEntity.getRandom().nextDouble() - 0.5D) * tp_range;
                if (pLivingEntity.isPassenger()) {
                   pLivingEntity.stopRiding();
                }

                Vec3 vec3 = pLivingEntity.position();
                pLevel.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(pLivingEntity));
                net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(pLivingEntity, d3, d4, d5);
                if (event.isCanceled()) return pStack;
            }

            if (pStack != null && pLivingEntity instanceof Player) {
                ((Player) pLivingEntity).getCooldowns().addCooldown(pStack.getItem(), 20);
         }
        }

        return pStack;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pLivingEntity);
//        if (pLivingEntity instanceof Player)
//            pLivingEntity.setPos(pLivingEntity.getRandomX(tp_range), 300.0, pLivingEntity.getRandomZ(tp_range));
//        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
        return longTeleport(itemstack, pLevel, pLivingEntity, ServerConfig.LONG_TELEPORT_ITEM_DIST.get());
    }
}
