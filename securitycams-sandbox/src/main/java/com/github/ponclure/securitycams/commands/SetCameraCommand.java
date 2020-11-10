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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

public class SetCameraCommand implements TabExecutor {

    private final CameraManager cameraManager;

    public SetCameraCommand(final CamerasSandbox plugin, final PluginCommand command) {
	if (command == null) {
	    throw new RuntimeException();
	}
	command.setExecutor(this);
	this.cameraManager = plugin.getCameraManager();
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
	    @NotNull final String label, @NotNull final String[] args) {
	if (!(sender instanceof Player)) {
	    sender.sendMessage("Only players can run this command!");
	    return true;
	}
	if (!sender.isOp()) { // perms check go brrrrrrrrr
	    sender.sendMessage("You do not have sufficient permissions!");
	    return true;
	}
	
	final Player player = (Player) sender;
	final Location location;
	
	switch (args.length) {
	case 1:
	    location = player.getLocation();
	    break;

	case 4:
	    location = new Location(player.getWorld(), parseDouble(args[1]), parseDouble(args[2]),
		    parseDouble(args[3]));
	    break;

	case 6:
	    location = new Location(player.getWorld(), parseDouble(args[1]), parseDouble(args[2]), parseDouble(args[3]),
		    parseFloat(args[4]), parseFloat(args[5]));
	    break;

	default:
	    player.sendMessage("Usage: /setcamera <name> [<x> <y> <z> [<yaw> <pitch>]]");
	    return false;
	}

	cameraManager.addCamera(location, args[0]);
	return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command,
	    @NotNull final String alias, @NotNull final String[] args) {
	return Collections.emptyList();
    }
}
