package de.tycoon.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.generators.generator.Generator;

public class GeneratorManager {

	private ConfigManager configManager;
	
	private Map<UUID, List<Generator>> generators;
	private Map<UUID, Integer> generatorCount;
	private Config config;
	private int max_gens;
	
	public GeneratorManager() {
		this.generators = new HashMap<>();
		this.generatorCount = new HashMap<>();
		
		this.configManager = TycoonPlugin.get().getConfigManager();
		
		this.config = this.configManager.getSettingConfig();
		max_gens = this.config.getInt("Generators.Generator-Limit");
	}
	
	public void addGenerator(UUID uuid, Generator gen) {
		List<Generator> gens = this.generators.getOrDefault(uuid, new ArrayList<>());
		gens.add(gen);
		this.generators.put(uuid, gens);
		this.generatorCount.put(uuid, this.generatorCount.getOrDefault(uuid, 0) + 1);
	}
	
	public void removeGenerator(UUID uuid, Generator generator) {
		List<Generator> gens = this.generators.getOrDefault(uuid, new ArrayList<>());
		gens.remove(generator);
		this.generators.put(uuid, gens);
		this.generatorCount.put(uuid, this.generatorCount.getOrDefault(uuid, 0) - 1);
	}
	
	public List<Generator> getGenerators(UUID uuid){
		return this.generators.getOrDefault(uuid, null);
	}
	
	public boolean canPlaceGenerator(UUID uuid) {
		return getQuantityofGenerators(uuid) < max_gens;
	}
	
	public int getQuantityofGenerators(UUID uuid) {
		return this.generatorCount.getOrDefault(uuid, 0);
	}
	
	public int getMaxGens() {
		return max_gens;
	}
	
	
	
	public void spawn() {
		
		Bukkit.getOnlinePlayers().forEach(player -> {
			
			if(player.isOnline()) {
				if(this.getGenerators(player.getUniqueId()) != null) {
					getGenerators(player.getUniqueId()).forEach(gen -> {
						gen.spawn(gen.getBlock().getLocation());
					});
				}
			}
		});
		
	}
	
	public Map<UUID, List<Generator>> getGenerators(){
		return generators;
	}
	
}
