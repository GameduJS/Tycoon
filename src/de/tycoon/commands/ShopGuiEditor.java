package de.tycoon.commands;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tycoon.TycoonPlugin;
import de.tycoon.shop.ShopHandler;

public class ShopGuiEditor implements CommandExecutor{

	private TycoonPlugin plugin;
	private ShopHandler shopHandler;
	
	public ShopGuiEditor() {
		this.plugin = TycoonPlugin.get();
		this.shopHandler = this.plugin.getShopHandler();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player))
			return true;
		
		Player player = (Player) sender;
		
		if(args.length < 1)
			return true;
	
		if(!NumberUtils.isDigits(args[0])) return true;
		
		if(!player.hasPermission("tycoon.editshop")) return true;
		
		int index = Integer.parseInt(args[0]);
		
		if(index	 > this.shopHandler.getShopInventories().length) {
			player.sendMessage("Number is to long");
			return true;
		}
		
		if(index - 1 < 0) {
			player.sendMessage("Number is to short");
			return true;
		}
		
		this.shopHandler.setEditMode(player, true);
		player.openInventory(this.shopHandler.getShopInventories()[index - 1]);
			
		return false;
	}

	
	
}
