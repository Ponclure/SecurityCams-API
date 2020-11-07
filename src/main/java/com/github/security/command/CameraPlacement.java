package com.github.security.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.security.event.CameraSetEvent;
import com.github.security.utility.SkullCreation;

import net.md_5.bungee.api.ChatColor;

//TESTING PURPOSES
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
		Location loc;
		if (args.length == 1) {
			loc = p.getLocation();
		} else if (args.length == 4) {
			loc = new Location(p.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]),
					Double.parseDouble(args[3]));
		} else if (args.length == 6) {
			loc = new Location(p.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]),
					Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
		} else {
			p.sendMessage(ChatColor.RED + "Wrong usage for /setcamera");
			return false;
		}
		
		Bukkit.getPluginManager().callEvent(new CameraSetEvent(p, args[0], loc));

		return true;
	}

	public ItemStack getSkull(String skull) {
		return SkullCreation.itemWithBase64(SkullCreation.createSkull(), skull);
	}

}
