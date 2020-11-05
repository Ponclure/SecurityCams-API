package com.github.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class SecurityCams extends JavaPlugin {
	
	public static Map<UUID, NPC> npcs = new HashMap<>();
	public static Set<Camera> cameras = new HashSet<>();

	private static Logger logger = Bukkit.getLogger();
	private static SecurityCams security = JavaPlugin.getPlugin(SecurityCams.class);

	@Override
	public void onEnable() {
		logger.info(ChatColor.GREEN + "Security Cams API is Loading Up");
	}

	@Override
	public void onDisable() {

	}
	
	public static SecurityCams getPlugin() {
		return security;
	}
	
	public static Set<Camera> getCameras() {
		return cameras;
	}
	
	public static Map<UUID, NPC> getNPCs() {
		return npcs;
	}

}
