package com.github.colingrime.config;

import com.github.colingrime.AccessPin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Settings {

	private final AccessPin plugin;
	private FileConfiguration config;

	private String pin;
	private boolean caseSensitive;
	private boolean kickIfIncorrect;
	private int timeGiven;
	private List<Integer> warningIntervals;

	public Settings(AccessPin plugin) {
		this.plugin = plugin;
		this.plugin.saveDefaultConfig();
	}

	public void reload() {
		plugin.reloadConfig();
		config = plugin.getConfig();

		pin = _getPin();
		caseSensitive = _isCaseSensitive();
		kickIfIncorrect = _getKickIfIncorrect();
		timeGiven = _getTimeGiven();
		warningIntervals = _getWarningIntervals();
	}

	private String _getPin() {
		return config.getString("general.pin");
	}

	public String getPin() {
		return pin;
	}

	private boolean _isCaseSensitive() {
		return config.getBoolean("general.case-sensitive");
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	private boolean _getKickIfIncorrect() {
		return config.getBoolean("general.kick-if-incorrect");
	}

	public boolean getKickIfIncorrect() {
		return kickIfIncorrect;
	}

	private int _getTimeGiven() {
		return config.getInt("general.time-given");
	}

	public int getTimeGiven() {
		return timeGiven;
	}

	private List<Integer> _getWarningIntervals() {
		return config.getIntegerList("general.warning-intervals");
	}

	public List<Integer> getWarningIntervals() {
		return warningIntervals;
	}
}
