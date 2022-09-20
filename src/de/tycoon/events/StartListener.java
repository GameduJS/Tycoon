package de.tycoon.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.tycoon.TycoonPlugin;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.util.BukkitUtils;

public class StartListener implements Listener{

	private TycoonPlugin plugin;
	private UserManager userManager;
	
	public StartListener() {
		this.plugin = TycoonPlugin.get();
		this.userManager = this.plugin.getUserManager();
	}
	
	@EventHandler
	public void onStart(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		if(!player.hasPlayedBefore()) {

			e.setJoinMessage("§6" + player.getName() + " §7is new to our server! §5#" + Bukkit.getServer().getOfflinePlayers().length);
			
			User user = this.userManager.loadUser(player.getUniqueId());
			user.addMoney(750);
			
			player.getInventory().addItem(BukkitUtils.getTierOneGeneratorWithShopLore(null, true));
		}
		
		e.setJoinMessage(null);
	}
	
}
