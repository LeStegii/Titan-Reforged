package net.kettlemc.titan.content.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TitanItems {

    public static final SimpleTitanItem TOOL_HANDLE = new SimpleTitanItem("tool_handle");

    public static final SimpleTitanItem TITAN_INGOT = new SimpleTitanItem("titan_ingot");
    public static final SimpleTitanItem TITAN_DUST = new SimpleTitanItem("titan_dust");
    public static final SimpleTitanItem TITAN_CRYSTAL = new SimpleTitanItem("titan_crystal");

    public static final TitanSwordItem TITAN_SWORD = new TitanSwordItem("titan_sword", TitanToolMaterial.TITAN);
    public static final TitanSwordItem TITAN_GIANT_SWORD = new TitanSwordItem("titan_giant_sword", TitanToolMaterial.TITAN_GIANT);
    public static final TitanPickaxeItem TITAN_PICKAXE = new TitanPickaxeItem("titan_pickaxe", TitanToolMaterial.TITAN);
    public static final TitanAxeItem TITAN_AXE = new TitanAxeItem("titan_axe", TitanToolMaterial.TITAN);
    public static final TitanHoeItem TITAN_HOE = new TitanHoeItem("titan_hoe", TitanToolMaterial.TITAN);
    public static final TitanShovelItem TITAN_SHOVEL = new TitanShovelItem("titan_shovel", TitanToolMaterial.TITAN);

    // public static final SimpleTitanItem CITRINE_INGOT = new SimpleTitanItem("citrine_ingot");
    public static final SimpleTitanItem CITRINE_DUST = new SimpleTitanItem("citrine_dust");
    public static final SimpleTitanItem CITRINE_CRYSTAL = new SimpleTitanItem("citrine_crystal");

    public static final TitanSwordItem CITRINE_SWORD = new TitanSwordItem("citrine_sword", TitanToolMaterial.CITRINE);
    public static final TitanSwordItem CITRINE_GIANT_SWORD = new TitanSwordItem("citrine_giant_sword", TitanToolMaterial.CITRINE_GIANT);
    public static final TitanPickaxeItem CITRINE_PICKAXE = new TitanPickaxeItem("citrine_pickaxe", TitanToolMaterial.CITRINE);
    public static final TitanAxeItem CITRINE_AXE = new TitanAxeItem("citrine_axe", TitanToolMaterial.CITRINE);
    public static final TitanHoeItem CITRINE_HOE = new TitanHoeItem("citrine_hoe", TitanToolMaterial.CITRINE);
    public static final TitanShovelItem CITRINE_SHOVEL = new TitanShovelItem("citrine_shovel", TitanToolMaterial.CITRINE);
    
    public static final SimpleTitanItem NETHER_DUST = new SimpleTitanItem("nether_dust");
    public static final SimpleTitanItem NETHER_CRYSTAL = new SimpleTitanItem("nether_crystal");

    public static final TitanSwordItem NETHER_SWORD = new TitanSwordItem("nether_sword", TitanToolMaterial.NETHER);
    public static final TitanSwordItem NETHER_GIANT_SWORD = new TitanSwordItem("nether_giant_sword", TitanToolMaterial.NETHER_GIANT);
    public static final TitanPickaxeItem NETHER_PICKAXE = new TitanPickaxeItem("nether_pickaxe", TitanToolMaterial.NETHER);
    public static final TitanAxeItem NETHER_AXE = new TitanAxeItem("nether_axe", TitanToolMaterial.NETHER);
    public static final TitanHoeItem NETHER_HOE = new TitanHoeItem("nether_hoe", TitanToolMaterial.NETHER);
    public static final TitanShovelItem NETHER_SHOVEL = new TitanShovelItem("nether_shovel", TitanToolMaterial.NETHER);

    private static final Item[] TITAN_ITEM_ARRAY = {
            TOOL_HANDLE,

            TITAN_INGOT,
            TITAN_CRYSTAL,
            TITAN_DUST,
            TITAN_SWORD,
            TITAN_GIANT_SWORD,
            TITAN_AXE,
            TITAN_SHOVEL,
            TITAN_PICKAXE,
            TITAN_HOE,

            CITRINE_CRYSTAL,
            CITRINE_DUST,
            CITRINE_SWORD,
            CITRINE_GIANT_SWORD,
            CITRINE_AXE,
            CITRINE_SHOVEL,
            CITRINE_PICKAXE,
            CITRINE_HOE,
            
            NETHER_CRYSTAL,
            NETHER_DUST,
            NETHER_SWORD,
            NETHER_GIANT_SWORD,
            NETHER_AXE,
            NETHER_SHOVEL,
            NETHER_PICKAXE,
            NETHER_HOE
    };

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        for (Item item : TITAN_ITEM_ARRAY) {
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void registerModel(ModelRegistryEvent event) {
        for (Item item : TITAN_ITEM_ARRAY) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

}
