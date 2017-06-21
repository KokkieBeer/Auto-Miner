package com.Egietje.AutoMiner.handler;

import com.Egietje.AutoMiner.Reference;
import com.Egietje.AutoMiner.init.AMBlocks;
import com.Egietje.AutoMiner.init.AMItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Reference.ID, value = Side.CLIENT)
public class AMClientHandler {
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Block block : AMBlocks.BLOCKS) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
					new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
		
		for (Item item : AMItems.ITEMS) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
