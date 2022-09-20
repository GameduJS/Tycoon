package de.tycoon.generators;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.generators.generator.Generator;
import de.tycoon.generators.generator.GeneratorBlock;

public class GeneratorUpgradManager {

	private TycoonPlugin plugin;
	private ConfigManager configManager;
	private GeneratorManager generatorManager;
	private Config genConfig;
	
	public GeneratorUpgradManager() {
		this.plugin = TycoonPlugin.get();
		this.configManager = plugin.getConfigManager();
		this.generatorManager = plugin.getGeneratorManager();
	}




	public void upgrade(Generator generator, UUID uuid) {
		
		int tier = generator.getGeneratorLevel() + 1;
		
		this.genConfig = this.configManager.getConfigutationByTier(tier);
		
		String name = this.genConfig.getString("Generator.Name");
		
		Material dropMaterial = Material.valueOf(this.genConfig.getString("Generator.Drop.Material"));
		Material blockMaterial = Material.valueOf(this.genConfig.getString("Generator.Block"));
		List<String> lores = this.genConfig.getStringList("Generator.Lore");
		
		double xp = this.genConfig.getDouble("Generator.Drop.XP");
		
		Generator gen = new Generator(name, tier, dropMaterial, xp, new GeneratorBlock(generator.getBlock().getLocation(), blockMaterial, name, lores));
		generator.getBlock().getLocation().getBlock().setType(blockMaterial);
		
		this.generatorManager.removeGenerator(uuid, generator);
		this.generatorManager.addGenerator(uuid, gen);
		
		
	}
	
}
