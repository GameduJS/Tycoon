package de.tycoon.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerUtils {

	public static  Player fromUUIDString(String uuid) {
		return Bukkit.getPlayer(UUID.fromString(uuid));
	}
	
	public static Player fromName(String name) {
		if(isValidPlayer(name))
			return Bukkit.getPlayer(name);
		return null;
	}

	public static Player fromUUID(UUID uuid) {
		if(isValidPlayer(uuid))
			return Bukkit.getPlayer(uuid);
		return null;
	}
	
	public static UUID asUUID(Player player) {
		return player.getUniqueId();
	}
	
	public static UUID asUUID(String uuidString) {
		return UUID.fromString(uuidString);
	}
	
	public static boolean isValidPlayer(String name) {
		return Bukkit.getPlayer(name) != null;
	}
	
	public static boolean isValidPlayer(UUID uuid) {
		return Bukkit.getPlayer(uuid) != null;
	}
	
	public static boolean isValid(OfflinePlayer player) {
		return player.getUniqueId() != null;
	}
	
	 public static OfflinePlayer getOfflinePlayer(String name) {
	        for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
	            if(player.getName().equalsIgnoreCase(name)) return player;
	        }
	        return null;
	    }
	
}
