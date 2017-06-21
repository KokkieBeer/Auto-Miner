package com.Egietje.AutoMiner.blocks;

import java.util.Random;

import com.Egietje.AutoMiner.entities.tileentities.TileEntityAutoMiner;
import com.Egietje.AutoMiner.init.AMBlocks;
import com.Egietje.AutoMiner.init.AMItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAutoMiner extends Block {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static IProperty ACTIVATED = PropertyBool.create("activated");

	public BlockAutoMiner() {
		super(Material.PISTON);
		this.setDefaultState(
				this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVATED, false));
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
	}

	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
		return state.withProperty(ACTIVATED, te.ACTIVATED);
	}

	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVATED });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.getTileEntity(pos) instanceof TileEntityAutoMiner) {
			TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
			if (te.ACTIVATED) {
				EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
				double d0 = (double) pos.getX() + 0.5D;
				double d1 = (double) pos.getY() + 0.5D;
				double d2 = (double) pos.getZ() + 0.5D;
				double d3 = 0.52D;
				double d4 = rand.nextDouble() * 0.6D - 0.3D;

				switch (enumfacing) {
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D,
							new int[0]);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D,
							new int[0]);
					break;
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D,
							new int[0]);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D,
							new int[0]);
				default:
					break;
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				if (TileEntityFurnace.isItemFuel(playerIn.getHeldItem(hand))) {
					if (!playerIn.isCreative()) {
						playerIn.getHeldItem(hand).shrink(1);
					}
					if (worldIn.getTileEntity(pos) instanceof TileEntityAutoMiner) {
						TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
						te.addFuel(getItemBurnTime(playerIn.getHeldItem(hand)));
					}
				} else if (playerIn.getHeldItem(hand).getItem() == AMItems.WRENCH) {
					if (!playerIn.isCreative()) {
						playerIn.getHeldItem(hand).damageItem(1, playerIn);
					}
					if (worldIn.getTileEntity(pos) instanceof TileEntityAutoMiner) {
						TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
						if (te.ACTIVATED) {
							te.setActivated(false);
						} else {
							if (te.FUEL_AMOUNT > 0) {
								te.setActivated(true);
							}
						}
					}
				} else if (playerIn.getHeldItem(hand).getItem() == Item.getItemFromBlock(Blocks.TORCH)) {
					if (!playerIn.isCreative()) {
						playerIn.getHeldItem(hand).shrink(1);
					}
					if (worldIn.getTileEntity(pos) instanceof TileEntityAutoMiner) {
						TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
						te.addTorch();
					}
				} else if (playerIn.getHeldItem(hand).getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)) {
					if (!playerIn.isCreative()) {
						playerIn.getHeldItem(hand).shrink(1);
					}
					if (worldIn.getTileEntity(pos) instanceof TileEntityAutoMiner) {
						TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
						te.addBlock();
					}
				}
			}
		} else {
			if (hand == EnumHand.MAIN_HAND) {
				if (playerIn.getHeldItem(hand).getItem() == AMItems.WRENCH) {
					Minecraft.getMinecraft().player.swingArm(hand);
				} else if (TileEntityFurnace.isItemFuel(playerIn.getHeldItem(hand))) {
					Minecraft.getMinecraft().player.swingArm(hand);
				}
			}
		}
		return true;

	}

	private int getItemBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			Item item = stack.getItem();
			if (!item.getRegistryName().getResourceDomain().equals("minecraft")) {
				int burnTime = net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
				if (burnTime != 0)
					return burnTime;
			}

			if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB)) {
				return 160;
			} else if (item == Item.getItemFromBlock(Blocks.WOOL)) {
				return 120;
			} else if (item == Item.getItemFromBlock(Blocks.CARPET)) {
				return 80;
			} else if (item == Item.getItemFromBlock(Blocks.LADDER)) {
				return 320;
			} else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON)) {
				return 120;
			} else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD) {
				return 320;
			} else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
				return 16000;
			} else if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName())) {
				return 200;
			} else if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName())) {
				return 200;
			} else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName())) {
				return 200;
			} else if (item == Items.STICK) {
				return 120;
			} else if (item != Items.BOW && item != Items.FISHING_ROD) {
				if (item == Items.SIGN) {
					return 200;
				} else if (item == Items.COAL) {
					return 1600;
				} else if (item == Items.LAVA_BUCKET) {
					return 20000;
				} else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL) {
					if (item == Items.BLAZE_ROD) {
						return 2400;
					} else if (item instanceof ItemDoor && item != Items.IRON_DOOR) {
						return 200;
					} else {
						return item instanceof ItemBoat ? 400 : 0;
					}
				} else {
					return 120;
				}
			} else {
				return 320;
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (worldIn.getTileEntity(pos) instanceof TileEntityAutoMiner) {
				TileEntityAutoMiner te = (TileEntityAutoMiner) worldIn.getTileEntity(pos);
				for (int i = 0; i < te.TORCHES; i++) {
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(),
							new ItemStack(Blocks.TORCH)));
				}
				for (int x = 0; x < te.BLOCKS; x++) {
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(),
							new ItemStack(Blocks.COBBLESTONE)));
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityAutoMiner();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
