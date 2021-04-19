package de.tycoon.generators.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tycoon.config.ConfigManager;

public abstract class IBaseGenerator {

	private String name;
	private int genLeveL;
	private GeneratorBlock block;
	private Material materialToDrop;
	private double xpToDrop;
	
	public IBaseGenerator(String name, int genLevel, Material materialToDrop, double xp, GeneratorBlock block) {
		this.name = name;
		this.genLeveL = genLevel;
		this.block = block;
		this.materialToDrop = materialToDrop;
		this.xpToDrop = xp;
	}
	
	
	public abstract void spawn(Location loc);
	public abstract ItemStack getGeneratorItem(ConfigManager configManager);
	
	
	public GeneratorBlock getBlock() {
		return this.block;
	}
	public String getName() {
		return name;
	}
	public int getGenLeveL() {
		return genLeveL;
	}
	public Material getMaterialToDrop() {
		return materialToDrop;
	}
	public double getXpToDrop() {
		return xpToDrop;
	}
	
	
	
}
