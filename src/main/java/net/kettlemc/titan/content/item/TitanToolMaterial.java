package net.kettlemc.titan.content.item;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class TitanToolMaterial {

    public static final Item.ToolMaterial TITAN = EnumHelper.addToolMaterial("titan", 4, 2561, 10.0F, 4.0F, 20);
    public static final Item.ToolMaterial TITAN_GIANT = EnumHelper.addToolMaterial("titan_giant", 4, 2561, 10.0F, 7.0F, 15);
    public static final Item.ToolMaterial CITRINE = EnumHelper.addToolMaterial("citrine", 2, 400, 7.0F, 2.0F, 15);
    public static final Item.ToolMaterial CITRINE_GIANT = EnumHelper.addToolMaterial("citrine_giant", 2, 400, 7.0F, 4.5F, 15);
    public static final Item.ToolMaterial NETHER = EnumHelper.addToolMaterial("nether", 2, 300, 7.0F, 2.5F, 15);
    public static final Item.ToolMaterial NETHER_GIANT = EnumHelper.addToolMaterial("nether_giant", 2, 300, 7.0F, 5F, 15);
    public static final Item.ToolMaterial DIAMOND_GIANT = EnumHelper.addToolMaterial("diamond_giant", 3, 2061, 7.0F, 6F, 10);

}
