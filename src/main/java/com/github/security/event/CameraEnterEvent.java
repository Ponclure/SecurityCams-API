package com.github.security.event;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import com.github.security.camera.Camera;
import com.github.security.camera.CameraUser;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;

public class CameraEnterEvent extends Event implements Cancellable {

	private final HandlerList HANDLERS = new HandlerList();
	private final Player player;
	private final Camera camera;

	private boolean isCancelled;

	public CameraEnterEvent(Player p, Camera c) {
		this.player = p;
		this.camera = c;
		if (!isCancelled) {
			spawnNPC();
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

	@SuppressWarnings("deprecation")
	public void spawnNPC() {
		PlayerInventory inv = player.getInventory();
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
		npc.setFlyable(true);
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, inv.getHelmet());
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE, inv.getChestplate());
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS, inv.getLeggings());
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, inv.getBoots());
		npc.getOrAddTrait(Inventory.class).setContents(inv.getContents());
		npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, player.getName());
		npc.spawn(player.getLocation());

		camera.getWatchers().add(new CameraUser(player, npc));

		ArmorStand stand = camera.getStand();
		Location eyelocation = stand.getEyeLocation();
		Vector direction = stand.getLocation().getDirection();
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(eyelocation.add(direction));
		
	}

}
