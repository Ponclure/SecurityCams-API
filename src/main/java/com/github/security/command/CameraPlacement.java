package com.github.security.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.security.Camera;
import com.github.security.SecurityCams;
import com.github.security.utility.SkullCreation;

import net.md_5.bungee.api.ChatColor;

public class CameraPlacement implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		if (!sender.isOp()) {
			return false;
		}

		Player p = (Player) sender;
		if (args.length == 1) {
			SecurityCams.getCameras().add(new Camera(p, args[0]));
		} else if (args.length == 4) {
			SecurityCams.getCameras().add(new Camera(p, new Location(p.getWorld(), Double.parseDouble(args[2]),
					Double.parseDouble(args[3]), Double.parseDouble(args[4])), args[0]));
		} else if (args.length == 6) {
			SecurityCams.getCameras()
					.add(new Camera(p,
							new Location(p.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
									Double.parseDouble(args[4]), Float.parseFloat(args[5]), Float.parseFloat(args[6])),
							args[0]));
		} else {
			p.sendMessage(ChatColor.RED + "Wrong usage for /setcamera");
			return false;
		}

		return true;
	}

	public ItemStack getSkull(String skull) {
		return SkullCreation.itemWithBase64(SkullCreation.createSkull(), skull);
	}

}
