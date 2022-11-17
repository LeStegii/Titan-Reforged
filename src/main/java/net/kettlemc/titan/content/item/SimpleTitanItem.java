package net.kettlemc.titan.content.item;

import net.kettlemc.titan.config.TitanConstants;
import net.kettlemc.titan.content.TitanCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class SimpleTitanItem extends Item {

    public SimpleTitanItem(String registry) {
        this.setRegistryName(new ResourceLocation(TitanConstants.MOD_ID, registry));
        this.setTranslationKey(registry);
        this.setCreativeTab(TitanCreativeTabs.TITAN_MOD_TAB);
        this.setHasSubtypes(false);
    }
}
