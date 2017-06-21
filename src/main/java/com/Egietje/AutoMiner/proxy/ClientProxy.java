package com.Egietje.AutoMiner.proxy;

import com.Egietje.AutoMiner.entities.tileentities.TileEntityAutoMiner;

import net.minecraftforge.fml.common.registry.GameRegistry;

import org.lwjgl.opengl.Display;

public class ClientProxy implements IProxy {

	@Override
	public void setTitle(String title) {
		Display.setTitle(title);
	}
}
