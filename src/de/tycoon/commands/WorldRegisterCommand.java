package de.tycoon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tycoon.TycoonPlugin;
import de.tycoon.handlers.WorldHandler;

public class WorldRegisterCommand implements CommandExecutor{

	private WorldHandler worldHandler;
	
	public WorldRegisterCommand() {
		this.worldHandler = TycoonPlugin.get().getWorldHandler();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player))
			return true;
			
		Player player = (Player) sender;
		
		if(!player.hasPermission("tycoon.map")) {
			player.sendMessage("§c§lYou lack the permission §7tycoon.map");
			return true;
		}
		
		if(args.length == 0) {
			player.sendMessage("§6§lTYCOON §7Map");
			player.sendMessage("\n");
			player.sendMessage("§c§lSubcommands: ");
			player.sendMessage("§b/map create [MAPNAME]	§bCreate a new map");
			player.sendMessage("§b/map remove [MAPNAME]	§bRemoce a map");
			player.sendMessage("§b/map tp [MAPNAME]	§bTeleport to a map");
			return true;
		}
		
		if(args.length == 1) {
			player.sendMessage("§6§lTYCOON §7Map");
			player.sendMessage("\n");
			player.sendMessage("§c§lSubcommands: ");
			player.sendMessage("§b/map create [MAPNAME]	§bCreate a new map");
			player.sendMessage("§b/map remove [MAPNAME]	§bRemoce a map");
			player.sendMessage("§b/map tp [MAPNAME]	§bTeleport to a map");
			return true;
		}
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("create")) {
				this.worldHandler.registerNewWorld(args[1]);
			}
			if(args[0].equalsIgnoreCase("remove")) {
				this.worldHandler.removeWorld(args[1]);
			}
			if(args[0].equalsIgnoreCase("tp")) {
				player.teleport(this.worldHandler.getWorld(args[1]).getSpawnLocation());
			}
		}
		
		
		return false;
	}
	
}
