package de.tycoon.threads;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.ConfigManager;
import de.tycoon.generators.GeneratorManager;

public class Threads {

	private BukkitTask spawnRunnable;
	
	private TycoonPlugin plugin;
	private GeneratorManager generatorManager;
	private ConfigManager configManager;
	
	
	public Threads() {
		this.plugin = TycoonPlugin.get();
		this.generatorManager = this.plugin.getGeneratorManager();
		this.configManager = this.plugin.getConfigManager();
	}
	
	public void startSpawnThread() {
		
		 this.spawnRunnable = new BukkitRunnable() {
			
				@Override
				public void run() {
					generatorManager.spawn();
				}
				
		}.runTaskTimer(plugin, 0, configManager.getSettingConfig().getInt("Generators.Scheduler-Time") * 20);
				

		
	}
	
	
}
