package de.tycoon.generators.generator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;

public class Generator extends IBaseGenerator{

	private int tier = 1;
	
	public Generator(String name, int generatorLevel, Material material, double xp, GeneratorBlock genBlock) {
		super(name, generatorLevel, material, xp, genBlock);
		this.tier = generatorLevel;
	}
	
	
	@Override
	public void spawn(Location loc) {
		Location locationToDrop = loc.add(0, 0.75, 0);
		Item item = loc.getWorld().dropItemNaturally(locationToDrop, new ItemStack(this.getMaterialToDrop()));
		item.setVelocity(new Vector(0, 0, 0));
		locationToDrop.subtract(0, 0.75, 0);
	}

	@Override
	public ItemStack getGeneratorItem(ConfigManager configManager) {
		
		Config config = configManager.getConfigutationByTier(this.tier);
		
		ItemStack gen = new ItemStack(Material.valueOf(config.getString("Generator.Block").toUpperCase()));
		ItemMeta genMeta = gen.getItemMeta();
		
		List<String> lores = new ArrayList<>();
			for(String lore : config.getStringList("Generator.Lore")) {
				lores.add(lore.replace('&', '§'));
			}
		genMeta.setLore(lores);
		
		genMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Generator.Name")));
		gen.setItemMeta(genMeta);
		
		return gen;
		
	}
	
	public int getGeneratorLevel() {
		return tier;
	}


	

}
