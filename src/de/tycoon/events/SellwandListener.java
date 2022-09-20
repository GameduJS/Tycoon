package de.tycoon.events;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.handlers.SellHandler;
import de.tycoon.language.LanguageMessageUtils;
import de.tycoon.language.Messages;
import de.tycoon.sellwand.Sellwand;
import de.tycoon.sellwand.SellwandManager;

public class SellwandListener implements Listener{

	private TycoonPlugin plugin;
	private SellwandManager sellwandManager;
	private ConfigManager configManager;
	private SellHandler sellHandler;
	private UserManager userManager;
	private LanguageMessageUtils messageUtils;
	
	private Map<Material, Double> sellPrices;
	private Map<Material, Double> xp;
	
	public SellwandListener() {
		this.plugin = TycoonPlugin.get();
		this.sellwandManager = this.plugin.getSellwandManager();
		this.configManager = this.plugin.getConfigManager();
		this.sellHandler = new SellHandler();
		this.userManager = this.plugin.getUserManager();
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
	/*
	 * if(itemStack.getItemMeta().getDisplayName().replace("§", "&").equals("§7§kxX §6Sellwand §7(" + sellwand.getMultiplier() + "x) §7§kXx".replace("§", "&"))) {
				System.out.println("Test - 1");
				if(replace(itemStack.getItemMeta().getLore()).equals(replace(sellwand.getSellwandItem().getItemMeta().getLore()))) {
					System.out.println("Test - 2");
					return true;
					
				}
			}
	 */
	
	@EventHandler
	public void onChestClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(e.getClickedBlock().getType() != Material.CHEST) return;
		if(!(e.getClickedBlock().getState() instanceof Chest)) return;
		if(!this.sellwandManager.isSellwand(player.getInventory().getItemInMainHand())) return;
		e.setCancelled(true);

		
		Sellwand sellwand = this.sellwandManager.getSellwandByItem(player.getInventory().getItemInMainHand());
		
		Chest chest = (Chest) e.getClickedBlock().getState();
		
		if(chest.getInventory().isEmpty()) return;
		
		double amount = 0;
		double payout = 0;
		double gainedXPAmount = 0;
		
		for(Material material : this.sellPrices.keySet()) {
			
			if(chest.getInventory().contains(material)) {
				amount += this.sellHandler.getAmount(chest.getInventory(), material);
				this.sellHandler.removeItems(chest.getInventory(), material);
				payout += amount * this.sellPrices.get(material);
				gainedXPAmount+=amount * this.xp.get(material);
			}
			
		}
		
		User user = this.userManager.loadUser(player.getUniqueId());
		
		payout *= sellwand.getMultiplier();
		gainedXPAmount *= sellwand.getMultiplier();
		
		if(amount != 0) {
			user.addMoney(payout );
			user.addXP(gainedXPAmount);
			player.sendMessage(this.messageUtils.get(player, Messages.ECONOMY_SELL).replace("%money%", new DecimalFormat("#,###.#").format( payout ) ) + "§b You gained §a"+ gainedXPAmount + " XP§3 (" 
					+ Math.round((user.getUserLevel().getCurrentLevelXP() / user.getUserLevel().getXPNeeded()) * 100) + "% to " + (user.getUserLevel().getLevel() + 1) + ")");
		}
		
		
	}
	
}
