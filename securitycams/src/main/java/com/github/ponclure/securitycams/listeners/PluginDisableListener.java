package com.github.ponclure.securitycams.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class PluginDisableListener implements Listener {

	private final Plugin plugin;
	private final Runnable shutdown;

	public PluginDisableListener(final Plugin plugin, final Runnable shutdown) {
		this.plugin = plugin;
		this.shutdown = shutdown;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPluginDisable(final PluginDisableEvent event) {
		if (event.getPlugin().equals(plugin)) {
			shutdown.run();
		}
	}
}
