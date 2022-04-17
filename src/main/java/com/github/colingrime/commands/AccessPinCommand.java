package com.github.colingrime.commands;

import com.github.colingrime.AccessPin;
import com.github.colingrime.locale.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AccessPinCommand implements CommandExecutor {

	private final AccessPin plugin;

	public AccessPinCommand(AccessPin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission("accesspin.reload")) {
			plugin.reload();
			Messages.RELOAD_MESSAGE.sendTo(sender);
		}

		return true;
	}
}
