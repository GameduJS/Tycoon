package de.tycoon.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tycoon.TycoonPlugin;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.language.LanguageMessageUtils;
import de.tycoon.language.Messages;

public class EconomyCommand implements CommandExecutor {

	private TycoonPlugin plugin;
	private UserManager userManager;
	private LanguageMessageUtils messageUtils;
	
	public EconomyCommand() {
		this.plugin = TycoonPlugin.get();
		
		this.userManager = plugin.getUserManager();
		this.messageUtils = new LanguageMessageUtils(Messages.PREFIX.getMessage());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("bal") || label.equalsIgnoreCase("money")) {
			
			if(!(sender instanceof Player)) return true;
			Player player = (Player) sender;
			User user = this.userManager.loadUser(player.getUniqueId());

			player.sendMessage("" + user.getBalance());
			
		}
		
		if(label.equalsIgnoreCase("eco")) {
			
			if(args.length < 1) {
				sender.sendMessage("Please use /eco <add/remove/set> <amount> <player>");
				return true;
			}
			
			// /eco add 134 name
			if (args[0].equalsIgnoreCase("add")) {
				
				Player player = (Player) sender;
				Player target = null;
				
				if(args.length < 2) {
					player.sendMessage("Invalid amount!");
					return true;
				}
				
				double moneyToAdd = Double.parseDouble(args[1]);

				if(args.length < 3) {
					target = player;
				} else {
					target = Bukkit.getPlayer(args[2]);
					if(target == null) {
						player.sendMessage("Invalid Player");
						return true;
					}
				}
				
				User user = this.userManager.loadUser(target.getUniqueId());
				
				
				user.addMoney(moneyToAdd);
				player.sendMessage(this.messageUtils.get(player, Messages.ECONOMY_ADD_MONEY).replace("%money%", moneyToAdd + "").replace("%player%", target.getName()));
				
			}
			
		}
		
		return false;
	}

}
