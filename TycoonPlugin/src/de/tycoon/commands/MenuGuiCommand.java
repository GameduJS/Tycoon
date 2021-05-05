package de.tycoon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tycoon.gui.Menus;

public class MenuGuiCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;
		
		Player player = (Player) sender;
		
		player.openInventory(Menus.getMenuInventory());
		
		return false;
	}

}
