package de.tycoon.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;
import de.tycoon.config.ConfigManager;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.generators.GeneratorManager;
import de.tycoon.generators.GeneratorUpgradManager;
import de.tycoon.generators.generator.Generator;
import de.tycoon.generators.generator.GeneratorBlock;
import de.tycoon.language.LanguageMessageUtils;
import de.tycoon.language.Messages;

public class BlockInteract implements Listener{

	private TycoonPlugin plugin;
	
	private GeneratorManager genManager;
	private ConfigManager configManager;
	private GeneratorUpgradManager generatorUpgradManager;
	private UserManager userManager;
	private LanguageMessageUtils messageUtils;
	
	private Map<UUID, Long> cooldown;
	
	 public BlockInteract() {

		 this.plugin = TycoonPlugin.get();
		 this.genManager = this.plugin.getGeneratorManager();
		 this.configManager = this.plugin.getConfigManager();
		 this.generatorUpgradManager = new GeneratorUpgradManager();
		 this.userManager = this.plugin.getUserManager();
		 this.messageUtils = new LanguageMessageUtils(Messages.PREFIX.getMessage());
		 this.cooldown = new HashMap<>();
	 }
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		
		/* Check if Block is Generator */
		if(player.getInventory().getItemInMainHand() == null) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		
		if(item.getItemMeta() == null) return;
		if(item.getItemMeta().getDisplayName() == null) return;
		if(!item.getItemMeta().hasLore()) return;
		if(item.getItemMeta().getLore().size() == 0) return;
		if(!item.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', "&7A powerful and unlimited source!"))) return;
		/** ;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;**/
		
		Generator gen = null;

		/* Assign generator */
		String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
		int tier = Integer.valueOf(name.split(" ")[name.split(" ").length - 1]);
		
		Config config = this.configManager.getConfigutationByTier(tier);
		String genName = config.getString("Generator.Name");
		List<String> genLores = config.getStringList("Generator.Lore");
		
		Material genMaterial = Material.valueOf(config.getString("Generator.Block"));
		Material dropMaterial = Material.valueOf(config.getString("Generator.Drop.Material"));
		/** ;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;-;**/
		
		/* Add generator to player */
		gen = new Generator(name, tier, dropMaterial, new GeneratorBlock(e.getBlock().getLocation(), genMaterial, genName, genLores));
		this.genManager.addGenerator(player.getUniqueId(), gen);
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		
		/* Check if breaking block is a generator */
		if(this.genManager.getGenerators(player.getUniqueId()) == null) return;
		
		Location location = e.getBlock().getLocation();
		List<Generator> generatorsOfPlayers = this.genManager.getGenerators(player.getUniqueId());
		
		/** Check a bypass permission **/
		
		for(Generator gens : generatorsOfPlayers) {
			
			if(player.getGameMode() == GameMode.CREATIVE) return;
			
			if(gens.getBlock().getLocation().equals(location)) {
				e.setCancelled(true);
				break;
			}
		}
		
	}
	
	@EventHandler
	public void interactEvent(PlayerInteractEvent e) {
		
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			generatorRemove(e.getPlayer(), e.getClickedBlock());
		}
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			generatorUpgrade(e.getPlayer(), e.getClickedBlock());
		}
		
	}
	
	private void generatorUpgrade(Player player, Block clickedBlock) {
		
		/* Check if player is sneaking */
		if(!player.isSneaking()) return;
		/* Check if the clicked block is not null */
		if(clickedBlock == null || clickedBlock.getType() == Material.AIR) return;
		/* Check if the player has Generators */
		if(this.genManager.getGenerators(player.getUniqueId()) == null) return;
		
		/* Check if block is a generator */
		Location location = clickedBlock.getLocation();
		List<Generator> generatorsOfPlayers = this.genManager.getGenerators(player.getUniqueId());
		
		Generator genToUpgrade = null;
		
		for(Generator gens : generatorsOfPlayers) {
			if(gens.getBlock().getLocation().equals(location)) {
				genToUpgrade = gens;
				break;
			}
		}
		
		if(genToUpgrade == null) return;
		
		if(cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {

			User user = this.userManager.loadUser(player);
			int upgradePrice = this.configManager.getConfigutationByTier(genToUpgrade.getGeneratorLevel()).getInt("Generator.UpgradePrice");
			
			if(upgradePrice > user.getBalance()) {
				player.sendMessage(this.messageUtils.get(player, Messages.GENERATOR_NOT_ENOUGH_MONEY).replace("%more_money%", upgradePrice - user.getBalance() + ""));
				return;
			}
			
			int status = this.generatorUpgradManager.upgrade(genToUpgrade, player.getUniqueId());
			
			if(status == -1) {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				player.sendMessage(this.messageUtils.get(player, Messages.GENERATOR_UPGRADE));
				user.removeMoney(upgradePrice);
			}
			
		} else {
			cooldown.put(player.getUniqueId(), System.currentTimeMillis() + 20);
		}
		
		
	}

	public void generatorRemove(Player player, Block clickBlock) {
		
		/* Check if the clicked block is not null */
		if(clickBlock== null || clickBlock.getType() == Material.AIR) return;
		/* Check if the player has Generators */
		if(this.genManager.getGenerators(player.getUniqueId()) == null) return;
		
		/* Check if block is a generator */
		Location location = clickBlock.getLocation();
		List<Generator> generatorsOfPlayers = this.genManager.getGenerators(player.getUniqueId());
		
		Generator genToRemove = null;
		
		for(Generator gens : generatorsOfPlayers) {
			if(gens.getBlock().getLocation().equals(location)) {
				genToRemove = gens;
				break;
			}
		}
		
		if(genToRemove == null) return;
		
		this.genManager.removeGenerator(player.getUniqueId(), genToRemove);
		genToRemove.getBlock().getLocation().getBlock().setType(Material.AIR);
		player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
		player.sendMessage(this.messageUtils.get(player, Messages.GENERATOR_REMOVE));
		player.getInventory().addItem(genToRemove.getGeneratorItem(this.configManager));
		
	}
	
	
	
}
