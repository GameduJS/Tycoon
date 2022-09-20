package de.tycoon.language;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class LanguageRegisterCommand extends LanguageHandler implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		//Just simplifying the work, that I don't have to write sender
		CommandSender player = sender;
		
		if(!player.isOp() && !player.hasPermission("clan.language")) {
			player.sendMessage("§7I'm sorry but only player with op or permission clan.language can edit the language");
			return true;
		}
		
		if(args.length == 0) {
			
			player.sendMessage("§7--------------------------------------------------");
			player.sendMessage("§bAlright let's see so you wanna register a new language");
			player.sendMessage(" ");
			player.sendMessage("§bUse §c/language new [langToken] [langconfigenumkey] [sentence]");
			player.sendMessage(" ");
			player.sendMessage("§7So what's the lang token? thats the shortcut of the language e.g §6 Deutschland -> de");
			player.sendMessage(" ");
			player.sendMessage("§7How to get the §clangconfigenumkey§7? Simply use §c/language configkeys");
			player.sendMessage("§7--------------------------------------------------");
			
		}
		
		if(args.length == 1) {

			String subCommandChoose = args[0];
			
			//Get configkeys
			if(subCommandChoose.equalsIgnoreCase("configkeys")) {
				
				player.sendMessage("§b§l§nAlright here are some configkeys:");
				
				for(Messages config : Messages.values()) {
					
					player.sendMessage("§7- §b" + config.getPath());
					
				}
			}
			
			
		}
		
		if(args.length >= 4) {
		
			String subCommandChoose = args[0];
			String langToken = args[1];
			String langConfigEnumKey = args[2];
			
			//new language sentence
			if(subCommandChoose.equalsIgnoreCase("new")) {
				
				//Check if the given configkeys is equal to one of the langconfigenum
				boolean contains = false;
				for(Messages c : Messages.values()) {
					if(langConfigEnumKey.equalsIgnoreCase(c.getPath())) {
						langConfigEnumKey = c.getPath();
						contains = true;
						break;
					}
				}
				
				if(!contains) {
					player.sendMessage("§bOh sorry this configKey is not available");
					return true;
				}
				
				StringBuilder sb = new StringBuilder();
				for(int i = 3; i < args.length; i++) {
					if(!(i == args.length - 1)) {
						sb.append(args[i] + " ");
					} else {
						sb.append(args[i]);
					}
				}
				
				String sentence = sb.toString();
				
				this.registerLanguage(langToken, langConfigEnumKey, sentence);
				this.saveLanguageChanges();
				player.sendMessage("§bSuccesfully added the sentence§r " + ChatColor.translateAlternateColorCodes('&', sentence));
				
			}
			
			
		}
		
		return false;
	}

	
	
}
