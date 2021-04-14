package de.tycoon.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.tycoon.generators.generator.Generator;

public class GeneratorManager {

	public Map<UUID, List<Generator>> generators;
	
	public GeneratorManager() {
		this.generators = new HashMap<>();
	}
	
	public void addGenerator(UUID uuid, Generator gen) {
		List<Generator> gens = this.generators.getOrDefault(uuid, new ArrayList<>());
		gens.add(gen);
		this.generators.put(uuid, gens);
	}
	
	public void removeGenerator(UUID uuid, Generator generator) {
		List<Generator> gens = this.generators.getOrDefault(uuid, new ArrayList<>());
		gens.remove(generator);
		this.generators.put(uuid, gens);
	}
	
	public List<Generator> getGenerators(UUID uuid){
		return this.generators.getOrDefault(uuid, null);
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
			//Maybe spawn the half of the normal rate if player is offline
			
		});
		
	}
	
	public Map<UUID, List<Generator>> getGenerators(){
		return generators;
	}
	
}
