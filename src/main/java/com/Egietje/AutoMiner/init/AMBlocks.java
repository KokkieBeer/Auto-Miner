package com.Egietje.AutoMiner.init;

import com.Egietje.AutoMiner.Reference;
import com.Egietje.AutoMiner.blocks.BlockAutoMiner;
import com.Egietje.AutoMiner.utils.AMUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class AMBlocks {
	public static final Block STEEL_BLOCK = new Block(Material.IRON).setCreativeTab(AMTabs.AM_TAB).setHardness(8.0F)
			.setResistance(15.0F).setRegistryName("steel_block").setUnlocalizedName("steel_block");
	public static final Block AUTO_MINER = new BlockAutoMiner().setCreativeTab(AMTabs.AM_TAB).setHardness(7.0F)
			.setResistance(20.0F).setRegistryName("auto_miner").setUnlocalizedName("auto_miner");
	public static final Block[] BLOCKS = new Block[] { STEEL_BLOCK, AUTO_MINER };
}
