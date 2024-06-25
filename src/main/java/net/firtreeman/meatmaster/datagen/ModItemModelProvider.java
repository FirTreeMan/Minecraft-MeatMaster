package net.firtreeman.meatmaster.datagen;

import net.firtreeman.meatmaster.MeatMaster;
import net.firtreeman.meatmaster.block.ModBlocks;
import net.firtreeman.meatmaster.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MeatMaster.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        makeItem(ModItems.CREEPER_MEAT);
//        makeItem(ModItems.COOKED_CREEPER_MEAT);
//        makeItem(ModItems.DOG_LIVER);
//        makeItem(ModItems.COOKED_DOG_LIVER);
//        makeItem(ModItems.ENDERMEAT);
//        makeItem(ModItems.HORSE_MEAT);
//        makeItem(ModItems.COOKED_HORSE_MEAT);
//        makeItem(ModItems.COOKED_BLAZE_MEAT);
//        makeItem(ModItems.CAT_MEAT);
//        makeItem(ModItems.COOKED_CAT_MEAT);
//        makeItem(ModItems.SQUID_MEAT);
//        makeItem(ModItems.COOKED_SQUID_MEAT);
//
//        makeItem(ModItems.TABLE_SALT);
//        makeItem(ModItems.GUNPOWDER_CLUMPS);
//        makeItem(ModItems.ENDER_SPICE);
//        makeItem(ModItems.LIVER_BITS);
//        makeItem(ModItems.BLAZE_PEPPER);
//        makeItem(ModItems.BITTER_CRUMBS);
//        makeItem(ModItems.MEAT_RESIDUE);

        for (RegistryObject<Item> item: ModItems.ITEMS.getEntries()) {
            if (!ModBlocks.BLOCKS.getEntries().contains(item))
                makeItem(item);
        }
    }

    private ItemModelBuilder makeItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                    .texture("layer0",
                            new ResourceLocation(MeatMaster.MOD_ID, "item/" + item.getId().getPath()));
    }
}
