package com.github.security.command;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.PlayerInventory;

import com.github.security.Camera;
import com.github.security.SecurityCams;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.md_5.bungee.api.ChatColor;
import net.citizensnpcs.api.trait.trait.Inventory;

public class CameraNPCHandler implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if (!(sender instanceof Player)) {
			return false;
		}

		if (!sender.isOp()) {
			return false;
		}

		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();
		PlayerInventory inv = p.getInventory();
		
		Camera camera = null;
		for (Camera c : SecurityCams.getCameras()) {
			if (c.getName().equals(args[0])) {
				camera = c;
				break;
			}
		}
		
		if (camera == null) {
			sender.sendMessage(ChatColor.DARK_RED + "The camera name is not valid");
			return false;
		}

		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "fullwall");
		npc.setFlyable(true);
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.HELMET, inv.getHelmet());
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.CHESTPLATE, inv.getChestplate());
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.LEGGINGS, inv.getLeggings());
		npc.getOrAddTrait(Equipment.class).set(EquipmentSlot.BOOTS, inv.getBoots());
		npc.getOrAddTrait(Inventory.class).setContents(inv.getContents());
		npc.teleport(p.getLocation(), TeleportCause.COMMAND);
		npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, p.getName());
		
		p.setGameMode(GameMode.SPECTATOR);
		p.teleport(camera.getStand());
		
		SecurityCams.getNPCs().put(uuid, npc);
		
		p.sendMessage(ChatColor.GREEN + "Press Shift to Exit the Camera");
		
		return true;
		
	}

}
