package de.tycoon.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.tycoon.TycoonPlugin;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TokenMineEvents implements Listener{

	private TycoonPlugin plugin;
	private UserManager userManager;
	
	public TokenMineEvents() {
		this.plugin = TycoonPlugin.get();
		this.userManager = this.plugin.getUserManager();
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		
		if(player.getLocation().getWorld().getName().equals("TokenMine")) {
			
			//Check edit mode
			
			e.setCancelled(true);
			
			User user = this.userManager.loadUser(player.getUniqueId());
			Block block = e.getBlock();
			
			if(block.getType().equals(Material.DIAMOND)) {
				user.addTokens(8);
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bMined diamond ore §7(§6+8 Tokens§7)"));
			}
			
			if(block.getType().equals(Material.GOLD_ORE)) {
				user.addTokens(4);
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bMined gold ore §7(§6+4 Tokens§7)"));
			}
			
			if(block.getType().equals(Material.IRON_ORE)) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bMined iron ore §7(§6+2 Tokens§7)"));
				user.addTokens(2);
			}
			
		}
		
	}
	
}
