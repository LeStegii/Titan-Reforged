package net.kettlemc.titan.content.block;

import net.kettlemc.titan.config.TitanConstants;
import net.kettlemc.titan.content.TitanCreativeTabs;
import net.kettlemc.titan.content.tileentity.TitanTileEntityFurnace;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockIronFurnace extends BlockFurnace {

    private static boolean keepInventory;

    public BlockIronFurnace(String registry, boolean isBurning, Material material, float hardness, float resistance, String type, int harvestLevel, SoundType soundType, MapColor color) {
        super(isBurning);
        this.setRegistryName(new ResourceLocation(TitanConstants.MOD_ID, registry));
        this.setTranslationKey(registry);
        this.setCreativeTab(TitanCreativeTabs.TITAN_MOD_TAB);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(type, harvestLevel);
        this.setSoundType(soundType);
        keepInventory = false;
    }

    @Override
    public ItemStack getItem(World world, BlockPos blockPos, IBlockState blockState) {
        return new ItemStack(TitanBlocks.IRON_FURNACE);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(TitanBlocks.IRON_FURNACE);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TitanTileEntityFurnace) {
                player.displayGUIChest((IInventory) tileEntity);
            }
        }
        return true;
    }

    public static void setState(boolean active, World world, BlockPos blockPos) {
        IBlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        keepInventory = true;

        if (active) {
            world.setBlockState(blockPos, TitanBlocks.IRON_FURNACE_LIT.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
            world.setBlockState(blockPos, TitanBlocks.IRON_FURNACE_LIT.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
        } else {
            world.setBlockState(blockPos, TitanBlocks.IRON_FURNACE.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
            world.setBlockState(blockPos, TitanBlocks.IRON_FURNACE.getDefaultState().withProperty(FACING, blockState.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(blockPos, tileEntity);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TitanTileEntityFurnace(TitanBlocks.IRON_FURNACE.getTranslationKey(), 1, 0.75);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState blockState, EntityLivingBase entityPlacer, ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(FACING, entityPlacer.getHorizontalFacing().getOpposite()), 2);

        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);

            if (tileEntity instanceof TitanTileEntityFurnace) {
                ((TitanTileEntityFurnace) tileEntity).setCustomInventoryName(itemStack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState) {
        if (!keepInventory) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TitanTileEntityFurnace) {
                InventoryHelper.dropInventoryItems(world, blockPos, (TitanTileEntityFurnace)tileEntity);
                world.updateComparatorOutputLevel(blockPos, this);
            }
        }

        super.breakBlock(world, blockPos, blockState);
    }
}
