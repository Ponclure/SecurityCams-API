/*
 * This file is part of the SecurityCams (https://github.com/Ponclure/SecurityCams).
 *
 *  Copyright (c) 2020 Ponclure team and contributors
 *
 *  SecurityCams is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  SecurityCams is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.ponclure.securitycams.model;

import com.github.ponclure.simplenpcframework.SimpleNPCFramework;
import com.github.ponclure.simplenpcframework.api.NPC;
import com.github.ponclure.simplenpcframework.api.skin.AsyncSkinFetcher;
import com.github.ponclure.simplenpcframework.api.state.NPCSlot;
import com.github.ponclure.simplenpcframework.internal.NPCBase;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collections;

public class CameraUser {

	private NPCBase npc;
	private final Player player;
	private final Camera camera;
	private final Location previousLocation;
	private final GameMode previousGamemode;

	public CameraUser(final Player player, final Camera camera, final SimpleNPCFramework npcFramework) {
		this.player = player;
		this.camera = camera;
		this.previousLocation = player.getLocation();
		this.previousGamemode = player.getGameMode();

		PaperLib.teleportAsync(player, camera.getViewpoint(), PlayerTeleportEvent.TeleportCause.SPECTATE).thenRun(() -> {
			replacePlayer(player, previousLocation, npcFramework);
			player.setGameMode(GameMode.SPECTATOR);
		});
	}

	private void replacePlayer(final Player player, final Location oldLocation, final SimpleNPCFramework npcFramework) {
		final PlayerInventory inv = player.getInventory();

		npc = (NPCBase)npcFramework.createNPC(Collections.singletonList(player.getName()));
		npc.setItem(NPCSlot.HELMET, inv.getHelmet());
		npc.setItem(NPCSlot.CHESTPLATE, inv.getChestplate());
		npc.setItem(NPCSlot.LEGGINGS, inv.getLeggings());
		npc.setItem(NPCSlot.BOOTS, inv.getBoots());
		npc.setItem(NPCSlot.MAINHAND, inv.getItemInMainHand());
		npc.setItem(NPCSlot.OFFHAND, inv.getItemInOffHand());
		npc.setLocation(oldLocation);
		AsyncSkinFetcher.fetchSkinFromUuidAsync(player.getUniqueId(), skin -> {
			npc.setSkin(skin);
			npc.create();
			Bukkit.getScheduler().runTask(npcFramework.getPlugin(), () -> {
				Bukkit.getOnlinePlayers().forEach(npc::show);
			});
		});
	}

	public void returnBack() {
		npc.destroy();
		PaperLib.teleportAsync(player, previousLocation, PlayerTeleportEvent.TeleportCause.PLUGIN).thenRun(() -> {
			player.setGameMode(previousGamemode);
		});
	}

	public Player getPlayer() {
		return player;
	}

	public Camera getCamera() {
		return camera;
	}

	public NPC getNpc() {
		return npc;
	}

	public Location getPreviousLocation() {
		return previousLocation.clone();
	}

	public GameMode getPreviousGamemode() {
		return previousGamemode;
	}
}
