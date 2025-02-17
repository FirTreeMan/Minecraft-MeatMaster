package net.firtreeman.meatmaster;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.block.ModBlockEntities;
import net.firtreeman.meatmaster.block.custom.ShearableMeatBlock;
import net.firtreeman.meatmaster.config.CommonConfig;
import net.firtreeman.meatmaster.config.ServerConfig;
import net.firtreeman.meatmaster.core.dispenser.SnifferEggDispenseBehavior;
import net.firtreeman.meatmaster.core.dispenser.TurtleEggDispenseBehavior;
import net.firtreeman.meatmaster.entity.ai.goal.FoodTroughTemptGoal;
import net.firtreeman.meatmaster.entity.projectile.HormoneArrow;
import net.firtreeman.meatmaster.item.ModCreativeModeTabs;
import net.firtreeman.meatmaster.item.ModItems;
import net.firtreeman.meatmaster.item.custom.LongTeleportItem;
import net.firtreeman.meatmaster.loot.ModLootModifiers;
import net.firtreeman.meatmaster.recipe.ModRecipes;
import net.firtreeman.meatmaster.screen.*;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.SPICE_TYPES;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static net.firtreeman.meatmaster.util.TagUtils.*;

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

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC, MOD_ID + "-server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, MOD_ID + "-common.toml");

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
                return HormoneArrow.getDispenserBehavior(level, pos, stack);
            }
        });

        if (CommonConfig.DISPENSABLE_SNIFFER_EGG.get())
            DispenserBlock.registerBehavior(Items.SNIFFER_EGG, new SnifferEggDispenseBehavior());
        if (CommonConfig.DISPENSABLE_TURTLE_EGG.get())
            DispenserBlock.registerBehavior(Items.TURTLE_EGG, new TurtleEggDispenseBehavior());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (RegistryObject<Item> item: ModItems.ITEMS.getEntries())
                if (item.get().isEdible())
                    event.accept(item);
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
                if (tag != null && tag.contains(TAG_HORMONE_TYPE)) {
                    return switch (HORMONE_TYPES.values()[tag.getInt(TAG_HORMONE_TYPE)]) {
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
    public static class FoodListener {
        @SubscribeEvent
        public static void onUsedItem(LivingEntityUseItemEvent.Finish event) {
            ItemStack itemStack = event.getItem();
            if (itemStack.isEdible() && itemStack.getTag() != null) {
                if (event.getEntity() instanceof Player player) {
                    CompoundTag tag = itemStack.getTag();
                    applySpice(tag, player);
                    applyKebab(tag, player);
                }
            }
        }

        public static void applySpice(CompoundTag tag, Player player) {
            if (tag.getString(TAG_SPICE).isEmpty()) return;

            String spiceNames = tag.getString(TAG_SPICE);
            SPICE_TYPES[] spiceNamesArr = Arrays.stream(spiceNames.split("\\|")).map(SPICE_TYPES::valueOf).toArray(SPICE_TYPES[]::new);
            for (SPICE_TYPES spiceType: spiceNamesArr) {
                switch (spiceType) {
                    case SALTY -> player.getFoodData().eat(0, 1.0F);
                    case EXPLOSIVE -> player.level().explode(null, player.getX(), player.getY() + 1.0, player.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
                    case TELEPORTING -> LongTeleportItem.longTeleport(null, player.level(), player, ServerConfig.LONG_TELEPORT_SPICE_DIST.get());
                    case POISONED -> player.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 1));
                    case FLAMMABLE -> player.setSecondsOnFire(20);
                    case BITTER -> player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                    case FILLING -> player.getFoodData().eat(1, 0F);
                    default -> throw new IllegalStateException("spiceType must be defined");
                }
            }
        }

        public static void applyKebab(CompoundTag tag, Player player) {
            if (!tag.contains(TAG_KEBAB_DATA)) return;

            CompoundTag kebabTag = tag.getCompound(TAG_KEBAB_DATA);
            player.getFoodData().eat(kebabTag.getInt(TAG_NUTRITION), kebabTag.getFloat(TAG_SATURATION));
            if (!kebabTag.getCompound(TAG_ALL_EFFECTS).isEmpty()) {
                CompoundTag effects = kebabTag.getCompound(TAG_ALL_EFFECTS);
                for (String key: effects.getAllKeys()) {
                    CompoundTag effectTag = effects.getCompound(key);
                    if (effectTag.getFloat(TAG_EFFECT_PROBABILITY) > player.level().getRandom().nextFloat())
                        player.addEffect(MobEffectInstance.load(effectTag));
                }
            }
        }

        @SubscribeEvent
        public static void onCraftedItem(PlayerEvent.ItemCraftedEvent event) {
            ItemStack output = event.getCrafting();
            Container inputs = event.getInventory();

            if (!output.is(ModItems.KEBAB.get())) return;

            CompoundTag baseTag = output.getOrCreateTag();
            CompoundTag tag = new CompoundTag();
            baseTag.put(TAG_KEBAB_DATA, tag);

            tag.putInt(TAG_NUTRITION, 0);
            tag.putFloat(TAG_SATURATION, 0F);
            tag.put(TAG_ALL_EFFECTS, new CompoundTag());
            tag.putString(TAG_SPICE, "");

            for (int i = 0; i < inputs.getContainerSize(); i++) {
                ItemStack input = inputs.getItem(i);
                if (input.isEdible()) {
                    FoodProperties foodProperties = input.getFoodProperties(null);
                    assert foodProperties != null;

                    tag.putInt(TAG_NUTRITION, tag.getInt(TAG_NUTRITION) + foodProperties.getNutrition());
                    tag.putFloat(TAG_SATURATION, tag.getFloat(TAG_SATURATION) + foodProperties.getSaturationModifier());
                    if (!foodProperties.getEffects().isEmpty()) {
                        for (Pair<MobEffectInstance, Float> pair: foodProperties.getEffects()) {
                            CompoundTag effectTag = new CompoundTag();
                            effectTag.putFloat(TAG_EFFECT_PROBABILITY, pair.getSecond());
                            pair.getFirst().save(effectTag);

                            CompoundTag effectCompound = tag.getCompound(TAG_ALL_EFFECTS);
                            effectCompound.put(String.valueOf(pair.getFirst().hashCode()), effectTag);
                        }
                    }
                    if (input.getTag() != null && !input.getTag().getString(TAG_SPICE).isEmpty()) {
                        String spiceType = input.getTag().getString(TAG_SPICE);
                        if (baseTag.getString(TAG_SPICE).isEmpty())
                            baseTag.putString(TAG_SPICE, spiceType);
                        else baseTag.putString(TAG_SPICE, baseTag.getString(TAG_SPICE) + "|" + spiceType);
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
                // break out if any WrappedGoal is already FoodTroughTemptGoal
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
                else if (animal instanceof Sniffer)
                    animal.goalSelector.addGoal(3, new FoodTroughTemptGoal(animal, 1.0, Ingredient.of(ItemTags.SNIFFER_FOOD), false));
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

        // this is a bit of a brute-force solution and may not be compatible with some mods
        // waits for breeding to finish and breeding time to be set before editing it
        // however the other option is a coremod (mixin)
        @SubscribeEvent
        public static void onTick(TickEvent.ServerTickEvent event) {
            if (breedingHormoneHandler.isEmpty()) return;

            for (Iterator<AgeableMob> i = breedingHormoneHandler.keySet().iterator(); i.hasNext();) {
                AgeableMob ageableMob = i.next();
                System.out.println(ageableMob.getAge());

                int timeLeft = breedingHormoneHandler.get(ageableMob);
                if (timeLeft <= 0) {
                    // sets age to what it would be on this tick if age was halved at time of breeding
                    ageableMob.setAge(Math.max(ageableMob.getAge() / 2 - BREEDING_HORMONE_DELAY / 2, 0));
                    i.remove();
                }
                else breedingHormoneHandler.put(ageableMob, breedingHormoneHandler.get(ageableMob) - 1);
                System.out.println(ageableMob.getAge());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class MiscListener {
        @SubscribeEvent
        public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
            Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
            if (event.getItemStack().is(Items.SHEARS) && block instanceof ShearableMeatBlock shearableMeatBlock) {
                if (event.getLevel().isClientSide) return;

                BlockPos pos = event.getPos();
                RandomSource rand = event.getLevel().getRandom();
                BlockState newState = shearableMeatBlock.getPostShear().defaultBlockState();
                Level level = event.getLevel();

                event.getItemStack().hurtAndBreak(1, event.getEntity(), (e) -> e.broadcastBreakEvent(event.getEntity().getUsedItemHand()));
                level.setBlock(event.getPos(), newState, 0);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(event.getEntity(), newState));

                ItemEntity itemDrop = new ItemEntity(level, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, new ItemStack(shearableMeatBlock.getDrop()));
                itemDrop.setDeltaMovement(itemDrop.getDeltaMovement().add((rand.nextFloat() - rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F, (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                level.addFreshEntity(itemDrop);

                level.playSeededSound(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F, 0);
            }
        }

        @SubscribeEvent
        public static void onItemTooltip(ItemTooltipEvent event) {
            if (event.getItemStack().hasTag()) {
                double chance = event.getItemStack().getTag().getDouble("JEIToolTipPercentage");
                if (chance == 0.0) return;

                event.getToolTip().add(Component.literal(String.format("%.0f%%", chance * 100)).withStyle(ChatFormatting.YELLOW));
            }
        }
    }


}
