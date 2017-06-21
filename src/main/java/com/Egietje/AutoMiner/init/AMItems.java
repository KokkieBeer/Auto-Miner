package com.Egietje.AutoMiner.init;

import com.Egietje.AutoMiner.utils.AMUtils;

import net.minecraft.item.Item;

public class AMItems {
	public static final Item STEEL_INGOT = new Item().setCreativeTab(AMTabs.AM_TAB).setRegistryName("steel_ingot")
			.setUnlocalizedName("steel_ingot");
	public static final Item WRENCH = new Item().setCreativeTab(AMTabs.AM_TAB).setMaxDamage(128).setMaxStackSize(1)
			.setRegistryName("wrench").setUnlocalizedName("wrench");
	public static final Item[] ITEMS = new Item[] { STEEL_INGOT, WRENCH };
}
