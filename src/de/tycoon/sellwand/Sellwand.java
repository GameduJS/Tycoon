package de.tycoon.sellwand;

import org.bukkit.inventory.ItemStack;

public class Sellwand {

	private ItemStack sellwand;
	
	private int multiplier;
	
	public Sellwand(ItemStack itemStack, int multiplier) {
		this.sellwand = itemStack;
		this.multiplier = multiplier;
	}
	
	public ItemStack getSellwandItem() {
		return sellwand;
	}
	
	public int getMultiplier() {
		return multiplier;
	}
}
