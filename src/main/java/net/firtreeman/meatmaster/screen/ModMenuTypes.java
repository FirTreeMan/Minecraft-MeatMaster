package net.firtreeman.meatmaster.screen;

import net.firtreeman.meatmaster.MeatMaster;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MeatMaster.MOD_ID);

    public static final RegistryObject<MenuType<MeatRefineryStationMenu>> MEAT_REFINERY_MENU = registerMenuType("meat_refinery_menu", MeatRefineryStationMenu::new);
    public static final RegistryObject<MenuType<MeatCompactorStationMenu>> MEAT_COMPACTOR_MENU = registerMenuType("meat_compactor_menu", MeatCompactorStationMenu::new);
    public static final RegistryObject<MenuType<LathererStationMenu>> LATHERER_MENU = registerMenuType("latherer_menu", LathererStationMenu::new);
    public static final RegistryObject<MenuType<IndustrialOvenStationMenu>> INDUSTRIAL_OVEN_MENU = registerMenuType("industrial_oven_menu", IndustrialOvenStationMenu::new);
    public static final RegistryObject<MenuType<MeatMasherStationMenu>> MEAT_MASHER_MENU = registerMenuType("meat_masher_menu", MeatMasherStationMenu::new);
    public static final RegistryObject<MenuType<FoodTroughStationMenu>> FOOD_TROUGH_MENU = registerMenuType("food_trough_menu", FoodTroughStationMenu::new);
    public static final RegistryObject<MenuType<HormoneResearchStationMenu>> HORMONE_RESEARCH_MENU = registerMenuType("hormone_research_menu", HormoneResearchStationMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
