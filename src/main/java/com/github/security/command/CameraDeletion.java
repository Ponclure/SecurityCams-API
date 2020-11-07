package com.github.security.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.security.event.CameraDestroyEvent;
import net.md_5.bungee.api.ChatColor;

// TESTING PURPOSES
public class CameraDeletion implements CommandExecutor {

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
			Bukkit.getPluginManager().callEvent(new CameraDestroyEvent(p, args[0]));
		} else {
			p.sendMessage(ChatColor.RED + "Wrong usage for /destroycamera");
			return false;
		}

		return true;
	}
	
}
