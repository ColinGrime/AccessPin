package com.github.colingrime.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Utils {

	private Utils() {}

	/**
	 * @param message any message
	 * @return color-coded message
	 */
	public static String color(String message) {
		if (message == null) {
			return "";
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * @param messages list of messages
	 * @return color-coded list of messages
	 */
	public static List<String> color(List<String> messages) {
		if (messages.isEmpty()) {
			return new ArrayList<>();
		}

		return messages.stream().map(Utils::color).collect(Collectors.toList());
	}
}