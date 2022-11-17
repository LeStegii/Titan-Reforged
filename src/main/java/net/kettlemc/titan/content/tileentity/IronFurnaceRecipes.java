package net.kettlemc.titan.content.tileentity;

import net.kettlemc.titan.content.item.TitanItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.HashMap;

public class IronFurnaceRecipes {

    private static final HashMap<ItemStack, ItemStack> ITEMSTACK_RECIPES = new HashMap<>();
    private static final HashMap<ItemStack, Float> ITEMSTACK_EXP = new HashMap<>();


    public static void init() {
        FurnaceRecipes recipes = FurnaceRecipes.instance();
        recipes.getSmeltingList().forEach((input, output) -> {
            registerRecipe(input, output, recipes.getSmeltingExperience(input));
        });

        registerRecipe(new ItemStack(TitanItems.TITAN_DUST), new ItemStack(TitanItems.TITAN_INGOT), 1.0F);

    }

    public static ItemStack getResult(ItemStack input) {
        if (!isValidInput(input))
            return new ItemStack(Items.AIR);
        return ITEMSTACK_RECIPES.get(input);
    }

    public static float getExpResult(ItemStack input) {
        if (!isValidInput(input))
            return 0;
        return ITEMSTACK_EXP.get(input);
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return ITEMSTACK_RECIPES.containsKey(itemStack);
    }

    public static boolean registerRecipe(ItemStack input, ItemStack output, float exp) {
        if (!ITEMSTACK_RECIPES.containsKey(input)) {
            return false;
        }
        ITEMSTACK_RECIPES.put(input, output);
        ITEMSTACK_EXP.put(input, exp);
        return true;
    }

}
