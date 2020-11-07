package com.github.security.event.build;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.github.security.SecurityCams;
import com.github.security.camera.Camera;
import com.github.security.event.CameraExitEvent;
import com.github.security.event.CameraMoveEvent;
import com.github.security.camera.CameraUser;

public class CameraMovement implements Listener {

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		for (Camera c : SecurityCams.getCameras()) {
			if (c.containsWatcher(p.getUniqueId()) != null) {
				event.setCancelled(true);
				Bukkit.getPluginManager().callEvent(new CameraMoveEvent(p, c));
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		for (Camera c : SecurityCams.getCameras()) {
			CameraUser user = c.containsWatcher(p.getUniqueId());
			if (user != null) {
				event.setCancelled(true);
				Bukkit.getPluginManager().callEvent(new CameraExitEvent(p, c));
				return;
			}
		}

	}

}
