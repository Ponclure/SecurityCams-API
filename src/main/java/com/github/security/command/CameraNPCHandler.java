package com.github.security.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.security.SecurityCams;
import com.github.security.camera.Camera;
import com.github.security.event.CameraEnterEvent;

import net.md_5.bungee.api.ChatColor;

//TESTING PURPOSES
public class CameraNPCHandler implements CommandExecutor {

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

		Bukkit.getPluginManager().callEvent(new CameraEnterEvent(p, camera));
		
		return true;
		
	}

}
