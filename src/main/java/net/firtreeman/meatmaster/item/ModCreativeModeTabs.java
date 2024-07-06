package net.firtreeman.meatmaster.item;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.item.custom.HormoneArrowItem;
import net.firtreeman.meatmaster.item.custom.HormoneBaseItem;
import net.firtreeman.meatmaster.util.HORMONE_TYPES;
import net.firtreeman.meatmaster.util.HormoneUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MeatMaster.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("meat_master_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CREEPER_MEAT.get()))
                    .title(Component.translatable("creativetab.meat_master_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        ModItems.ITEMS.getEntries().forEach(itemLike -> pOutput.accept(itemLike.get()));
                        ModBlocks.BLOCKS.getEntries().forEach(itemLike -> pOutput.accept(itemLike.get()));
                        generateHormoneArrowItems(pOutput);
                        generateHormoneBaseItems(pOutput);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void generateHormoneArrowItems(CreativeModeTab.Output output) {
        for (HORMONE_TYPES hormoneType: HORMONE_TYPES.values()) {
            if (hormoneType == HORMONE_TYPES.NONE) continue;
            ItemStack hormoneArrowItem = HormoneUtils.itemStackOf(ModItems.SYRINGE_DART.get(), hormoneType);
            output.accept(hormoneArrowItem);
        }
    }

    public static void generateHormoneBaseItems(CreativeModeTab.Output output) {
        for (HORMONE_TYPES hormoneType: HORMONE_TYPES.values()) {
            if (hormoneType == HORMONE_TYPES.NONE) continue;
            ItemStack hormoneBaseItem = HormoneUtils.itemStackOf(ModItems.HORMONE_BASE.get(), hormoneType);
            output.accept(hormoneBaseItem);
        }
    }
}
