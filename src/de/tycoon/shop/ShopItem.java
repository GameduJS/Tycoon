package de.tycoon.shop;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

@SerializableAs("ShopItem")
public class ShopItem implements ConfigurationSerializable{

	/**
	 * ItemStack
	 */
	private ItemStack itemStack;
	
	/**
	 * Which page the item should be
	 */
	private int page;
	
	/**
	 * Which slot the item should be
	 */
	private int slot;
	
	/**
	 * Price of the item
	 */
	private double buyPrice;

	/**
	 * @param itemStack
	 * @param page
	 * @param slot
	 * @param buyPrice
	 */
	public ShopItem(ItemStack itemStack, int page, int slot, double buyPrice) {
		this.itemStack = itemStack;
		this.page = page;
		this.slot = slot;
		this.buyPrice = buyPrice;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getPage() {
		return page;
	}

	public int getSlot() {
		return slot;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	@Override
	public Map<String, Object> serialize() {
		 Map<String, Object> result = new LinkedHashMap<String, Object>();
		 
		 result.put("Itemstack", this.getItemStack());
		 result.put("Page", this.getPage());
		 result.put("Slot", this.getSlot());
		 result.put("BuyPrice", this.getBuyPrice());
		
		
		return result;
	}
	
	public static ShopItem deserialize(Map<String, Object> args) {
		
		ItemStack itemStack = null;
		int page = 0;
		int slot = 0;
		double buyPrice = 0;
		
		if(args.containsKey("Itemstack"))
			itemStack = (ItemStack) args.get("Itemstack");
		
		if(args.containsKey("Page"))
			page = (int) args.get("Page");
		
		if(args.containsKey("Slot"))
			slot = (int) args.get("Slot");
		
		if(args.containsKey("BuyPrice"))
			buyPrice = (double) args.get("BuyPrice");
		
		return new ShopItem(itemStack, page, slot, buyPrice);
	}
	
	
	
}
