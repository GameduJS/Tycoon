package de.tycoon.sellwand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tycoon.util.ItemBuilder;

public class SellwandManager {

	private List<Sellwand> sellwands;
	
	public SellwandManager() {
		this.sellwands = new ArrayList<>();
		
		this.sellwands.add(new Sellwand(getSellwand("2"), 2));
		this.sellwands.add(new Sellwand(getSellwand("4"), 4));
		this.sellwands.add(new Sellwand(getSellwand("5"), 5));
	}

	private ItemStack getSellwand(String multiplier) {
		return new ItemBuilder(Material.BLAZE_ROD)
					.setDisplayName("§7§kxX §6Sellwand §7(" + multiplier + "x) §7§kXx")
					.setLore("§b§o<Rightclick> §7on chest to sell all")
					.setLore("§aPowerful, given by god")
					.build();
	}
	
	public List<Sellwand> getSellwands() {
		return sellwands;
	}
	
	public Sellwand getSellwandByMultiplier(int multiplier) {
		for(Sellwand sellwand : sellwands)
			if(sellwand.getMultiplier() == multiplier)
				return sellwand;
		return new Sellwand(getSellwand("1"), 1);
	}
	
	public boolean isSellwand(ItemStack itemStack) {
		if(itemStack.getType() != Material.BLAZE_ROD) return false;
		if(!itemStack.hasItemMeta()) return false;
		if(!itemStack.getItemMeta().hasDisplayName()) return false;
		if(!itemStack.getItemMeta().hasLore()) return false;
		
		
		for(Sellwand sellwand : sellwands) {
			if(itemStack.getItemMeta().getDisplayName().equals("§7§kxX §6Sellwand §7(" + sellwand.getMultiplier() + "x) §7§kXx")) {
				if(replace(itemStack.getItemMeta().getLore()).equals(replace(sellwand.getSellwandItem().getItemMeta().getLore()))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Sellwand getSellwandByItem(ItemStack itemStack) {
		if(itemStack.getType() != Material.BLAZE_ROD) return null;
		if(!itemStack.hasItemMeta()) return null;
		if(!itemStack.getItemMeta().hasDisplayName()) return null;
		if(!itemStack.getItemMeta().hasLore()) return null;
		
		for(Sellwand sellwand : sellwands) {
			if(itemStack.getItemMeta().getDisplayName().equals("§7§kxX §6Sellwand §7(" + sellwand.getMultiplier() + "x) §7§kXx")) {
				if(replace(itemStack.getItemMeta().getLore()).equals(replace(sellwand.getSellwandItem().getItemMeta().getLore()))) {
					return sellwand;
				}
			}
			
		}
		return null;
	}
	
	private List<String> replace(List<String> source){
		List<String> result = new ArrayList<>();
		
		for(String s : source) {
			result.add(s.replace("§", "&"));
		}
		
		return result;
	}
	
}
