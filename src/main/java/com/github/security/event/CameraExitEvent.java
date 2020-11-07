package com.github.security.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.github.security.camera.Camera;
import com.github.security.camera.CameraUser;

public class CameraExitEvent extends Event implements Cancellable {

	private final HandlerList HANDLERS = new HandlerList();
	private final Player player;
	private final Camera camera;

	private boolean isCancelled;

	public CameraExitEvent(Player p, Camera c) {
		this.player = p;
		this.camera = c;
		if (!isCancelled) {
			despawnNPC();
		}
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public void despawnNPC() {
		CameraUser user = camera.containsWatcher(player.getUniqueId());
		camera.getWatchers().remove(user);
		user.getNpc().despawn();
		player.teleport(user.getOrigLoc());
		player.setGameMode(user.getOrigMode());
	}

}
