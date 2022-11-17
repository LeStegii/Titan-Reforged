package net.kettlemc.titan.content.tileentity;

import net.kettlemc.titan.content.block.BlockIronFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Iterator;

public class TitanTileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory {

    private static final int DEFAULT_BURNING_TIME = 200;
    private static final int MAX_STACK_SIZE = 64;
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {2, 1};
    private static final int[] SLOTS_SIDES = new int[] {1};

    private NonNullList<ItemStack> furnaceItemStacks;

    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    private int outputModifier;
    private double burningTimeModifier;

    private String furnaceCustomName;

    IItemHandler handlerTop;
    IItemHandler handlerBottom;
    IItemHandler handlerSide;

    public TitanTileEntityFurnace(String registry, int outputModifier, double burningTimeModifier) {
        this.furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
        this.handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
        this.handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
        this.handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);
        this.outputModifier = outputModifier;
        this.burningTimeModifier = burningTimeModifier;
    }

    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator<ItemStack> iterator = this.furnaceItemStacks.iterator();

        ItemStack itemstack;
        do {
            if (!iterator.hasNext()) {
                return true;
            }

            itemstack = iterator.next();
        } while (itemstack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.furnaceItemStacks.get(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, slot, amount);
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        ItemStack currentItemstack = this.furnaceItemStacks.get(slot);
        boolean equal = !itemStack.isEmpty() && itemStack.isItemEqual(currentItemstack) && ItemStack.areItemStackTagsEqual(itemStack, currentItemstack);
        this.furnaceItemStacks.set(slot, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }

        if (slot == 0 && !equal) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    }

    @Override
    public boolean hasCustomName() {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String customInventoryName) {
        this.furnaceCustomName = customInventoryName;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.furnaceItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbtTagCompound, this.furnaceItemStacks);
        this.furnaceBurnTime = nbtTagCompound.getInteger("BurnTime");
        this.cookTime = nbtTagCompound.getInteger("CookTime");
        this.totalCookTime = nbtTagCompound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = nbtTagCompound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("BurnTime", (short) this.furnaceBurnTime);
        nbtTagCompound.setInteger("CookTime", (short) this.cookTime);
        nbtTagCompound.setInteger("CookTimeTotal", (short) this.totalCookTime);
        ItemStackHelper.saveAllItems(nbtTagCompound, this.furnaceItemStacks);

        if (this.hasCustomName()) {
            nbtTagCompound.setString("CustomName", this.furnaceCustomName);
        }

        return nbtTagCompound;
    }

    @Override
    public int getInventoryStackLimit() {
        return MAX_STACK_SIZE;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote) {
            ItemStack itemstack = this.furnaceItemStacks.get(1);
            if (this.isBurning() || !itemstack.isEmpty() && !(this.furnaceItemStacks.get(0)).isEmpty()) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.furnaceBurnTime = getItemBurnTime(itemstack);
                    this.currentItemBurnTime = this.furnaceBurnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.furnaceItemStacks.set(1, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;
                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks.get(0));
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                BlockIronFurnace.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1) {
            this.markDirty();
        }

    }

    public int getCookTime(ItemStack itemStack) {
        return (int) (DEFAULT_BURNING_TIME / burningTimeModifier);
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks.get(0).isEmpty()) {
            return false;
        } else {
            ItemStack itemstack = IronFurnaceRecipes.getResult(this.furnaceItemStacks.get(0));
            if (itemstack == null || itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.furnaceItemStacks.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = this.furnaceItemStacks.get(0);
            ItemStack itemstack1 = IronFurnaceRecipes.getResult(itemstack);
            ItemStack itemstack2 = this.furnaceItemStacks.get(2);
            if (itemstack2.isEmpty()) {
                this.furnaceItemStacks.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount() * this.outputModifier);
            }

            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !(this.furnaceItemStacks.get(1)).isEmpty() && (this.furnaceItemStacks.get(1)).getItem() == Items.BUCKET) {
                this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }

    }

    public static int getItemBurnTime(ItemStack itemStack) {
        return TileEntityFurnace.getItemBurnTime(itemStack);
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return entityPlayer.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer) {
        // Not implemented
    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer) {
        // Not implemented
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemstack = this.furnaceItemStacks.get(1);
            return isItemFuel(itemstack) || SlotFurnaceFuel.isBucket(itemstack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN) {
            return SLOTS_BOTTOM;
        } else {
            return enumFacing == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, EnumFacing enumFacing) {
        return this.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN && slot == 1) {
            Item item = itemStack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getGuiID() {
        return "minecraft:furnace";
    }

    @Override
    public Container createContainer(InventoryPlayer p_createContainer_1_, EntityPlayer p_createContainer_2_) {
        return new ContainerFurnace(p_createContainer_1_, this);
    }

    @Override
    public int getField(int field) {
        switch (field) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int field, int value) {
        switch (field) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }

    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        this.furnaceItemStacks.clear();
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing enumFacing) {
        if (enumFacing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (enumFacing == EnumFacing.DOWN) {
                return (T) this.handlerBottom;
            } else {
                return (T) (enumFacing == EnumFacing.UP ? this.handlerTop : this.handlerSide);
            }
        } else {
            return super.getCapability(capability, enumFacing);
        }
    }
}
