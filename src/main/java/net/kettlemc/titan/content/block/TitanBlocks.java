package net.kettlemc.titan.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TitanBlocks {


    public static final SimpleTitanBlock TITAN_BLOCK = new SimpleTitanBlock("titan_block", Material.IRON, 5, 6, ToolEnums.TitanToolType.PICKAXE.getId(), ToolEnums.TitanHarvestLevel.IRON.getLevel(), SoundType.METAL, MapColor.BLUE);
    public static final SimpleTitanBlock CITRINE_BLOCK = new SimpleTitanBlock("citrine_block", Material.IRON, 5, 6, ToolEnums.TitanToolType.PICKAXE.getId(), ToolEnums.TitanHarvestLevel.IRON.getLevel(), SoundType.METAL, MapColor.YELLOW);
    public static final SimpleTitanBlock NETHER_CRYSTAL_BLOCK = new SimpleTitanBlock("nether_crystal_block", Material.IRON, 5, 6, ToolEnums.TitanToolType.PICKAXE.getId(), ToolEnums.TitanHarvestLevel.IRON.getLevel(), SoundType.METAL, MapColor.RED);
    public static final BlockIronFurnace IRON_FURNACE = new BlockIronFurnace("iron_furnace", false, Material.IRON, 5, 6, ToolEnums.TitanToolType.PICKAXE.getId(), ToolEnums.TitanHarvestLevel.WOOD_GOLD.getLevel(), SoundType.METAL, MapColor.IRON);
    public static final BlockIronFurnace IRON_FURNACE_LIT = new BlockIronFurnace("iron_furnace_lit", true, Material.IRON, 5, 6, ToolEnums.TitanToolType.PICKAXE.getId(), ToolEnums.TitanHarvestLevel.WOOD_GOLD.getLevel(), SoundType.METAL, MapColor.IRON);


    private static final Block[] TITAN_BLOCK_ARRAY = {
            TITAN_BLOCK,
            CITRINE_BLOCK,
            NETHER_CRYSTAL_BLOCK,
            IRON_FURNACE
    };

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        for (Block block : TITAN_BLOCK_ARRAY) {
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void registerItemBlock(RegistryEvent.Register<Item> event) {
        for (Block block : TITAN_BLOCK_ARRAY) {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        for (Block block : TITAN_BLOCK_ARRAY) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
    }

}
