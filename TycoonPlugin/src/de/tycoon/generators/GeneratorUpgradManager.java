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
	private Config settingsConfig;
	private Config genConfig;
	
	public GeneratorUpgradManager() {
		this.plugin = TycoonPlugin.get();
		this.configManager = plugin.getConfigManager();
		this.settingsConfig = configManager.getSettingConfig();
		this.generatorManager = plugin.getGeneratorManager();
	}




	public int upgrade(Generator generator, UUID uuid) {
		
		int tier = generator.getGeneratorLevel() + 1;
		if(tier == this.settingsConfig.getInt("Generators.Max-Tiers") + 1) return 1;
		
		this.genConfig = this.configManager.getConfigutationByTier(tier);
		
		String name = this.genConfig.getString("Generator.Name");
		
		Material dropMaterial = Material.valueOf(this.genConfig.getString("Generator.Drop.Material"));
		Material blockMaterial = Material.valueOf(this.genConfig.getString("Generator.Block"));
		List<String> lores = this.genConfig.getStringList("Generator.Lore");
		
		Generator gen = new Generator(name, tier, dropMaterial, new GeneratorBlock(generator.getBlock().getLocation(), blockMaterial, name, lores));
		generator.getBlock().getLocation().getBlock().setType(blockMaterial);
		
		this.generatorManager.removeGenerator(uuid, generator);
		this.generatorManager.addGenerator(uuid, gen);
		
		return -1;
		
	}
	
}
