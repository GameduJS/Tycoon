package de.tycoon.commands;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.economy.UserManager;
import de.tycoon.generators.GeneratorManager;
import de.tycoon.handlers.SellHandler;

public class SellCommand implements CommandExecutor{

	private TycoonPlugin plugin;
	private ConfigManager configManager;
	private GeneratorManager generatorManager;
	private UserManager userManager;
	private SellHandler sellHandler;
	
	private Map<Material, Double> sellPrices;
	
	
	public SellCommand() {
		this.plugin = TycoonPlugin.get();
		this.configManager = this.plugin.getConfigManager();
		this.generatorManager = this.plugin.getGeneratorManager();
		this.userManager = this.plugin.getUserManager();
		this.sellHandler = new SellHandler();
		this.sellPrices = new HashMap<>();
		this.initPrices();
	}
	
	private void initPrices() {
		for(Config config : this.configManager.getGeneratorConfigs()) {
			this.sellPrices.put(Material.valueOf(config.getString("Generator.Drop.Material")), config.getDouble("Generator.Drop.Price"));
		}
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
//		player.sendMessage("You have " + sellHandler.getAmount(player.getInventory(), Material.HONEYCOMB) + " honeycombs");
//		this.sellHandler.removeItems(player.getInventory(), Material.HONEYCOMB);
		
		int amount = 0;
		int payout = 0;
		
		for(Material material : this.sellPrices.keySet()) {
			
			if(player.getInventory().contains(material)) {
				amount += this.sellHandler.getAmount(player.getInventory(), material);
				this.sellHandler.removeItems(player.getInventory(), material);
				payout += amount * this.sellPrices.get(material);
			}
			
		}
		
		if(amount != 0) {
			player.sendMessage("You would get " + new DecimalFormat("#,###.#").format( payout ) + "€");
		}
		
		return false;
	}

	
	
}
