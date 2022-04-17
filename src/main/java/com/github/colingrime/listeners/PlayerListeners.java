package com.github.colingrime.listeners;

import com.github.colingrime.AccessPin;
import com.github.colingrime.locale.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListeners implements Listener {

	private final AccessPin plugin;

	public PlayerListeners(AccessPin plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));

		plugin.getJoinTimes().put(player, System.currentTimeMillis());
		Messages.ENTER_PIN.sendTo(player);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		// player can no longer see chat messages
		event.getRecipients().removeIf(r -> plugin.getJoinTimes().containsKey(r));

		// we only care about players who need to enter pins
		Player player = event.getPlayer();
		if (!plugin.getJoinTimes().containsKey(player)) {
			return;
		}

		String message = event.getMessage();
		String pin = plugin.getSettings().getPin();

		Bukkit.getScheduler().runTask(plugin, () -> {
			if (message.equals(pin) || (!plugin.getSettings().isCaseSensitive()) && message.equalsIgnoreCase(pin)) {
				player.removePotionEffect(PotionEffectType.BLINDNESS);
				plugin.getJoinTimes().remove(player);
				Messages.CORRECT_PIN.sendTo(player);
			} else {
				Messages.WRONG_PIN.sendTo(player);

				if (plugin.getSettings().getKickIfIncorrect()) {
					player.kickPlayer(Messages.KICK_MESSAGE.toString());
				}
			}
		});

		// don't send the chat message through
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.getJoinTimes().containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (plugin.getJoinTimes().containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (plugin.getJoinTimes().containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.getJoinTimes().remove(event.getPlayer());
	}
}
