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

package com.github.ponclure.securitycams;

import com.github.ponclure.securitycams.event.CameraDestroyEvent;
import com.github.ponclure.securitycams.event.CameraEnterEvent;
import com.github.ponclure.securitycams.event.CameraExitEvent;
import com.github.ponclure.securitycams.event.CameraSetEvent;
import com.github.ponclure.securitycams.listeners.CameraMovement;
import com.github.ponclure.securitycams.listeners.PlayerConserver;
import com.github.ponclure.securitycams.model.Camera;
import com.github.ponclure.securitycams.model.CameraUser;
import com.github.ponclure.simplenpcframework.SimpleNPCFramework;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class CameraManager {

	private final Plugin plugin;
	private final SimpleNPCFramework npcFramework;

	private final File knownCamerasFile;
	private final YamlConfiguration knownCamerasYaml = new YamlConfiguration();
	private final Set<UUID> knownCameras = new LinkedHashSet<>();

	private final Map<String, Camera> cameras = new LinkedHashMap<>();
	private final Map<UUID, CameraUser> watchers = new LinkedHashMap<>();

	public CameraManager(final JavaPlugin plugin, final File index) throws InvalidConfigurationException, IOException {
		this.plugin = plugin;
		this.npcFramework = new SimpleNPCFramework(plugin);

		index.getParentFile().mkdirs();
		index.createNewFile();
		knownCamerasFile = index;
		knownCamerasYaml.load(index);
		loadCameras();

		new CameraMovement(plugin, this);
		new PlayerConserver(plugin, this);
	}

	// TODO: load/save armor stands UUIDs and their locations into the index file, that would be done asynchronously with a lock.
	// load would cache on camera manager creation, save would trigger on each camera addition/deletion.
	private void loadCameras() {

	}

	public void addWatcher(final Player player, final Camera camera) {
		if (watchers.containsKey(player.getUniqueId())) {
			return;
		}

		final CameraEnterEvent event = new CameraEnterEvent(player, camera);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			final CameraUser user = new CameraUser(player, camera, npcFramework);
			watchers.put(player.getUniqueId(), user);
		}
	}

	public CameraUser getWatcher(final Player player) {
		return watchers.get(player.getUniqueId());
	}

	public boolean isWatching(final Player player) {
		return watchers.containsKey(player.getUniqueId());
	}

	public boolean removeWatcher(final Player player, final boolean force) {
		final CameraUser user = watchers.get(player.getUniqueId());
		if (user == null) {
			return false;
		}

		final CameraExitEvent event = new CameraExitEvent(player, user);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled() || force) {
			watchers.remove(player.getUniqueId());
		}
		return !event.isCancelled();
	}

	public Set<String> getCameraNames() {
		return Collections.unmodifiableSet(cameras.keySet());
	}

	public Camera getCamera(final String name) {
		return cameras.get(name.toLowerCase());
	}

	public boolean isCamera(final Entity entity) {
		return knownCameras.contains(entity.getUniqueId());
	}

	public Camera addCamera(final Location location, final String name) {
		if (cameras.containsKey(name.toLowerCase())) {
			return cameras.get(name.toLowerCase());
		}

		final CameraSetEvent event = new CameraSetEvent(name, location);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			final Camera camera = Camera.create(location, name);
			cameras.put(name.toLowerCase(), camera);
			return camera;
		}
		return null;
	}

	public boolean removeCamera(final String name) {
		final Camera camera = cameras.get(name.toLowerCase());
		if (camera == null) {
			return false;
		}

		final CameraDestroyEvent event = new CameraDestroyEvent(camera);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			camera.destroy();
			cameras.remove(name.toLowerCase());
		}
		return !event.isCancelled();
	}
}