package com.github.security;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.security.utility.SkullCreation;

import net.md_5.bungee.api.ChatColor;

public class Camera {

	private final ArmorStand stand;
	private final UUID uuid;
	private final String name;
	private Block moniter;
	
	public Camera(Player p, String name) {
		this.stand = (ArmorStand) p.getLocation().getWorld().spawn(p.getLocation(), ArmorStand.class);
		this.stand.setGravity(false);
		this.stand.setCanPickupItems(false);
		this.stand.setArms(false);
		this.stand.setCustomName("Security Camera");
		this.stand.getEquipment().setHelmet(getSkull(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RiODM1ODY1NDI5MzRmOGMzMjMxYTUyODRmMjQ4OWI4NzY3ODQ3ODQ1NGZjYTY5MzU5NDQ3NTY5ZjE1N2QxNCJ9fX0="));
		this.stand.setVisible(false);
		this.uuid = stand.getUniqueId();
		this.name = name;
		p.sendMessage(ChatColor.GREEN + "Succesfully summoned Camera");
	}
	
	public Camera(Player p, Location loc, String name) {
		this.stand = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
		this.stand.setGravity(false);
		this.stand.setCanPickupItems(false);
		this.stand.setArms(false);
		this.stand.setCustomName("Security Camera");
		this.stand.getEquipment().setHelmet(getSkull(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RiODM1ODY1NDI5MzRmOGMzMjMxYTUyODRmMjQ4OWI4NzY3ODQ3ODQ1NGZjYTY5MzU5NDQ3NTY5ZjE1N2QxNCJ9fX0="));
		this.stand.setVisible(false);
		this.uuid = stand.getUniqueId();
		this.name = name;
		p.sendMessage(ChatColor.GREEN + "Succesfully summoned Camera");
	}
	
	public ItemStack getSkull(String skull) {
		return SkullCreation.itemWithBase64(SkullCreation.createSkull(), skull);
	}

	public ArmorStand getStand() {
		return stand;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}
	
	public Block getMoniter() {
		return moniter;
	}

	public void setMoniter(Block moniter) {
		this.moniter = moniter;
	}
	
}
