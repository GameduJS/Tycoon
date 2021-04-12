package de.tycoon.generators.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;

public abstract class IBaseGenerator {

	private String name;
	private int genLeveL;
	private GeneratorBlock block;
	private Material materialToDrop;
	
	public IBaseGenerator(String name, int genLevel, Material materialToDrop, GeneratorBlock block) {
		this.name = name;
		this.genLeveL = genLevel;
		this.block = block;
		this.materialToDrop = materialToDrop;
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
	
	
	
}
