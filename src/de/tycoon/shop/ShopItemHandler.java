package de.tycoon.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.util.BukkitUtils;
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
		
		ShopItem dirt = new ShopItem(new ItemBuilder(Material.DIRT).setDisplayName("§7§oDirt").setLore("§62€§7- Buy dirt").build(), 1, 2, 2.0);
		ShopItem stone = new ShopItem(new ItemBuilder(Material.STONE).setDisplayName("§7§oStone").setLore("§63€§7- Buy stone").build(), 1, 3, 3.0);
		ShopItem oak_log = new ShopItem(new ItemBuilder(Material.OAK_LOG).setDisplayName("§7§oOak Log").setLore("§65€§7- Buy Oak Log").build(), 1, 4, 5.0);
		ShopItem spruce_log = new ShopItem(new ItemBuilder(Material.OAK_LOG).setDisplayName("§7§oSpruce Log").setLore("§65€§7- Buy Spruce Log").build(), 1, 5, 5.0);
		ShopItem generator = new ShopItem(BukkitUtils.getTierOneGeneratorWithShopLore("§6500€§7 - Buy Tier I Generator", false), 1, 6, 500);
		
		
		this.config.set("Shop.1", dirt);
		this.config.set("Shop.2", stone);
		this.config.set("Shop.3", oak_log);
		this.config.set("Shop.4", spruce_log);
		this.config.set("Shop.5", generator);
	}
	
	public Map<Integer, Map<Integer, ShopItem>> getShopItems() {
		return shopItems;
	}
	
	public ShopItem getItemStackByIndex(int page, int slot) {
		return this.shopItems.get(page).get(slot);
	}

}
