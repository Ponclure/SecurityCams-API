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

package com.github.ponclure.securitycams.util;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReflectionUtil {

	private static final Pattern SERVER_CLASS_PATTERN = Pattern.compile("^org\\.bukkit\\.craftbukkit(\\.\\w+\\.)CraftServer$");
	private static final String SERVER_VERSION;

	static {
		final Class<? extends Server> serverClass = Bukkit.getServer().getClass();
		final Matcher matcher = SERVER_CLASS_PATTERN.matcher(serverClass.getName());

		if (matcher.matches()) {
			SERVER_VERSION = matcher.group(1);
		} else {
			// versionless server
			SERVER_VERSION = ".";
		}
	}

	public static Class<?> nmsClass(final String clazz) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server" + SERVER_VERSION + clazz);
	}

	public static Method nmsDeclaredMethod(final String clazz, final String method) throws ClassNotFoundException, NoSuchMethodException {
		return nmsClass(clazz).getDeclaredMethod(method);
	}

	public static Method nmsMethod(final String clazz, final String method) throws ClassNotFoundException, NoSuchMethodException {
		return nmsClass(clazz).getMethod(method);
	}

	public static Method[] nmsDeclaredMethods(final String clazz) throws ClassNotFoundException {
		return nmsClass(clazz).getDeclaredMethods();
	}

	public static Method[] nmsMethods(final String clazz) throws ClassNotFoundException {
		return nmsClass(clazz).getMethods();
	}

	public static Field nmsDeclaredField(final String clazz, final String field) throws ClassNotFoundException, NoSuchFieldException {
		return nmsClass(clazz).getDeclaredField(field);
	}

	public static Field nmsField(final String clazz, final String field) throws ClassNotFoundException, NoSuchFieldException {
		return nmsClass(clazz).getField(field);
	}

	public static Field[] nmsDeclaredFields(final String clazz) throws ClassNotFoundException {
		return nmsClass(clazz).getDeclaredFields();
	}

	public static Field[] nmsFields(final String clazz) throws ClassNotFoundException {
		return nmsClass(clazz).getFields();
	}

	public static Class<?> obcClass(final String clazz) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit" + SERVER_VERSION + clazz);
	}

	public static Method obcDeclaredMethod(final String clazz, final String method) throws ClassNotFoundException, NoSuchMethodException {
		return obcClass(clazz).getDeclaredMethod(method);
	}

	public static Method obcMethod(final String clazz, final String method) throws ClassNotFoundException, NoSuchMethodException {
		return obcClass(clazz).getMethod(method);
	}

	public static Method[] obcDeclaredMethods(final String clazz) throws ClassNotFoundException {
		return obcClass(clazz).getDeclaredMethods();
	}

	public static Method[] obcMethods(final String clazz) throws ClassNotFoundException {
		return obcClass(clazz).getMethods();
	}

	public static Field obcDeclaredField(final String clazz, final String field) throws ClassNotFoundException, NoSuchFieldException {
		return obcClass(clazz).getDeclaredField(field);
	}

	public static Field obcField(final String clazz, final String field) throws ClassNotFoundException, NoSuchFieldException {
		return obcClass(clazz).getField(field);
	}

	public static Field[] obcDeclaredFields(final String clazz) throws ClassNotFoundException {
		return obcClass(clazz).getDeclaredFields();
	}

	public static Field[] obcFields(final String clazz) throws ClassNotFoundException {
		return obcClass(clazz).getFields();
	}

	private ReflectionUtil() {
		throw new UnsupportedOperationException();
	}
}
