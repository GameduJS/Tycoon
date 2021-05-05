package de.tycoon.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tycoon.util.ItemBuilder;

public class Items {
	
	public static ItemStack getSettingItemStack() {
		return new ItemBuilder(Material.REDSTONE_TORCH)
							.setDisplayName("§c§lSettings")
							.setLore("§b§o<Rightclick> §7Customize your personal settings")
							.addHiddenEnchantment()
							.build();
	}
	
	public static ItemStack getShopItemStack() {
		return new ItemBuilder(Material.EMERALD)
							.setDisplayName("§a§lShop")
							.setLore("§b§o<Rightclick> §7Open shop")
							.addHiddenEnchantment()
							.build();
	}
	
}
