package net.kettlemc.titan.content.block;

import net.kettlemc.titan.config.TitanConstants;
import net.kettlemc.titan.content.TitanCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class SimpleTitanBlock extends Block {


    public SimpleTitanBlock(String registry, Material material, float hardness, float resistance, String type, int harvestLevel, SoundType soundType, MapColor color) {
        super(material, color);
        this.setRegistryName(new ResourceLocation(TitanConstants.MOD_ID, registry));
        this.setTranslationKey(registry);
        this.setCreativeTab(TitanCreativeTabs.TITAN_MOD_TAB);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(type, harvestLevel);
        this.setSoundType(soundType);
    }
}
