package com.github.security.event;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.github.security.SecurityCams;

public class CameraMovement implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		if (!SecurityCams.npcs.containsKey(uuid)) {
			return;
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		UUID uuid = p.getUniqueId();
		if (!SecurityCams.npcs.containsKey(uuid)) {
			return;
		}
		p.teleport(SecurityCams.getNPCs().get(uuid).getStoredLocation());
		SecurityCams.npcs.remove(uuid);
		
	}

}
