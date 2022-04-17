package com.github.colingrime.tasks;

import com.github.colingrime.AccessPin;
import com.github.colingrime.locale.Messages;
import com.github.colingrime.locale.Replacer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class TimeTask extends BukkitRunnable {

	private final AccessPin plugin;

	public TimeTask(AccessPin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();

		for (Map.Entry<Player, Long> joinTime : plugin.getJoinTimes().entrySet()) {
			int timeLeft = getTimeLeft(joinTime.getValue(), currentTime);

			if (timeLeft <= 0) {
				joinTime.getKey().kickPlayer(Messages.KICK_MESSAGE.toString());
			} else if (plugin.getSettings().getWarningIntervals().contains(timeLeft)) {
				Messages.WARNING_MESSAGE.sendTo(joinTime.getKey(), new Replacer("%time%", timeLeft));
			}
		}
	}

	private int getTimeLeft(long playerTime, long currentTime) {
		int time = (int) ((currentTime - playerTime) / 1000);
		return plugin.getSettings().getTimeGiven() - time;
	}
}
