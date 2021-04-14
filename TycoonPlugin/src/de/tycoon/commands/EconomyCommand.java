package de.tycoon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.ConfigManager;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;

public class EconomyCommand implements CommandExecutor {

	private TycoonPlugin plugin;
	private ConfigManager configManager;
	private UserManager userManager;
	
	public EconomyCommand() {
		this.plugin = TycoonPlugin.get();
		this.configManager = plugin.getConfigManager();
		this.userManager = plugin.getUserManager();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(label.equalsIgnoreCase("bal") || label.equalsIgnoreCase("money")) {
			
			if(!(sender instanceof Player)) return true;
			Player player = (Player) sender;
			User user = this.userManager.loadUser(player);

			player.sendMessage("" + user.getBalance());
			
		}
		
		if(label.equalsIgnoreCase("eco")) {
			
			if (args[0].equalsIgnoreCase("add")) {
				
				Player player = (Player) sender;
				User user = this.userManager.loadUser(player);
				
				user.addMoney(1d);
				player.sendMessage("Added money!");
				
			}
			
		}
		
		return false;
	}

}
