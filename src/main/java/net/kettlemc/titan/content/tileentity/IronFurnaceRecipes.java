package net.kettlemc.titan.content.tileentity;

import net.kettlemc.titan.content.item.TitanItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.HashMap;
import java.util.Map;

public class IronFurnaceRecipes {

    private static final HashMap<ItemStack, ItemStack> ITEMSTACK_RECIPES = new HashMap<>();
    private static final HashMap<ItemStack, Float> ITEMSTACK_EXP = new HashMap<>();


    public static void init() {
        FurnaceRecipes recipes = FurnaceRecipes.instance();
        recipes.getSmeltingList().forEach((input, output) -> registerRecipe(input, output, recipes.getSmeltingExperience(input)));

        registerRecipe(new ItemStack(TitanItems.TITAN_DUST), new ItemStack(TitanItems.TITAN_INGOT), 1.0F);

    }

    public static ItemStack getResult(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : ITEMSTACK_RECIPES.entrySet()) {
            if (compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }

    public static float getExpResult(ItemStack input) {
        for (Map.Entry<ItemStack, Float> entry : ITEMSTACK_EXP.entrySet()) {
            if (compareItemStacks(input, entry.getKey())) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }

    public static void registerRecipe(ItemStack input, ItemStack output, float exp) {
        ITEMSTACK_RECIPES.put(input, output);
        ITEMSTACK_EXP.put(input, exp);
    }

    // This method is from the original Minecraft code
    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

}
