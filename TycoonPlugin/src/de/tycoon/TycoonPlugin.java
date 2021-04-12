package de.tycoon;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.tycoon.commands.EconomyCommand;
import de.tycoon.commands.GetGeneratorCommand;
import de.tycoon.config.ConfigManager;
import de.tycoon.discord.DiscordBot;
import de.tycoon.economy.UserManager;
import de.tycoon.events.BlockPlace;
import de.tycoon.generators.GeneratorConfigManager;
import de.tycoon.generators.GeneratorManager;
import de.tycoon.generators.generator.IBaseGenerator;
import de.tycoon.thread.Threads;

public class TycoonPlugin extends JavaPlugin{

	private static TycoonPlugin INSTANCE;
	
	private ConfigManager config;
	private PluginManager pluginManager;
	private GeneratorManager generatorManager;
	private DiscordBot discordBot;
	private UserManager userManager;
	private GeneratorConfigManager generatorConfigManager;
	
	private Threads threads;
	
	@Override
	public void onEnable() {

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling Tycoon Plugin");
		
		INSTANCE = this;
		this.pluginManager = Bukkit.getPluginManager();
		
		this.generatorManager = new GeneratorManager();
		
		this.config = new ConfigManager();
		
		this.generatorConfigManager = new GeneratorConfigManager();
		this.generatorConfigManager.loadGenerators();

		this.userManager = new UserManager();
//		this.userManager.loadPlayersBalances();
		
		this.discordBot = new DiscordBot();
		this.startDiscordBot();
		
		this.threads = new Threads();
		this.threads.startSpawnThread();
		
		this.registerCommands();
		
		/* Check if generator are enabled */
		if(this.config.getSettingConfig().getBoolean("Generators.Enabled")) {
			
			this.registerEvents();
			
		} else {
			
			Bukkit.getConsoleSender().sendMessage("§7-------------------------------------");
			Bukkit.getConsoleSender().sendMessage("");
			Bukkit.getConsoleSender().sendMessage("§c Gens are diabled through Config");
			Bukkit.getConsoleSender().sendMessage("");
			Bukkit.getConsoleSender().sendMessage("§7-------------------------------------");
			
		}
		
	}
	
	@Override
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Disbling Tycoon Plugin");
		
		this.generatorConfigManager.saveGenerators();
//		this.userManager.savePlayerBalance();
		
	}
	
	private void startDiscordBot() {
		try {
			this.discordBot.start();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
	
	private void registerEvents() {
		
		this.pluginManager.registerEvents(new BlockPlace(), this);
		
	}

	private void registerCommands() {
		this.getCommand("getgen").setExecutor(new GetGeneratorCommand());
		this.getCommand("bal").setExecutor(new EconomyCommand());
	}


	/* Plugin Getter */
	public static TycoonPlugin get() {
		return INSTANCE;
	}
	
	/* Configmanager Getter */
	public ConfigManager getConfigManager() {
		return config;
	}
	
	/* GeneratorManager Getter */
	public GeneratorManager getGeneratorManager() {
		return this.generatorManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	
}
