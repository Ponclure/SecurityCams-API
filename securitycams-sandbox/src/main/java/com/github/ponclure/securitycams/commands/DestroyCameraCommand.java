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

package com.github.ponclure.securitycams.commands;

import com.github.ponclure.securitycams.CameraManager;
import com.github.ponclure.securitycams.CamerasSandbox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DestroyCameraCommand implements TabExecutor {

	private final CamerasSandbox plugin;
	private final CameraManager cameraManager;

	public DestroyCameraCommand(final CamerasSandbox plugin, final PluginCommand command) {
		if (command == null) {
			throw new RuntimeException();
		}
		command.setExecutor(this);
		command.setTabCompleter(this);
		this.plugin = plugin;
		this.cameraManager = plugin.getCameraManager();
	}

	@Override
	public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can run this command!");
			return true;
		}

		if (!sender.isOp()) {
			sender.sendMessage("You do not have sufficient permissions!");
			return true;
		}

		final Player player = (Player)sender;
		if (args.length == 1) {
			cameraManager.removeCamera(args[0]);
		}

		else {
			player.sendMessage("Usage: /destroycamera <camera>");
		}

		return true;
	}

	@Override
	public @NotNull List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String alias, @NotNull final String[] args) {
		if (args.length != 1) {
			return Collections.emptyList();
		}

		return plugin.filterNames(args[0]);
	}
}
