package com.github.security.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.security.SecurityCams;
import com.github.security.camera.Camera;

public class CameraSetEvent extends Event implements Cancellable {

	private final HandlerList HANDLERS = new HandlerList();
	private final Player player;
	private final Camera camera;

	private boolean isCancelled;

	public CameraSetEvent(Player p, String name, Location loc) {
		this.player = p;
		this.camera = new Camera(p, loc, name);
		if (!isCancelled) {
			SecurityCams.getCameras().add(camera);
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

}
