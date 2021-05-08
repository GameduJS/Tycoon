package de.tycoon.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.util.BukkitUtils;
import de.tycoon.util.ReloadBlock;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TokenMineEvents implements Listener{

	private static TokenMineEvents INSTANCE;
	
	private static final int TIME_RELOAD = 25;
	
	private TycoonPlugin plugin;
	private UserManager userManager;
	private ConfigManager configManager;
	
	private Config config;
	
	@SuppressWarnings("unused")
	private BukkitTask reloadBlockTask;
	private List<ReloadBlock> reloadedBlocks;
	
	private Map<Material, Integer> tokensPerMaterial;
	
	public TokenMineEvents() {
		INSTANCE = this;
		
		this.plugin = TycoonPlugin.get();
		this.userManager = this.plugin.getUserManager();
		this.configManager = this.plugin.getConfigManager();
		
		this.tokensPerMaterial = new HashMap<>();
		this.reloadedBlocks = new ArrayList<>();
		
		this.config = new Config(Config.TOKENMINE_PATH, "Settings");
		this.setConfigDefaults();
		this.loadConfig();
		
		this.startBlockReloader();
	}
	
	private void setConfigDefaults() {
		if(!this.config.contains("TokenMine.Minables")) {
			this.config.set("TokenMine.Minables", Arrays.asList(Material.DIAMOND_ORE.toString(), Material.GOLD_ORE.toString(), Material.IRON_ORE.toString()));
		}
		
		if(!this.config.contains("TokenMine.Tokens")) {
			this.config.set("TokenMine.Tokens.diamond_ore", 8);
			this.config.set("TokenMine.Tokens.gold_ore", 4);
			this.config.set("TokenMine.Tokens.iron_ore", 2);
		}
	}
	
	private void loadConfig() {

		for(String materialString : this.config.getStringList("TokenMine.Minables")) {
			
			Material currentMaterial = null;
			if(BukkitUtils.isValidMaterial(materialString)) {
				currentMaterial = Material.valueOf(materialString);
			}
			
			if(!currentMaterial.isBlock()) return;
			if(!this.config.contains("TokenMine.Tokens." + materialString.toLowerCase())) continue;
			
			this.tokensPerMaterial.put(currentMaterial, this.config.getInt("TokenMine.Tokens." + materialString.toLowerCase()));
			
		}
		
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		if(!(player.getGameMode() == GameMode.SURVIVAL)) return;
		
		if(!configManager.getSettingConfig().contains("TokenMineWorld")) return;
		
		
		if(player.getLocation().getWorld().getName().equalsIgnoreCase(this.configManager.getSettingConfig().getString("TokenMineWorld"))) {
			
			//Check edit mode
			
			e.setCancelled(true);
			
			User user = this.userManager.loadUser(player.getUniqueId());
			Block block = e.getBlock();
			
			if(this.tokensPerMaterial.keySet().contains(block.getType())) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bMined "
							+ StringUtils.capitalize(block.getType().toString().toLowerCase().replace("_ore", "")) +  " §7(§6+"+  this.tokensPerMaterial.get(block.getType() )+  " Tokens§7)"));
				user.addTokens(this.tokensPerMaterial.get(block.getType()));
				
				this.reloadedBlocks.add(new ReloadBlock(block, block.getType(), TIME_RELOAD));

				block.setType(Material.COBBLESTONE);
				
			} else {
				//Cancel if mined block is not contained in map
				e.setCancelled(true);
			}
			
		}
		
	}
	
	public void startBlockReloader(){
		this.reloadBlockTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Iterator<ReloadBlock> iterator = reloadedBlocks.iterator();
				while(iterator.hasNext()) {
					
					ReloadBlock reloadBlock = iterator.next();
					
					reloadBlock.setTime(reloadBlock.getTime() - 1);
					
					if(reloadBlock.getTime() == 0) {
						reloadBlock.getReloadBlock().setType(reloadBlock.getMaterialToReload());
						iterator.remove();
					}
					
				}
				
			}
		}.runTaskTimer(plugin, 0, 1 * 20);
	}
	
	public static void restore() {
		
		Iterator<ReloadBlock> iterator = INSTANCE.reloadedBlocks.iterator();
		while(iterator.hasNext()) {
			ReloadBlock currentReloadBlock = iterator.next();
			currentReloadBlock.getReloadBlock().setType(currentReloadBlock.getMaterialToReload());
			iterator.remove();
		}
		
	}
	
}
