package de.tycoon.generators;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.generators.generator.Generator;
import de.tycoon.generators.generator.GeneratorBlock;
import de.tycoon.util.FileUtils;

public class GeneratorConfigManager {

	private TycoonPlugin plugin;
	private GeneratorManager generatorManager;
	private ConfigManager configManager;
	
	public GeneratorConfigManager() {
		this.plugin = TycoonPlugin.get();
		this.generatorManager = plugin.getGeneratorManager();
		this.configManager = plugin.getConfigManager();
	}
	
	
	public void loadGenerators() {
		
		File userChacheFolder = new File(Config.GENERATOR_CHACHE_PATH);
		FileUtils.createFolderIfNotExists(userChacheFolder);
		
		/* Get all files from folder that ends with .yml */
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".yml");
			}
			
		};
		String[] userFilesName = userChacheFolder.list(filter);
		
		/* Load user balance */
		for(int i = 0; i < userFilesName.length; i++) {
			
			Config config = new Config(Config.GENERATOR_CHACHE_PATH, userFilesName[i]);
			
			if(!config.contains("Generators")) continue;
			
			for(String key : config.getConfigurationSection("Generators").getKeys(false)) {
				
				Config configTier = this.configManager.getConfigutationByTier(
						Integer.parseInt(
								config
								.getString("Generators." + key + ".Tier")));
							
								
								this.generatorManager.addGenerator(UUID.fromString(userFilesName[i].replace(".yml", "")), 
										new Generator(configTier.getString("Generator.Name"), config.getInt("Generators." + key + ".Tier"), Material.valueOf(configTier.getString("Generator.Drop.Material")), 
												new GeneratorBlock(
														new Location(Bukkit.getWorld(config.getString("Generators." + key + ".Location.World")), config.getInt("Generators." + key + ".Location.X") , config.getInt("Generators." + key + ".Location.Y"), config.getInt("Generators." + key + ".Location.Z")), 
														Material.valueOf(configTier.getString("Generator.Block")), 
														configTier.getString("Generator.Name"), 
														configTier.getStringList("Generator.Lore")
													)
											)
								);
			}
			
		}
		
	}
	
	public void saveGenerators() {
		
		File userChacheFolder = new File(Config.GENERATOR_CHACHE_PATH);
		FileUtils.createFolderIfNotExists(userChacheFolder);

		if(this.generatorManager.getGenerators().keySet().isEmpty()) return;
		
		this.generatorManager.getGenerators().keySet().forEach(uuid -> {

			List<Generator> gens = this.generatorManager.getGenerators(uuid);
			
			Config config = new Config(Config.GENERATOR_CHACHE_PATH, uuid.toString());
			
			config.set("Generators", null);
			
			for(int i = 1; i < gens.size() + 1; i++) {
				
				config.set("Generators." + i + ".Tier", gens.get(i - 1).getGeneratorLevel());
				config.set("Generators." + i + ".Location.World", gens.get(i - 1).getBlock().getLocation().getWorld().getName());
				config.set("Generators." + i + ".Location.X", gens.get(i - 1).getBlock().getLocation().getBlockX());
				config.set("Generators." + i + ".Location.Y", gens.get(i - 1).getBlock().getLocation().getBlockY());
				config.set("Generators." + i + ".Location.Z", gens.get(i - 1).getBlock().getLocation().getBlockZ());
				
			}
			
		});
		
	}
	
}
