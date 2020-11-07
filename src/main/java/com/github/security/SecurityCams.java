package com.github.security;

import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.security.camera.Camera;
import com.github.security.command.CameraDeletion;
import com.github.security.command.CameraNPCHandler;
import com.github.security.command.CameraPlacement;
import com.github.security.event.build.CameraMovement;
import com.github.security.event.build.PlayerConserver;

import net.md_5.bungee.api.ChatColor;

public class SecurityCams extends JavaPlugin {

	public static FileConfiguration config;

	public static SecurityCams security;
	public static Logger logger;

	@Override
	public void onEnable() {
		security = this;
		logger = Bukkit.getLogger();
		config = SecurityCams.getPlugin().getConfig();
		logger.info(ChatColor.GREEN + "Security Cams API is Loading Up");

		if (config.get("enable-api-commands") == null) {
			config.addDefault("enable-api-commands", true);
			config.options().copyDefaults(true);
			saveConfig();
		} else {
			boolean status = (boolean)config.get("enable-api-commands");
			if (!status) {
				getServer().getPluginManager().disablePlugin(this);
			}
		}

		Bukkit.getPluginCommand("usecamera").setExecutor(new CameraNPCHandler());
		Bukkit.getPluginCommand("setcamera").setExecutor(new CameraPlacement());
		Bukkit.getPluginCommand("deletecamera").setExecutor(new CameraDeletion());
		
		getServer().getPluginManager().registerEvents(new CameraMovement(), this);
		getServer().getPluginManager().registerEvents(new PlayerConserver(), this);
	}

	@Override
	public void onDisable() {
		logger.info(ChatColor.GREEN + "Security Cams API is Shutting Down");
	}

	public static SecurityCams getPlugin() {
		return security;
	}

	public static Set<Camera> getCameras() {
		return CameraMap.cameras;
	}

}
