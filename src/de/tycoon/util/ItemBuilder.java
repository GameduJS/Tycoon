package de.tycoon.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ItemBuilder {
	
	private Material material;
	private String displayName;
	private ItemStack item;
	private ItemMeta meta;
	private List<String> lore;
	private int amount;
	
	public ItemBuilder(Material material) {
			
			this.material = material;
			this.displayName = "";
			this.amount = 1;
			this.item = new ItemStack(material);
			this.meta = item.getItemMeta();
			this.lore = new ArrayList<String>();
		}
	
	public ItemBuilder(Material material, int amount) {
			
			this.material = material;
			this.displayName = "";
			this.amount = amount;
			this.item = new ItemStack(material, amount);
			this.meta = item.getItemMeta();
			this.lore = new ArrayList<String>();
		}
	
	@SuppressWarnings("deprecation")
	public ItemBuilder(Material material, int amount, short data) {
		
		this.material = material;
		this.displayName = "";
		this.amount = amount;
		this.item = new ItemStack(material, this.amount, data);
		this.meta = item.getItemMeta();
		this.lore = new ArrayList<String>();
	}
	
	
	public Material getMaterial() {
		return this.material;
	}
		
	
	public ItemBuilder setDisplayName(String displayname) {
		this.displayName = displayname;
		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment ench, int level) {
		this.meta.addEnchant(ench, level, true);
		
		return this;
	}
	
	public ItemBuilder addDefaultEnchantment(Enchantment ench) {
		this.meta.addEnchant(ench, 1, true);
		return this;
	}
	
	public ItemBuilder addHiddenEnchantment() {
		this.meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return this;
	}

	public ItemBuilder setLore(String lore) {
		this.lore.add(format(lore));
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ItemBuilder setDurability(int dura) {
		this.item.setDurability((short) dura);
		return this;
	}
	
	public ItemBuilder amount(int amount) {
	    this.item.setAmount(amount);
	    return this;
	}
	
	 public ItemBuilder unbreakable(boolean b) {
		 this.meta.setUnbreakable(true);
	    return this;
	 }
	
	 public ItemBuilder addItemFlag(ItemFlag flag) {
	    this.meta.addItemFlags(new ItemFlag[] { flag });
	    return this;
	 }
	
	 public ItemStack build() {
	    this.meta.setDisplayName(this.displayName);
	    this.meta.setLore(this.lore);
	    this.item.setItemMeta(this.meta);
	
	     return this.item;
	 }
	 
	 
	 private String format(String message) {
		 
		 if(!message.contains("&")) {
			 message = "&7" + message;
		 }
		 
			
		if(message.contains("&r") || message.contains("&f")) {
				message = message.replace("&r", "&7");
				message = message.replace("&f", "&7");
		}
			
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
}
