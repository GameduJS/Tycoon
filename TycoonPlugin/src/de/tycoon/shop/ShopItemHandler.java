package de.tycoon.shop;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import de.tycoon.config.Config;
import de.tycoon.util.ItemBuilder;

public class ShopItemHandler {

	private Map<Integer, Map<Integer, ShopItem>> shopItems;
	private Config config;
	
	public ShopItemHandler() {
		this.shopItems = new HashMap<>();
		this.config = new Config(Config.SHOP_PATH, "ShopItems");
	}
	
	
	public void loadItems() {
		
		if(!config.contains("Shop")) return;
		
			this.config.getConfigurationSection("Shop").getKeys(false).forEach(key -> {
				ShopItem item = (ShopItem) this.config.get("Shop." + key);

				Map<Integer, ShopItem> itemBySlot = this.shopItems.getOrDefault(item.getPage(), new HashMap<>());
				itemBySlot.put(item.getSlot(), item);
				
				shopItems.put(item.getPage(), itemBySlot);
				
			});
	}
	
	public void setDefaultItems() {
		if(config.contains("Shop")) return;
		
		ShopItem item = new ShopItem(new ItemBuilder(Material.DIRT).setDisplayName("§7§oDirt").setLore("§610€ §7- Buy dirt").build(), 1, 0, 2.0);
		
		this.config.set("Shop.1", item);
	}
	
	public Map<Integer, Map<Integer, ShopItem>> getShopItems() {
		return shopItems;
	}
	
}
