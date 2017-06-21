package com.Egietje.AutoMiner.handler;

import java.net.Proxy;

import com.Egietje.AutoMiner.AutoMiner;
import com.Egietje.AutoMiner.Reference;
import com.Egietje.AutoMiner.init.AMBlocks;
import com.Egietje.AutoMiner.init.AMItems;
import com.Egietje.AutoMiner.utils.AMUtils;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;

@Mod.EventBusSubscriber(modid = Reference.ID)
public class AMServerHandler {
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		AMBlocks.STEEL_BLOCK.setHarvestLevel("pickaxe", 2);
		AMBlocks.AUTO_MINER.setHarvestLevel("pickaxe", 1);
		event.getRegistry().registerAll(AMBlocks.BLOCKS);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(AMItems.ITEMS);
		for (Block block : AMBlocks.BLOCKS) {
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName())
					.setUnlocalizedName(block.getUnlocalizedName()));
		}
	}
}
