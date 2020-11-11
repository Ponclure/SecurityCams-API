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

import com.github.ponclure.securitycams.util.SkullCreation;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.UUID;

import static java.lang.Math.toRadians;

public class Camera {

	private static final ItemStack CAMERA_HEAD = SkullCreation.itemWithBase64(SkullCreation.createSkull(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RiODM1ODY1NDI5MzRmOGMzMjMxYTUyODRmMjQ4OWI4NzY3ODQ3ODQ1NGZjYTY5MzU5NDQ3NTY5ZjE1N2QxNCJ9fX0=");
	private static final Vector TWO = new Vector(2, 2, 2);

	public static Camera create(final Location location, final String name) {
		return new Camera(location, name, getStandModel(location).getUniqueId());
	}

	public static Camera createVirtual(final Location location, final String name, final UUID uuid) {
		return new Camera(location, name, uuid);
	}

	private static ArmorStand getStandModel(final Location location) {
		return location.getWorld().spawn(location, ArmorStand.class, armorStand -> {
			armorStand.setGravity(false);
			armorStand.setArms(false);
			armorStand.setVisible(false);
			armorStand.setBasePlate(false);
			armorStand.setPersistent(true);
			armorStand.setInvulnerable(true);
			armorStand.setCustomNameVisible(true);
			armorStand.setCustomName("Security Camera");
			armorStand.getEquipment().setHelmet(CAMERA_HEAD);

			armorStand.setBodyPose(EulerAngle.ZERO);
			armorStand.setHeadPose(new EulerAngle(toRadians(location.getPitch()), 0, 0));
			armorStand.setLeftArmPose(EulerAngle.ZERO);
			armorStand.setLeftLegPose(EulerAngle.ZERO);
			armorStand.setRightArmPose(EulerAngle.ZERO);
			armorStand.setRightLegPose(EulerAngle.ZERO);

			for (final EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
				for (final ArmorStand.LockType lockType : ArmorStand.LockType.values()) {
					armorStand.addEquipmentLock(equipmentSlot, lockType);
				}
			}
		});
	}

	private final Location actualLocation;
	private final Location bakedViewpoint;
	private final UUID armorStandUuid;
	private final String name;

	private Camera(final Location location, final String name, final UUID uuid) {
		this.armorStandUuid = uuid;
		this.name = name;

		this.actualLocation = location.clone();
		this.bakedViewpoint = actualLocation.clone();
		this.bakedViewpoint.add(bakedViewpoint.getDirection().normalize().divide(TWO));
	}

	public Location getActualLocation() {
		return actualLocation.clone();
	}

	public Location getViewpoint() {
		return bakedViewpoint.clone();
	}

	public UUID getUUID() {
		return armorStandUuid;
	}

	public String getName() {
		return name;
	}

	public void destroy() {
		PaperLib.getChunkAtAsync(actualLocation).thenAccept(chunk -> {
			final ArmorStand armorStand = (ArmorStand)Bukkit.getEntity(armorStandUuid);
			if (armorStand != null) {
				armorStand.remove();
			}
		});
	}
}
