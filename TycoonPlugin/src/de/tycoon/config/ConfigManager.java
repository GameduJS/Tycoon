package de.tycoon.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import de.tycoon.TycoonPlugin;

public class ConfigManager {

	/* Main */
	private TycoonPlugin plugin;
	
	/* Setting Configuration */
	private Config settingConfig;
	
	/* Discord Bot Configuration */
	private Config discordConfig;
	
	/* Generator Configurations */
	private List<Config> generatorConfigs;
	
	public ConfigManager() {
		this.plugin = TycoonPlugin.get();
		this.settingConfig = new Config(Config.SETTING_PATH, "Settings");
		this.discordConfig = new Config(Config.DISCORD_SETTING_PATH, "Bot");
		this.generatorConfigs = new ArrayList<>();
		
		this.generatorConfigListSetup();
		this.initGeneratorConfigSetup();
		this.initSettingsConfigurationSetup();
	}
	
	private void generatorConfigListSetup() {
		
		/* Amount of tiers (Later on Setting Configuration) */
		int amount = (int) this.settingConfig.getOrDefault("Generators.Max-Tiers", 15);
		
		/* Check if given amount is smaller than 15 */
		// amount = (int) Math.min(amount, 15);
		
		/* Set configuration into list */
		for(int i = 1; i < amount + 1; i++) {
			this.generatorConfigs.add(
					new Config(Config.GEN_SETTING_PATH, "Tier " + i)
			);
		}
		
	}
	
	private void initGeneratorConfigSetup() {
		
		/* Loop through each configuration in list and check if it's "empty" */
		
		if(this.generatorConfigs.isEmpty()) return;
		
		this.generatorConfigs.forEach(config -> {
			
			/**
			 * <code>config#contains</code>
			 * Check if the base path is set, if one of the subpaths is removed by an operator it isn't the problem of the code
			 */
			if(!config.contains("Generator")) {
				
					//Generator Tier X
					String name = config.getFileName().replace(".yml", "");
					int i = Integer.parseInt(name.split(" ")[name.split(" ").length - 1]);
					Material materialBlock = null;
					Material materialToDrop = null;
					int upgradePrice = 0;
					int dropPrice = 0;
					
					switch(i) {
					case 1: {
						materialBlock = Material.HAY_BLOCK;
						materialToDrop = Material.WHEAT;
						
						upgradePrice = 500;
						dropPrice = 5;
						break;
					}
					case 2: {
						materialBlock = Material.MELON;
						materialToDrop = Material.MELON_SLICE;
						
						upgradePrice = 2000;
						dropPrice = 15;
						break;
					}
					case 3: {
						materialBlock = Material.PUMPKIN;
						materialToDrop = Material.PUMPKIN_PIE;
						
						upgradePrice = 10000;
						dropPrice = 30;
						break;
					}
					case 4: {
						materialBlock = Material.COAL_BLOCK;
						materialToDrop = Material.COAL;
						
						upgradePrice = 100000;
						dropPrice = 50;
						break;
					}
					case 5: {
						materialBlock = Material.IRON_BLOCK;
						materialToDrop = Material.IRON_INGOT;
						
						upgradePrice = 200000;
						dropPrice = 100;
						break;
					}
					case 6: {
						materialBlock = Material.LAPIS_BLOCK;
						materialToDrop = Material.LAPIS_LAZULI;
						
						upgradePrice = 500000;
						dropPrice = 250;
						break;
					}
					case 7: {
						materialBlock = Material.REDSTONE_BLOCK;
						materialToDrop = Material.REDSTONE;
						
						upgradePrice = 750000;
						dropPrice = 350;
						break;
					}
					case 8: {
						materialBlock = Material.GOLD_BLOCK;
						materialToDrop = Material.GOLD_INGOT;
						
						upgradePrice = 1000000;
						dropPrice = 500;
						break;
					}
					case 9: {
						materialBlock = Material.DIAMOND_BLOCK;
						materialToDrop = Material.DIAMOND;
						
						upgradePrice = 2000000;
						dropPrice = 1000;
						break;
					}
					case 10: {
						materialBlock = Material.EMERALD_BLOCK;
						materialToDrop = Material.EMERALD;
						
						upgradePrice = 5000000;
						dropPrice = 2000;
						break;
					}
					case 11: {
						materialBlock = Material.NETHERITE_BLOCK;
						materialToDrop = Material.NETHERITE_INGOT;
						
						upgradePrice = 10000000;
						dropPrice = 5000;
						break;
					}
					case 12: {
						materialBlock = Material.MAGMA_BLOCK;
						materialToDrop = Material.MAGMA_CREAM;
						
						upgradePrice = 15000000;
						dropPrice = 7500;
						break;
					}
					case 13: {
						materialBlock = Material.OBSIDIAN;
						materialToDrop = Material.FLINT;
						
						upgradePrice = 25000000;
						dropPrice = 10000;
						break;
					}
					case 14: {
						materialBlock = Material.SLIME_BLOCK;
						materialToDrop = Material.SLIME_BALL;
						
						upgradePrice = 50000000;
						dropPrice = 15000;
						break;
					}
					case 15: {
						materialBlock = Material.HONEY_BLOCK;
						materialToDrop = Material.HONEYCOMB;
						
						upgradePrice = 100000000;
						dropPrice = 25000;
						break;
					}
					default: {
						materialBlock = Material.DIRT;
						materialToDrop = Material.DIRT;
						
						upgradePrice = 0;
						dropPrice = 0;
						break;
					}
					}
					
					config.set("Generator.Name", "Generator Tier " + i);
					config.set("Generator.Lore", Arrays.asList("&7A powerful and unlimited source!", "&aPresent of a god"));
					config.set("Generator.Tier", i);	
					
					config.set("Generator.Block", materialBlock.toString());
					config.set("Generator.UpgradePrice", upgradePrice);
					
					config.set("Generator.Drop.Material", materialToDrop.toString());
					config.set("Generator.Drop.Price", dropPrice);
				
			}
			
		});
		
	}
	
	
	private void initSettingsConfigurationSetup() {
		
		if(!this.settingConfig.contains("Generators")) {
			this.settingConfig.set("Generators.Enabled", true);
			this.settingConfig.set("Generators.Scheduler-Time", 15);
			this.settingConfig.set("Generators.Generator-Limit", 20);
			this.settingConfig.set("Generators.Max-Tiers", 15);
		}
		
	}
	
	public Config getSettingConfig() {
		return settingConfig;
	}
	
	public List<Config> getGeneratorConfigs() {
		return generatorConfigs;
	}
	
	public Config getDiscordConfig() {
		return discordConfig;
	}
	
	public Config getConfigutationByTier(int tier) {
		
		if(tier > this.settingConfig.getInt("Generators.Max-Tiers") || tier < 0)
			throw new IllegalArgumentException("This is an invalid tier value " + tier);
		
		for(Config config : this.generatorConfigs) {
			if(Integer.valueOf(config.getFileName().replace(".yml", "").split(" ")[config.getFileName().split(" ").length - 1]) == tier) {
				return config;
			}
		}
		return null;
	}
	
	
}
