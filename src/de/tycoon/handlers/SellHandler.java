package de.tycoon.handlers;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellHandler {

	public int getAmount(Inventory inventory, Material itemStack) {
		
		int amount = 0;
		
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) != null)
				if(inventory.getItem(i).getType().equals(itemStack))
					amount+=inventory.getItem(i).getAmount();
		}
		
		return amount;
		
	}
	
	public void removeItems(Inventory inventory, Material mat) {
		
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) != null) {
				if(inventory.getItem(i).getType().equals(mat)) {
					inventory.setItem(i, new ItemStack(Material.AIR));
				}
			}
		}
		
	}
	
}
