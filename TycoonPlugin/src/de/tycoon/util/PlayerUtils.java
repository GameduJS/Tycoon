package de.tycoon.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {

	public Player fromUUIDString(String uuid) {
		return Bukkit.getPlayer(UUID.fromString(uuid));
	}
	
	public Player fromName(String name) {
		if(isValidPlayer(name))
			return Bukkit.getPlayer(name);
		return null;
	}

	public Player fromUUID(UUID uuid) {
		if(isValidPlayer(uuid))
			return Bukkit.getPlayer(uuid);
		return null;
	}
	
	public UUID asUUID(Player player) {
		return player.getUniqueId();
	}
	
	public UUID asUUID(String uuidString) {
		return UUID.fromString(uuidString);
	}
	
	public boolean isValidPlayer(String name) {
		return Bukkit.getPlayer(name) != null;
	}
	
	public boolean isValidPlayer(UUID uuid) {
		return Bukkit.getPlayer(uuid) != null;
	}
	
}
