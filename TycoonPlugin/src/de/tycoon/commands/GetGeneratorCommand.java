package de.tycoon.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tycoon.TycoonPlugin;
import de.tycoon.config.Config;

public class GetGeneratorCommand implements CommandExecutor {

	private Config config = TycoonPlugin.get().getConfigManager().getConfigutationByTier(1);
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		ItemStack gen = new ItemStack(Material.valueOf(this.config.getString("Generator.Block").toUpperCase()));
		gen.setAmount(1);
		ItemMeta genMeta = gen.getItemMeta();
		
		List<String> lores = new ArrayList<>();
			for(String lore : this.config.getStringList("Generator.Lore")) {
				lores.add(lore.replace('&', '§'));
			}
		genMeta.setLore(lores);
		
		genMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.config.getString("Generator.Name")));
		gen.setItemMeta(genMeta);
		
		player.getInventory().addItem(gen);
		
		return false;
	}
	
}
