package com.Egietje.AutoMiner;

import com.Egietje.AutoMiner.handler.*;
import com.Egietje.AutoMiner.init.*;
import com.Egietje.AutoMiner.proxy.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = "[" + Reference.MC_VERSION + "]")
public class AutoMiner {

	@Instance
	public static AutoMiner INSTANCE = new AutoMiner();

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static IProxy PROXY;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		AMRegisters.registerPreInit();

		PROXY.setTitle("Minecraft - " + Reference.MC_VERSION + " AutoMiner - " + Reference.VERSION);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {

	}
}
