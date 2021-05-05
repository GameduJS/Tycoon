package de.tycoon.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.cassandra.io.util.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

public class WorldHandler {

	public Map<String, World> worlds;
		
	public WorldHandler() {
		this.worlds = new HashMap<>();
	}
	
	
	public void loadWorlds() {
		Bukkit.getWorlds().forEach(world -> {
			new WorldCreator(world.getName()).createWorld();
			this.worlds.put(world.getName(), world);
		});
	}
	
	public void sendPlayerTo(Player player, String world) {
		if(!this.worlds.keySet().contains(world))
			return;
		player.teleport(this.worlds.get(world).getSpawnLocation());
	}
	
	public World registerNewWorld(String name) {
		if(this.worlds.keySet().contains(name))
			return this.worlds.get(name);
		
		WorldCreator wc = new WorldCreator(name);
		wc.generator(new ChunkGenerator() {
			@Override
			public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
				return createChunkData(world);
			}
		});
		World world = wc.createWorld();
		this.worlds.put(name, world);
		
		return world;
	}
	
	public void removeWorld(String name) {
		if(!this.worlds.keySet().contains(name))
			return;
		
		Bukkit.unloadWorld(this.worlds.get(name), false);
		this.worlds.remove(name);

		FileUtils.delete(name);
	}
	
	public World getWorld(String name) {
		return this.worlds.getOrDefault(name, Bukkit.getWorld("world"));
	}
	
	public Set<String> getExistingWorlds(){
		return this.worlds.keySet();
	}
	
}
