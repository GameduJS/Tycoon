package de.tycoon.threads;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.ConfigManager;
import de.tycoon.economy.UserManager;
import de.tycoon.generators.GeneratorConfigManager;
import de.tycoon.generators.GeneratorManager;

public class Threads {

	/*----------------------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private BukkitTask spawnRunnable;
	@SuppressWarnings("unused")
	private BukkitTask savingRunnable;
	/*----------------------------------------------------------------------------------*/
	
	
	/*----------------------------------------------------------------------------------*/
	private TycoonPlugin plugin;
	
	private GeneratorManager generatorManager;
	
	private ConfigManager configManager;
	
	private GeneratorConfigManager generatorConfigManager;
	
	private UserManager userManager;
	/*----------------------------------------------------------------------------------*/
	
	
	public Threads() {
		
		this.plugin = TycoonPlugin.get();
		
		this.generatorManager = this.plugin.getGeneratorManager();
		
		this.configManager = this.plugin.getConfigManager();
		
		this.generatorConfigManager = this.plugin.getGeneratorConfigManager();
		
		this.userManager = this.plugin.getUserManager();
		
	}
	
	
	public void startSpawnThread() {
		
		 this.spawnRunnable = new BukkitRunnable() {
			
				@Override
				public void run() {
					
					generatorManager.spawn();
					
				}
				
		}.runTaskTimer(plugin, 0, configManager.getSettingConfig().getInt("Generators.Scheduler-Time") * 20);
		
	}
	
	
	public void startSavingThread() {
		
		this.savingRunnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.getOnlinePlayers().forEach(player -> {
					//Check if player has enabled notifications
					player.sendTitle("§cSaving player balance and generators", "§7It could be a bit laggy", 5, 50, 5);
				});

				generatorConfigManager.saveGenerators();
				userManager.savePlayerBalance();
				
			}
			
		}.runTaskTimerAsynchronously(plugin, 0, 5 * 60 * 20);
		
	}
	
}
