package com.Egietje.AutoMiner.tabs;

import com.Egietje.AutoMiner.init.AMItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class AMTab extends CreativeTabs {

	public AMTab(String label) {
		super(label);
		
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(AMItems.STEEL_INGOT);
	}

}
