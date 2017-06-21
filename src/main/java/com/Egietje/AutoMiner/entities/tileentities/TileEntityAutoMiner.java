package com.Egietje.AutoMiner.entities.tileentities;

import java.util.List;
import java.util.Random;

import com.Egietje.AutoMiner.blocks.BlockAutoMiner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityAutoMiner extends TileEntity implements ITickable {

	public int FUEL_AMOUNT = 0;
	public boolean ACTIVATED = false;
	public boolean UPDATE = false;
	public int TICKS = 0;
	public int TORCHES = 0;
	public int PLACE_TORCH = 0;
	public int BLOCKS = 0;

	public TileEntityAutoMiner() {
	}

	public void addFuel(int amount) {
		FUEL_AMOUNT += amount;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void removeFuel() {
		if (FUEL_AMOUNT > 0) {
			FUEL_AMOUNT--;
			IBlockState state = world.getBlockState(pos);
			world.markBlockRangeForRenderUpdate(pos, pos);
			markDirty();
		}
	}

	public void addTorch() {
		TORCHES++;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void removeTorches(int amount) {
		TORCHES -= amount;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void addPlaceTorch() {
		PLACE_TORCH++;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void removePlaceTorches() {
		PLACE_TORCH = 0;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void addBlock() {
		BLOCKS++;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void removeBlock() {
		BLOCKS--;
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void setActivated(boolean active) {
		ACTIVATED = active;
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, state, state, 3);
		world.scheduleBlockUpdate(pos, getBlockType(), 0, 0);
	}

	public void addTick() {
		TICKS++;
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
	}

	public void removeTicks() {
		TICKS = 0;
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.markBlockRangeForRenderUpdate(pos, pos);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("FuelAmount", FUEL_AMOUNT);
		compound.setBoolean("Activated", ACTIVATED);
		compound.setInteger("Ticks", TICKS);
		compound.setInteger("Torches", TORCHES);
		compound.setInteger("PlaceTorch", PLACE_TORCH);
		compound.setInteger("Blocks", BLOCKS);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		FUEL_AMOUNT = compound.getInteger("FuelAmount");
		ACTIVATED = compound.getBoolean("Activated");
		TICKS = compound.getInteger("Ticks");
		TORCHES = compound.getInteger("Torches");
		PLACE_TORCH = compound.getInteger("PlaceTorch");
		BLOCKS = compound.getInteger("Blocks");
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void update() {
		if (ACTIVATED) {
			addTick();
			UPDATE = true;
			removeFuel();
			if (FUEL_AMOUNT <= 0) {
				FUEL_AMOUNT = 0;
				setActivated(false);
				markDirty();
			}
			if (TICKS == 20) {
				if (!world.isRemote) {
					EnumFacing facing = world.getBlockState(pos).getValue(BlockAutoMiner.FACING);
					EnumFacing facingL = facing.rotateYCCW();
					EnumFacing facingR = facing.rotateY();
					Random random = new Random();

					BlockPos positionC = pos.offset(world.getBlockState(pos).getValue(BlockAutoMiner.FACING));
					BlockPos positionL = positionC.offset(facingL);
					BlockPos positionR = positionC.offset(facingR);
					BlockPos positionCU = positionC.up();
					BlockPos positionLU = positionL.up();
					BlockPos positionRU = positionR.up();
					BlockPos positionCUU = positionCU.up();
					BlockPos positionLUU = positionLU.up();
					BlockPos positionRUU = positionRU.up();

					IBlockState stateC = world.getBlockState(positionC);
					IBlockState stateL = world.getBlockState(positionL);
					IBlockState stateR = world.getBlockState(positionR);
					IBlockState stateCU = world.getBlockState(positionCU);
					IBlockState stateLU = world.getBlockState(positionLU);
					IBlockState stateRU = world.getBlockState(positionRU);
					IBlockState stateCUU = world.getBlockState(positionCUU);
					IBlockState stateLUU = world.getBlockState(positionLUU);
					IBlockState stateRUU = world.getBlockState(positionRUU);

					List<ItemStack> stackC = stateC.getBlock().getDrops(world, positionC, stateC, 0);
					List<ItemStack> stackL = stateL.getBlock().getDrops(world, positionL, stateL, 0);
					List<ItemStack> stackR = stateR.getBlock().getDrops(world, positionR, stateR, 0);
					List<ItemStack> stackCU = stateCU.getBlock().getDrops(world, positionCU, stateCU, 0);
					List<ItemStack> stackLU = stateLU.getBlock().getDrops(world, positionLU, stateLU, 0);
					List<ItemStack> stackRU = stateRU.getBlock().getDrops(world, positionRU, stateRU, 0);
					List<ItemStack> stackCUU = stateCUU.getBlock().getDrops(world, positionCUU, stateCUU, 0);
					List<ItemStack> stackLUU = stateLUU.getBlock().getDrops(world, positionLUU, stateLUU, 0);
					List<ItemStack> stackRUU = stateRUU.getBlock().getDrops(world, positionRUU, stateRUU, 0);

					for (int i = 0; i < stackC.size(); i++) {
						ItemStack stack = stackC.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateC, world, positionC) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateC, world,
										positionC) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackL.size(); i++) {
						ItemStack stack = stackL.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateL, world, positionL) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateL, world,
										positionL) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackR.size(); i++) {
						ItemStack stack = stackR.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateR, world, positionR) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateR, world,
										positionR) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackCU.size(); i++) {
						ItemStack stack = stackCU.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateCU, world, positionCU) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateCU, world,
										positionCU) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackLU.size(); i++) {
						ItemStack stack = stackLU.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateLU, world, positionLU) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateLU, world,
										positionLU) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackRU.size(); i++) {
						ItemStack stack = stackRU.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateRU, world, positionRU) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateRU, world,
										positionRU) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackCUU.size(); i++) {
						ItemStack stack = stackCUU.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateCUU, world, positionCUU) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateCUU, world,
										positionCUU) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackLUU.size(); i++) {
						ItemStack stack = stackLUU.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateLUU, world, positionLUU) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateLUU, world,
										positionLUU) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}
					for (int i = 0; i < stackRUU.size(); i++) {
						ItemStack stack = stackRUU.get(i);
						if ((stack.getItem() instanceof ItemBlock) && ((Block.getBlockFromItem(stack.getItem())
								.getBlockHardness(stateRUU, world, positionRUU) < 0F)
								|| (Block.getBlockFromItem(stack.getItem()).getBlockHardness(stateRUU, world,
										positionRUU) > 30F))) {
							continue;
						}
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
					}

					if (!(stateC.getBlockHardness(world, positionC) > 30F)
							&& !(stateC.getBlockHardness(world, positionC) < 0F)) {
						world.setBlockToAir(positionC);
					}
					if (!(stateL.getBlockHardness(world, positionL) > 30F)
							&& !(stateL.getBlockHardness(world, positionL) < 0F)) {
						world.setBlockToAir(positionL);
					}
					if (!(stateR.getBlockHardness(world, positionR) > 30F)
							&& !(stateR.getBlockHardness(world, positionR) < 0F)) {
						world.setBlockToAir(positionR);
					}
					if (!(stateCU.getBlockHardness(world, positionCU) > 30F)
							&& !(stateCU.getBlockHardness(world, positionCU) < 0F)) {
						world.setBlockToAir(positionCU);
					}
					if (!(stateLU.getBlockHardness(world, positionLU) > 30F)
							&& !(stateLU.getBlockHardness(world, positionLU) < 0F)) {
						world.setBlockToAir(positionLU);
					}
					if (!(stateRU.getBlockHardness(world, positionRU) > 30F)
							&& !(stateRU.getBlockHardness(world, positionRU) < 0F)) {
						world.setBlockToAir(positionRU);
					}
					if (!(stateCUU.getBlockHardness(world, positionCUU) > 30F)
							&& !(stateCUU.getBlockHardness(world, positionCUU) < 0F)) {
						world.setBlockToAir(positionCUU);
					}
					if (!(stateLUU.getBlockHardness(world, positionLUU) > 30F)
							&& !(stateLUU.getBlockHardness(world, positionLUU) < 0F)) {
						world.setBlockToAir(positionLUU);
					}
					if (!(stateRUU.getBlockHardness(world, positionRUU) > 30F)
							&& !(stateRUU.getBlockHardness(world, positionRUU) < 0F)) {
						world.setBlockToAir(positionRUU);
					}

					if ((TORCHES >= 2) && (PLACE_TORCH >= 6)) {
						BlockPos positionLLU = positionLU.offset(facingL);
						BlockPos positionRRU = positionRU.offset(facingR);

						if (world.getBlockState(positionLLU).isSideSolid(world, positionLLU, facingR)
								&& (world.getBlockState(positionLU).getMaterial() == Material.AIR)) {
							if (world.setBlockState(positionLU,
									Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, facingR))) {
								removeTorches(1);
								removePlaceTorches();
							}
						}
						if (world.getBlockState(positionRRU).isSideSolid(world, positionRRU, facingL)
								&& (world.getBlockState(positionRU).getMaterial() == Material.AIR)) {
							if (world.setBlockState(positionRU,
									Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, facingL))) {
								removeTorches(1);
								removePlaceTorches();
							}
						}
					} else {
						addPlaceTorch();
					}

					BlockPos positionD = positionC.down();
					if (BLOCKS > 0) {
						if (world.isAirBlock(positionD)) {
							world.setBlockState(positionD, Blocks.COBBLESTONE.getDefaultState());
							removeBlock();
						}
					} else {
						if (world.isAirBlock(positionD)) {
							setActivated(false);
							removeTicks();
						}
					}
					if (world.isAirBlock(pos.down())) {
						setActivated(false);
						removeTicks();
					}
					if (world.getBlockState(positionC).getMaterial().isSolid()) {
						setActivated(false);
						removeTicks();
					}
				}
			}
			if (TICKS == 40) {
				if (!world.isRemote) {
					BlockPos position = pos.offset(world.getBlockState(pos).getValue(BlockAutoMiner.FACING));
					BlockPos position1 = pos;
					world.setBlockState(position, world.getBlockState(pos));
					TileEntityAutoMiner te = new TileEntityAutoMiner();
					te.ACTIVATED = ACTIVATED;
					te.FUEL_AMOUNT = FUEL_AMOUNT;
					te.TICKS = TICKS;
					te.UPDATE = UPDATE;
					te.TORCHES = TORCHES;
					te.PLACE_TORCH = PLACE_TORCH;
					te.BLOCKS = BLOCKS;
					ACTIVATED = false;
					TICKS = 0;
					UPDATE = false;
					TORCHES = 0;
					PLACE_TORCH = 0;
					BLOCKS = 0;
					world.setTileEntity(position, te);
					world.setBlockToAir(position1);
				}
			}
		} else {
			if (UPDATE) {
				IBlockState state = world.getBlockState(pos);
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
			UPDATE = false;
		}
		if (TICKS >= 40) {
			removeTicks();
		}
	}

}
