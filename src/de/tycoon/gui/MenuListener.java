package de.tycoon.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tycoon.TycoonPlugin;
import de.tycoon.economy.User;
import de.tycoon.economy.UserManager;
import de.tycoon.shop.ShopHandler;
import de.tycoon.shop.ShopItem;
import de.tycoon.shop.ShopItemHandler;
import de.tycoon.util.BukkitUtils;

public class MenuListener implements Listener{

	private TycoonPlugin plugin;
	private ShopHandler shopHandler;
	private UserManager userManager;
	
	private Map<UUID, Integer> currentShopSite;
	
	private Inventory[] shopInventories;
	
	public MenuListener() {
		this.plugin = TycoonPlugin.get();
		this.shopHandler = this.plugin.getShopHandler();
		this.userManager = this.plugin.getUserManager();
		
		this.currentShopSite = new HashMap<>();
		
		this.shopInventories = this.shopHandler.getShopInventories();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		
		if(e.getRawSlot() == -999 || e.getRawSlot() == 999) return;
		if(e.getClickedInventory() == null) return;
		if(e.getCurrentItem() == null) return;
		if(!e.getCurrentItem().hasItemMeta()) return;
		if(!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
		
		
		/**-------------------------------------------------------------------------------**/
		/** When in menu																	   **/
		/**-------------------------------------------------------------------------------**/
		if(e.getView().getTitle().equals("§e§lTYCOON §7- Menu")) {
			
			e.setCancelled(true);
			
			/**-------------------------------------------------------------------**
			/** Settings Menu														 **/
			/**-------------------------------------------------------------------**/
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(
					Items.getSettingItemStack().getItemMeta().getDisplayName())) {
				player.openInventory(Menus.getSettingsInventory(player));
			}
			/**-------------------------------------------------------------------**/


			/**-------------------------------------------------------------------**/
			/** Shop Menu **/
			/**-------------------------------------------------------------------**/
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(
					Items.getShopItemStack().getItemMeta().getDisplayName())) {
				player.openInventory(this.shopInventories[0]);
				this.currentShopSite.put(player.getUniqueId(), 0);
			}
			/**-------------------------------------------------------------------**/

			
		}
		/**-------------------------------------------------------------------------------**/
		
		
		
		/**-------------------------------------------------------------------------------**/
		/** When in shop																		   **/
		/**-------------------------------------------------------------------------------**/
		if(e.getView().getTitle().contains("e§lTYCOON §aShop")) {
			
			if(this.shopHandler.isEditMode(player)) return;
			e.setCancelled(true);
			
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lNext page...")) {
				
				int currentShopSite = this.currentShopSite.get(player.getUniqueId());
				
				if(currentShopSite + 1 < shopHandler.getShopInventories().length) {
					currentShopSite = currentShopSite + 1;
					this.currentShopSite.put(player.getUniqueId(), currentShopSite);
					player.openInventory(shopInventories[ currentShopSite ] );
				}
				
			}
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lPrevious page...")) {
				
				int currentShopSite = this.currentShopSite.get(player.getUniqueId());

				if(currentShopSite > 0) {
					currentShopSite = currentShopSite - 1;
					this.currentShopSite.put(player.getUniqueId(), currentShopSite);	
					player.openInventory(shopInventories[ currentShopSite ] );
				}
				
			}
			
			ShopItemHandler shopItemHandler = this.shopHandler.getShopItemHandler();
			ShopItem shopItem = shopItemHandler.getItemStackByIndex(this.currentShopSite.get(player.getUniqueId()) + 1, e.getSlot());
			
			User user = this.userManager.loadUser(player.getUniqueId());
			
			if(e.getAction() == InventoryAction.PICKUP_ALL) {
				if(user.getBalance() < shopItem.getBuyPrice() * 64) return;
				
				user.removeMoney(shopItem.getBuyPrice() * 64);
				
				ItemStack toGive = null;
				ItemStack gen = BukkitUtils.getTierOneGeneratorWithShopLore(null, true);
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals(gen.getItemMeta().getDisplayName())) {
					toGive = gen;
				} else {
					toGive = new ItemStack(shopItem.getItemStack().getType());
				}
				
				toGive.setAmount(64);
				
				player.getInventory().addItem(toGive);
			}
			
			if(e.getAction() == InventoryAction.PICKUP_HALF) {
				if(user.getBalance() < shopItem.getBuyPrice() * 1) return;
				
				user.removeMoney(shopItem.getBuyPrice()  * 1);
				
				ItemStack toGive = null;
				ItemStack gen = BukkitUtils.getTierOneGeneratorWithShopLore(null, true);
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals(gen.getItemMeta().getDisplayName())) {
					toGive = gen;
				} else {
					toGive = new ItemStack(shopItem.getItemStack().getType());
				}
				
				toGive.setAmount(1);
				
				player.getInventory().addItem(toGive);
			}
			
			
//			int slot = e.getSlot();
			
			
		}
		
		
		if(e.getView().getTitle().contains("§e§lTYCOON §7- Personal settings")) {
			
			e.setCancelled(true);
			
		}
		
		
	}
	
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		
		Player player = (Player) e.getPlayer();

		if(!this.shopHandler.isEditMode(player)) return;
		if(!e.getView().getTitle().contains("e§lTYCOON §aShop")) return;
		
		Inventory closedInv = e.getInventory();
		
		this.shopHandler.setEditMode(player, false);
		
		for(int i = 0; i < closedInv.getSize(); i++) {
			
			ItemStack currentItemStack = closedInv.getItem(i);
			
			if(currentItemStack == null) return;
			if(currentItemStack.getType() == Material.AIR) return;
				
				
			
		}
		
	}
	
}


