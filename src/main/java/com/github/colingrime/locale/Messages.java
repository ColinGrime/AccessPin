package com.github.colingrime.locale;

import com.github.colingrime.AccessPin;
import com.github.colingrime.utils.Logger;
import com.github.colingrime.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Messages {

	ENTER_PIN("enter-pin", "&aPlease enter the PIN to be given access to the server..."),
	CORRECT_PIN("correct-pin", "&aYou have been granted access to the server!"),
	WRONG_PIN("wrong-pin", "&cThe PIN you have entered is incorrect."),
	WARNING_MESSAGE("warning-message", "&cYou have &e%time% seconds &cleft to enter the PIN before you get kicked."),
	KICK_MESSAGE("kick-message", "&cYou have been kicked due to not entering the correct PIN."),
	RELOAD_MESSAGE("reload-message", "&eAccessPin &ahas been reloaded!"),
	;

	private static FileConfiguration config;

	private final String path;
	private List<String> messages;

	Messages(String path, String...messages) {
		this.path = "messages." + path;
		this.messages = Arrays.asList(messages);
	}

	public static void reload(AccessPin plugin) {
		config = plugin.getConfig();
		Arrays.stream(Messages.values()).forEach(Messages::update);
	}

	public void update() {
		if (!config.getStringList(path).isEmpty()) {
			messages = Utils.color(config.getStringList(path));
		} else if (config.getString(path) != null) {
			messages = Collections.singletonList(Utils.color(config.getString(path)));
		} else {
			Logger.log("Messages path \"" + path + "\" has failed to load (using default value).");
			messages = Utils.color(messages);
		}
	}

	public void sendTo(CommandSender sender) {
		if (messages.isEmpty()) {
			return;
		}

		messages.forEach(sender::sendMessage);
	}

	public void sendTo(CommandSender sender, Replacer replacer) {
		if (messages.isEmpty()) {
			return;
		}

		replacer.replace(messages).forEach(sender::sendMessage);
	}

	@Override
	public String toString() {
		return String.join("\n", messages);
	}
}