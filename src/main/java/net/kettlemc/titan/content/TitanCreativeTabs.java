package net.kettlemc.titan.content;

import net.kettlemc.titan.content.item.TitanItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class TitanCreativeTabs {

    public static final CreativeTabs TITAN_MOD_TAB = new CreativeTabs("titanreforged") {

        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(TitanItems.TITAN_CRYSTAL);
        }

    };

}
