package de.tycoon.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tycoon.util.ItemBuilder;

public class Menus {

	
	public static Inventory getMenuInventory() {
		Inventory menuInventory = Bukkit.createInventory(null, 9, "§e§lTYCOON §7- Menu");
		
		for(int i = 0; i < menuInventory.getSize(); i++)
			menuInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
					.setDisplayName(" ").build());
		
		menuInventory.setItem(2, Items.getSettingItemStack());
		menuInventory.setItem(6, Items.getShopItemStack());
		
		return menuInventory;
	}
	
	
	public static Inventory getSettingsInventory(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 9, "§e§lTYCOON §7- Personal settings");
		
		for(int i = 0; i < inventory.getSize(); i++)
			inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
					.setDisplayName(" ").build());
		
		ItemStack lagInformation = null;
		if( player.isOp() ) {
			lagInformation = new ItemBuilder(Material.RED_WOOL)
															.setDisplayName("§7§oDisabled §r§7- §aEnable lag notifications")
															.addHiddenEnchantment().build();
		} else {
			lagInformation = new ItemBuilder(Material.LIME_WOOL)
					.setDisplayName("§7§oEnabled §r§7- §cDisable lag notifications")
					.addHiddenEnchantment().build();
		}
		
		ItemStack sellInformation = null;
		if( player.isOp() ) {
			sellInformation = new ItemBuilder(Material.RED_WOOL)
														.setDisplayName("§7§oDisabled §r§7- §aEnable sell notifications")
														.addHiddenEnchantment().build();
		} else {
			sellInformation = new ItemBuilder(Material.LIME_WOOL)
					.setDisplayName("§7§oEnabled §r§7- §cDisable sell notifications")
					.addHiddenEnchantment().build();
		}
		
		ItemStack placeHolder;
		placeHolder = new ItemBuilder(Material.BARRIER)
												.setDisplayName("§c§lComing soon...")
												.addHiddenEnchantment().build();
		
		inventory.setItem(2, lagInformation);
		inventory.setItem(4, sellInformation);
		inventory.setItem(6, placeHolder);
		
		return inventory;
	}
	
}
