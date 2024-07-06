package net.firtreeman.meatmaster;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.entity.ai.goal.FoodTroughTemptGoal;
import net.firtreeman.meatmaster.entity.projectile.HormoneArrow;
import net.firtreeman.meatmaster.item.ModCreativeModeTabs;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.item.custom.LongTeleportItem;
import net.firtreeman.meatmaster.item.custom.SpiceItem;
import net.firtreeman.meatmaster.loot.ModLootModifiers;
import net.firtreeman.meatmaster.recipe.ModRecipes;
import net.firtreeman.meatmaster.screen.*;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.HormoneUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MeatMaster.MOD_ID)
public class MeatMaster {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "meatmaster";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public MeatMaster() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DispenserBlock.registerBehavior(ModItems.SYRINGE_DART.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position pos, ItemStack stack) {
                return HormoneArrow.dispenser(level, pos, stack);
            }
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.CREEPER_MEAT);
            event.accept(ModItems.DOG_LIVER);
            event.accept(ModItems.HORSE_MEAT);
            event.accept(ModItems.CAT_MEAT);
            event.accept(ModItems.SQUID_MEAT);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.MEAT_REFINERY_MENU.get(), MeatRefineryStationScreen::new);
            MenuScreens.register(ModMenuTypes.MEAT_COMPACTOR_MENU.get(), MeatCompactorStationScreen::new);
            MenuScreens.register(ModMenuTypes.LATHERER_MENU.get(), LathererStationScreen::new);
            MenuScreens.register(ModMenuTypes.INDUSTRIAL_OVEN_MENU.get(), IndustrialOvenStationScreen::new);
            MenuScreens.register(ModMenuTypes.MEAT_MASHER_MENU.get(), MeatMasherStationScreen::new);
            MenuScreens.register(ModMenuTypes.FOOD_TROUGH_MENU.get(), FoodTroughStationScreen::new);
            MenuScreens.register(ModMenuTypes.HORMONE_RESEARCH_MENU.get(), HormoneResearchStationScreen::new);
        }

        @SubscribeEvent
        public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
            event.register((stack, layer) -> {
                if (layer != 1) return 0xFFFFFF;

                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("HormoneType")) {
                    return switch (HORMONE_TYPES.values()[tag.getInt("HormoneType")]) {
                        case NONE -> 0xf6feff;
                        case GROWTH -> 0x01f1ff;
                        case BREEDING -> 0x00ff26;
                        case YIELD -> 0xcbcdcd;
                    };
                }
                return 0xf6feff;
            },
                    ModItems.HORMONE_BASE.get(),
                    ModItems.SYRINGE_DART.get()
            );
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class SpiceListener {
//        @SubscribeEvent
//        public void onewtwtewt(PlayerEvent.ItemPickupEvent event) {
//            System.out.println(event.getStack().getTag());
//        }

        @SubscribeEvent
        public static void onUsedItem(LivingEntityUseItemEvent.Finish event) {
            ItemStack itemStack = event.getItem();
            if (itemStack.isEdible() && itemStack.getTag() != null) {
                if (event.getEntity() instanceof Player player) {
                    CompoundTag tag = itemStack.getTag();
                    if (!tag.getString("Spice").isEmpty()) {
                        String spiceNames = tag.getString("Spice");
                        SpiceItem.SPICE_NAMES[] spiceNamesArr = Arrays.stream(spiceNames.split("\\|")).map(SpiceItem.SPICE_NAMES::valueOf).toArray(SpiceItem.SPICE_NAMES[]::new);
                        for (SpiceItem.SPICE_NAMES spiceName : spiceNamesArr) {
                            switch (spiceName) {
                                case SALTY -> player.getFoodData().eat(0, 1.0F);
                                case EXPLOSIVE ->
                                        player.level().explode(null, player.getX(), player.getY() + 1.0, player.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
                                case TELEPORTING -> LongTeleportItem.longTeleport(null, player.level(), player, 50);
                                case POISONED -> player.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 1));
                                case FLAMMABLE -> player.setSecondsOnFire(20);
                                case BITTER -> player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                                case FILLING -> player.getFoodData().eat(1, 0F);
                                default -> throw new IllegalStateException("spiceName must be defined");
                            }
                        }
                    }
                    player.getFoodData().eat(tag.getInt("totalNutrition"), tag.getFloat("totalSaturation"));
                    if (!tag.getCompound("allEffects").isEmpty()) {
                        CompoundTag effects = tag.getCompound("allEffects");
                        for (String key : effects.getAllKeys()) {
                            CompoundTag effectTag = effects.getCompound(key);
                            if (effectTag.getFloat("probability") > player.level().getRandom().nextFloat())
                                player.addEffect(MobEffectInstance.load(effectTag));
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onCraftedItem(PlayerEvent.ItemCraftedEvent event) {
            ItemStack output = event.getCrafting();
            Container inputs = event.getInventory();

            if (!output.is(ModItems.KEBAB.get())) return;

            CompoundTag tag = output.getOrCreateTag();
            tag.putInt("totalNutrition", 0);
            tag.putFloat("totalSaturation", 0F);
            tag.put("allEffects", new CompoundTag());
            tag.putString("Spice", "");

            for (int i = 0; i < inputs.getContainerSize(); i++) {
                ItemStack input = inputs.getItem(i);
                if (input.isEdible()) {
                    FoodProperties foodProperties = input.getFoodProperties(null);
                    assert foodProperties != null;

                    tag.putInt("totalNutrition", tag.getInt("totalNutrition") + foodProperties.getNutrition());
                    tag.putFloat("totalSaturation", tag.getFloat("totalSaturation") + foodProperties.getSaturationModifier());
                    if (!foodProperties.getEffects().isEmpty()) {
                        for (Pair<MobEffectInstance, Float> pair: foodProperties.getEffects()) {
                            CompoundTag effectTag = new CompoundTag();
                            effectTag.putFloat("probability", pair.getSecond());
                            pair.getFirst().save(effectTag);

                            CompoundTag effectCompound = tag.getCompound("allEffects");
                            effectCompound.put(String.valueOf(pair.getFirst().hashCode()), effectTag);
                        }
                    }
                    if (input.getTag() != null && !input.getTag().getString("Spice").isEmpty()) {
                        String spiceName = input.getTag().getString("Spice");
                        if (tag.getString("Spice").isEmpty())
                            tag.putString("Spice", spiceName);
                        else tag.putString("Spice", tag.getString("Spice") + "|" + spiceName);
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class FoodTroughListener {
        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            TemptGoal temptGoal = null;
            int temptGoalPriority = 0;
            if (event.getEntity() instanceof Animal animal) {
                for (WrappedGoal wrappedGoal: animal.goalSelector.getAvailableGoals()) {
                    if (wrappedGoal.getGoal() instanceof FoodTroughTemptGoal) return;
                    if (wrappedGoal.getGoal() instanceof TemptGoal) {
                        temptGoal = (TemptGoal) wrappedGoal.getGoal();
                        temptGoalPriority = wrappedGoal.getPriority();
                    }
                }
                if (temptGoal != null) {
                    animal.goalSelector.addGoal(temptGoalPriority, new FoodTroughTemptGoal(animal, temptGoal.speedModifier, temptGoal.items, temptGoal.canScare));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class HormoneListener {
        private static final int BREEDING_HORMONE_DELAY = 10;
        private static final HashMap<AgeableMob, Integer> breedingHormoneHandler = new HashMap<>();

        @SubscribeEvent
        public static void onEntityShot(LivingHurtEvent event) {
            if (event.getSource().getDirectEntity() instanceof HormoneArrow arrow) {
                Entity entity = event.getEntity();
                CompoundTag tag = entity.serializeNBT();

                if (tag.getCompound("ForgeData").contains("TakenHormone")) return;

                boolean hormoneApplied = false;
                switch (arrow.getHormoneType()) {
                    case GROWTH -> {
                        if (entity instanceof AgeableMob ageableMob && ageableMob.isBaby()) {
                            hormoneApplied = true;
                            ageableMob.setAge(ageableMob.getAge() / 2);
                        }
                    }
                    case BREEDING -> {
                        if (entity instanceof Animal animal && !animal.isBaby()) {
                            hormoneApplied = true;
                            System.out.println("HHHHHHHHJ");
                        }
                    }
                    case YIELD -> {
                        if (entity instanceof Mob mob) {
                            hormoneApplied = true;
                        }
                    }
                }

                if (hormoneApplied) {
                    CompoundTag forgeTag = tag.getCompound("ForgeData");
                    forgeTag.putInt("TakenHormone", arrow.getHormoneType().ordinal());
                    tag.put("ForgeData", forgeTag);
                    entity.deserializeNBT(tag);
                }
            }
        }

        @SubscribeEvent
        public static void onEntityBreeding(BabyEntitySpawnEvent event) {
            Mob[] parents = new Mob[]{event.getParentA(), event.getParentB()};

            for (Mob mob: parents) {
                CompoundTag forgeTag = mob.serializeNBT().getCompound("ForgeData");
                if (mob instanceof AgeableMob ageableMob && HORMONE_TYPES.values()[forgeTag.getInt("TakenHormone")] == HORMONE_TYPES.BREEDING)
                    breedingHormoneHandler.put(ageableMob, BREEDING_HORMONE_DELAY);
            }
        }

        @SubscribeEvent
        public static void onLivingDrops(LivingDropsEvent event) {
            if (event.getEntity() instanceof Mob mob) {
                if (HORMONE_TYPES.values()[mob.serializeNBT().getCompound("ForgeData").getInt("TakenHormone")] == HORMONE_TYPES.YIELD) {
                    List<ItemEntity> drops = event.getDrops().stream().filter(s -> s.getItem().getRarity() == Rarity.COMMON).toList();
                    int chosen = mob.level().getRandom().nextIntBetweenInclusive(0, drops.size() - 1);
                    event.getDrops().add(drops.get(chosen).copy());
                }
            }
        }

        @SubscribeEvent
        public static void onTick(TickEvent.ServerTickEvent event) {
            if (breedingHormoneHandler.isEmpty()) return;

            for (Iterator<AgeableMob> i = breedingHormoneHandler.keySet().iterator(); i.hasNext();) {
                AgeableMob ageableMob = i.next();
                System.out.println(ageableMob.getAge());

                int timeLeft = breedingHormoneHandler.get(ageableMob);
                if (timeLeft <= 0) {
                    // sets age to what it would be on this tick if age was halved without any delay
                    ageableMob.setAge(Math.max(ageableMob.getAge() / 2 - BREEDING_HORMONE_DELAY / 2, 0));
                    i.remove();
                }
                else breedingHormoneHandler.put(ageableMob, breedingHormoneHandler.get(ageableMob) - 1);
                System.out.println(ageableMob.getAge());
            }
        }
    }


}
