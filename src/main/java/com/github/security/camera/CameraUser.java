package com.github.security.camera;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.npc.NPC;

public class CameraUser {

	private final UUID player;
	private final NPC npc;
	private final Location origLoc;
	private final GameMode origMode;

	public CameraUser(Player p, NPC npc) {
		this.player = p.getUniqueId();
		this.npc = npc;
		this.origLoc = p.getLocation();
		this.origMode = p.getGameMode();
	}

	public UUID getPlayerUUID() {
		return player;
	}
	
	public NPC getNpc() {
		return npc;
	}

	public Location getOrigLoc() {
		return origLoc;
	}

	public GameMode getOrigMode() {
		return origMode;
	}

}
