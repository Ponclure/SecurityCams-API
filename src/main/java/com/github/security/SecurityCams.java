package com.github.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.security.command.CameraNPCHandler;
import com.github.security.command.CameraPlacement;
import com.github.security.event.CameraMovement;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class SecurityCams extends JavaPlugin {

	public static Map<UUID, NPC> npcs = new HashMap<>();
	public static Set<Camera> cameras = new HashSet<>();

	private static Logger logger = Bukkit.getLogger();
	private static SecurityCams security;

	@Override
	public void onEnable() {
		security = this;
		logger.info(ChatColor.GREEN + "Security Cams API is Loading Up");
		Bukkit.getPluginCommand("usecamera").setExecutor(new CameraNPCHandler());
		Bukkit.getPluginCommand("setcamera").setExecutor(new CameraPlacement());
		getServer().getPluginManager().registerEvents(new CameraMovement(), this);
	}

	@Override
	public void onDisable() {
		logger.info(ChatColor.GREEN + "Security Cams API is Shutting Down");
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
