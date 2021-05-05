package de.tycoon.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.tycoon.config.Config;
import de.tycoon.util.ItemBuilder;

public class ShopHandler {

	private ShopItemHandler shopItemHandler;

	private Config config;
	
	private Map<UUID, Boolean> isEditMode;

	private Inventory[] shopInventories;

	public ShopHandler() {
		this.shopItemHandler = new ShopItemHandler();
		this.shopItemHandler.setDefaultItems();
		this.shopItemHandler.loadItems();

		// Might useless
		this.config = new Config(Config.SHOP_PATH, "Shop-Settings");
		this.isEditMode = new HashMap<>();
		
		this.setDefaultSettings();
		this.buildInventory();
	}

	private void setDefaultSettings() {
		if (config.contains("ShopSettings"))
			return;
	}

	private void buildInventory() {

		// Design manager

		// ShopItem manager
		/* | Size of Design Manager */
		int totalPages = Math.max(maxKey(this.shopItemHandler.getShopItems()), 0);

		Inventory[] shopPages = new Inventory[totalPages];

		for (int i = 0; i < totalPages; i++) {

			shopPages[i] = Bukkit.createInventory(null, 6 * 9, "§e§lTYCOON §aShop §7(" + (i + 1) + ")");

			shopPages[i].setItem(53,
					new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§c§lNext page...").build());
			shopPages[i].setItem(45,
					new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§c§lPrevious page...").build());

		}
		
		for(int page : this.shopItemHandler.getShopItems().keySet()) {
			for(int slot : this.shopItemHandler.getShopItems().get(page).keySet()) {
				shopPages[page - 1].setItem(slot, this.shopItemHandler.getShopItems().get(page).get(slot).getItemStack());
			}
		}
		
		this.shopInventories = shopPages;
	}

	public Inventory[] getShopInventories() {
		return shopInventories;
	}

	public int maxKey(Map<Integer, ?> o) {
		int highest = 0;

		for (int index : o.keySet()) {
			if (index > highest) {
				highest = index;
			}
		}

		return highest;
	}
	
	public boolean isEditMode(Player player) {
		return this.isEditMode.getOrDefault(player.getUniqueId(), false);
	}
	
	public void setEditMode(Player player, boolean b) {
		this.isEditMode.put(player.getUniqueId(), b);
	}

}
