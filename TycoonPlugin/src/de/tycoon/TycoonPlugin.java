package de.tycoon;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.tycoon.commands.EconomyCommand;
import de.tycoon.commands.GetGeneratorCommand;
import de.tycoon.commands.SellCommand;
import de.tycoon.config.ConfigManager;
import de.tycoon.discord.DiscordBot;
import de.tycoon.discord.commands.ImageCreator;
import de.tycoon.discord.commands.basecommand.CommandListener;
import de.tycoon.discord.commands.basecommand.CommandManager;
import de.tycoon.economy.UserManager;
import de.tycoon.events.BlockInteract;
import de.tycoon.generators.GeneratorConfigManager;
import de.tycoon.generators.GeneratorManager;
import de.tycoon.handlers.WorldHandler;
import de.tycoon.language.LanguageHandler;
import de.tycoon.threads.Threads;

public class TycoonPlugin extends JavaPlugin{

	private static TycoonPlugin INSTANCE;
	
	private PluginManager pluginManager;
	
	private ConfigManager config;
	private GeneratorManager generatorManager;
	
	private DiscordBot discordBot;
	
	private UserManager userManager;
	
	private GeneratorConfigManager generatorConfigManager;
	
	private LanguageHandler languageHandler;
	
	private WorldHandler worldHandler;
	
	
	/** Discord **/
	private CommandManager commandManager;
	
	private Threads threads;
	
	@Override
	public void onEnable() {

		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling Tycoon Plugin");
		
		INSTANCE = this;
		
		this.pluginManager = Bukkit.getPluginManager();
		
		this.config = new ConfigManager();

		this.generatorManager = new GeneratorManager();
		
		this.generatorConfigManager = new GeneratorConfigManager();
		this.generatorConfigManager.loadGenerators();
		
		this.languageHandler = new LanguageHandler();
		this.languageHandler.setDefaultValues();
		
		this.userManager = new UserManager();
		this.userManager.loadPlayersBalances();
		
		this.worldHandler = new WorldHandler();
		this.worldHandler.loadWorlds();
		
		this.commandManager = new CommandManager();
		
		this.discordBot = new DiscordBot();
		this.registerDiscordEvents();
		this.regiserDiscordCommands();
		this.discordBot.start();
		
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
//		
		this.discordBot.stop();
		this.generatorConfigManager.saveGenerators();
		this.userManager.savePlayerBalance();

		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Disbling Tycoon Plugin");
		
		
	}
	
	private void registerEvents() {
		this.pluginManager.registerEvents(new BlockInteract(), this);
	}
	
	private void registerDiscordEvents() {
		this.discordBot.addEvent(new CommandListener());
	}
	
	private void regiserDiscordCommands() {
		this.commandManager.getCommand("img").setExecuter(new ImageCreator());
	}
	

	private void registerCommands() {
		
		this.getCommand("getgen").setExecutor(new GetGeneratorCommand());
		this.getCommand("eco").setExecutor(new EconomyCommand());
		this.getCommand("sell").setExecutor(new SellCommand());
		
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
	
	/* UserManager Getter */
	public UserManager getUserManager() {
		return userManager;
	}
	
	/* CommandManager Getter */
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	
}
