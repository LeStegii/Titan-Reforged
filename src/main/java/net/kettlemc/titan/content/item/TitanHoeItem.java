package net.kettlemc.titan.content.item;

import net.kettlemc.titan.config.TitanConstants;
import net.kettlemc.titan.content.TitanCreativeTabs;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

public class TitanHoeItem extends ItemHoe {

    public TitanHoeItem(String registry, ToolMaterial material) {
        super(material);
        this.setRegistryName(new ResourceLocation(TitanConstants.MOD_ID, registry));
        this.setTranslationKey(registry);
        this.setCreativeTab(TitanCreativeTabs.TITAN_MOD_TAB);
        this.setHasSubtypes(false);
    }
}
