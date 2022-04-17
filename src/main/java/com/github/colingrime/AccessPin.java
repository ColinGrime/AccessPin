package com.github.colingrime;

import com.github.colingrime.commands.AccessPinCommand;
import com.github.colingrime.config.Settings;
import com.github.colingrime.listeners.PlayerListeners;
import com.github.colingrime.locale.Messages;
import com.github.colingrime.tasks.TimeTask;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class AccessPin extends JavaPlugin {

	// since this plugin is so small, I can just cache it in main
	private final Map<Player, Long> joinTimes = new HashMap<>();

	private Settings settings;

	@Override
	public void onEnable() {
		loadData();
		getServer().getPluginCommand("accesspin").setExecutor(new AccessPinCommand(this));
		new PlayerListeners(this);
		new TimeTask(this).runTaskTimer(this, 20L, 20L);
	}

	/**
	 * Initializes the yaml data.
	 */
	private void loadData() {
		settings = new Settings(this);
		reload();
	}

	/**
	 * Reloads the yaml data.
	 */
	public void reload() {
		settings.reload();
		Messages.reload(this);
	}

	public Map<Player, Long> getJoinTimes() {
		return joinTimes;
	}

	public Settings getSettings() {
		return settings;
	}
}
