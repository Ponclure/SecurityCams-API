package com.github.security.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import com.github.security.event.CameraDestroyEvent;
import com.github.security.event.CameraSetEvent;
import com.github.security.utility.SkullCreation;

public class Camera {

	private List<CameraUser> watchers;

	private final ArmorStand stand;
	private final String name;

	public Camera(Player caller, Location loc, String name) {
		this.watchers = new ArrayList<>();
		this.stand = getDefaultStand(loc);
		this.name = name;
		Bukkit.getPluginManager().callEvent(new CameraSetEvent(caller, name, stand.getLocation()));
	}

	public ArmorStand getDefaultStand(Location loc) {
		ArmorStand stand = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
		stand.setGravity(false);
		stand.setCanPickupItems(false);
		stand.setArms(false);
		stand.setCustomName("Security Camera");
		stand.getEquipment().setHelmet(SkullCreation.itemWithBase64(SkullCreation.createSkull(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RiODM1ODY1NDI5MzRmOGMzMjMxYTUyODRmMjQ4OWI4NzY3ODQ3ODQ1NGZjYTY5MzU5NDQ3NTY5ZjE1N2QxNCJ9fX0="));
		stand.setVisible(false);

		return stand;
	}

	public List<CameraUser> getWatchers() {
		return watchers;
	}

	public CameraUser containsWatcher(UUID uuid) {
		for (CameraUser user : watchers) {
			if (user.getPlayerUUID() == uuid) {
				return user;
			}
		}
		return null;
	}

	public ArmorStand getStand() {
		return stand;
	}

	public String getName() {
		return name;
	}
	
	public void destroy(Player p) {
		Bukkit.getPluginManager().callEvent(new CameraDestroyEvent(p, name));
	}

}