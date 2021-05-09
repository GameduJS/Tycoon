package de.tycoon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;

public class BukkitUtils {

	public static Player fromUUIDString(String uuid) {
		return Bukkit.getPlayer(UUID.fromString(uuid));
	}

	public static Player fromName(String name) {
		if (isValidPlayer(name))
			return Bukkit.getPlayer(name);
		return null;
	}

	public static Player fromUUID(UUID uuid) {
		if (isValidPlayer(uuid))
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
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equalsIgnoreCase(name))
				return player;
		}
		return null;
	}

	public static boolean isValidMaterial(String input) {
		try {
			Material.valueOf(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static ItemStack getTierOneGeneratorWithShopLore(String shopLore, boolean w) {

		Config generatorConfig = TycoonPlugin.get().getConfigManager().getConfigutationByTier(1);
		
		ItemStack gen = new ItemStack(
				Material.valueOf(generatorConfig.getString("Generator.Block").toUpperCase()));
		gen.setAmount(1);
		ItemMeta genMeta = gen.getItemMeta();

		List<String> lores = new ArrayList<>();
		if(w) {
			for (String lore : generatorConfig.getStringList("Generator.Lore")) {
				lores.add(lore.replace('&', '§'));
			}
		}
		
		lores.add(shopLore);
		
		genMeta.setLore(lores);

		genMeta.setDisplayName(
				ChatColor.translateAlternateColorCodes('&', generatorConfig.getString("Generator.Name")));
		gen.setItemMeta(genMeta);

		return gen;
	}

}
