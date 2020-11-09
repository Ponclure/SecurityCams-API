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

package com.github.ponclure.securitycams.listeners;

import com.github.ponclure.securitycams.CameraManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class CameraMovement implements Listener {

	private final CameraManager manager;

	public CameraMovement(final Plugin plugin, final CameraManager manager) {
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		event.setCancelled(event.isCancelled() || manager.isWatching(player));
	}

	@EventHandler
	public void onPlayerToggleSneakEvent(final PlayerToggleSneakEvent event) {
		final Player player = event.getPlayer();
		if (!manager.isWatching(player) || !event.isSneaking()) {
			return;
		}

		event.setCancelled(event.isCancelled() || !manager.removeWatcher(player, false));
	}
}
