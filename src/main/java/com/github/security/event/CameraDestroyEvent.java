package com.github.security.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.github.security.SecurityCams;
import com.github.security.camera.Camera;

public class CameraDestroyEvent extends Event implements Cancellable {
	
	private final HandlerList HANDLERS = new HandlerList();
	private final Player player;
	private final Camera camera;

	private boolean isCancelled;

	public CameraDestroyEvent(Player p, String n) {
		this.player = p;
		this.camera = getCamera(n);
		if (!isCancelled) {
			SecurityCams.getCameras().remove(camera);
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

	public Camera getCamera(String s) {
		for (Camera c : SecurityCams.getCameras()) {
			if (c.getName().equals(s)) {
				return c;
			}
		}
		return null;
	}

}
