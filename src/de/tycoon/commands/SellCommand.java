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
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.handlers.SellHandler;
import de.tycoon.language.LanguageMessageUtils;
import de.tycoon.language.Messages;

public class SellCommand implements CommandExecutor{

	private TycoonPlugin plugin;
	private ConfigManager configManager;
	private UserManager userManager;
	private SellHandler sellHandler;
	private LanguageMessageUtils messageUtils;
	
	private Map<Material, Double> sellPrices;
	private Map<Material, Double> xp;
	
	
	public SellCommand() {
		this.plugin = TycoonPlugin.get();
		this.configManager = this.plugin.getConfigManager();
		this.userManager = this.plugin.getUserManager();
		this.sellHandler = new SellHandler();
		this.messageUtils = new LanguageMessageUtils(Messages.PREFIX.getMessage());
		this.sellPrices = new HashMap<>();
		this.xp = new HashMap<>();
		this.initPrices();
	}
	
	private void initPrices() {
		for(Config config : this.configManager.getGeneratorConfigs()) {
			this.sellPrices.put(Material.valueOf(config.getString("Generator.Drop.Material")), config.getDouble("Generator.Drop.Price"));
			this.xp.put(Material.valueOf(config.getString("Generator.Drop.Material")), config.getDouble("Generator.Drop.XP"));
		}
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		double amount = 0;
		double payout = 0;
		double gainedXPAmount = 0;
		
		for(Material material : this.sellPrices.keySet()) {
			
			if(player.getInventory().contains(material)) {
				amount += this.sellHandler.getAmount(player.getInventory(), material);
				this.sellHandler.removeItems(player.getInventory(), material);
				payout += amount * this.sellPrices.get(material);
				gainedXPAmount+=amount * this.xp.get(material);
			}
			
		}
		
		User user = this.userManager.loadUser(player.getUniqueId());
		
		if(amount != 0) {
			user.addMoney(payout);
			user.addXP(gainedXPAmount);
			player.sendMessage(this.messageUtils.get(player, Messages.ECONOMY_SELL).replace("%money%", new DecimalFormat("#,###.#").format( payout ) ) + "§b You gained §a"+ gainedXPAmount + " XP§3 (" 
					+ Math.round((user.getUserLevel().getCurrentLevelXP() / user.getUserLevel().getXPNeeded()) * 100) + "% to " + (user.getUserLevel().getLevel() + 1) + ")");
		}
		
		return false;
	}

	
	
}
