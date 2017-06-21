package com.Egietje.AutoMiner.init;

import com.Egietje.AutoMiner.entities.tileentities.TileEntityAutoMiner;
import com.Egietje.AutoMiner.utils.AMUtils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AMRegisters {

	private static void registerEntities() {
		GameRegistry.registerTileEntity(TileEntityAutoMiner.class, "AutoMiner");
	}

	public static void registerPreInit() {
		registerEntities();
	}

}
