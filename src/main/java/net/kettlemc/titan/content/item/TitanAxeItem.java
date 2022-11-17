package net.kettlemc.titan.content.item;

import net.kettlemc.titan.config.TitanConstants;
import net.kettlemc.titan.content.TitanCreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.util.ResourceLocation;

public class TitanAxeItem extends ItemAxe {

    public TitanAxeItem(String registry, ToolMaterial material) {
        super(material, material.getAttackDamage(), -3.0F);
        this.setRegistryName(new ResourceLocation(TitanConstants.MOD_ID, registry));
        this.setTranslationKey(registry);
        this.setCreativeTab(TitanCreativeTabs.TITAN_MOD_TAB);
        this.setHasSubtypes(false);
    }
}
