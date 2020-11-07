package com.github.security.event.build;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.security.SecurityCams;
import com.github.security.camera.Camera;
import com.github.security.camera.CameraUser;

import net.md_5.bungee.api.ChatColor;

public class PlayerConserver implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		for (Camera c : SecurityCams.getCameras()) {
			CameraUser user = c.containsWatcher(p.getUniqueId());
			if (user != null) {
				user.getNpc().despawn();
				c.getWatchers().remove(user);
				p.teleport(user.getOrigLoc());
				p.setGameMode(user.getOrigMode());
				p.sendMessage(ChatColor.RED + "Exited Camera View");
				return;
			}
		}
	}

}
