package de.tycoon.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tycoon.TycoonPlugin;
import de.tycoon.shop.ShopHandler;

public class MenuListener implements Listener{

	private TycoonPlugin plugin;
	private ShopHandler shopHandler;
	
	private Map<UUID, Integer> currentShopSite;
	
	private Inventory[] shopInventories;
	
	public MenuListener() {
		this.plugin = TycoonPlugin.get();
		this.shopHandler = this.plugin.getShopHandler();
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


