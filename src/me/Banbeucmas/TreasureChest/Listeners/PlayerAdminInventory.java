package me.Banbeucmas.TreasureChest.Listeners;

import me.Banbeucmas.TreasureChest.Commands.GUICommand;
import me.Banbeucmas.TreasureChest.GUI.AdminGUI;
import me.Banbeucmas.TreasureChest.GUI.ChestListGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Listener for Admin GUI
 */
public class PlayerAdminInventory implements Listener {

    @EventHandler
    public void onAdminGUI(InventoryClickEvent e){
        Player p;
        if(e.getWhoClicked() instanceof Player){
            p = (Player) e.getWhoClicked();
        }
        else{
            return;
        }

        if(!p.hasPermission("TreasureChest.Admin") || !e.getInventory().getTitle().equals(ChatColor.YELLOW + "Treasure Chest Config")){
            return;
        }

        e.setCancelled(true);

        ItemStack clicked = e.getCurrentItem();
        AdminGUI adminGUI = new AdminGUI();
        ChestListGUI chestListGUI = new ChestListGUI();

        if(clicked == null || clicked.getType() == Material.AIR){
            return;
        }

        if(clicked.equals(adminGUI.getChestMenuItem())){
            p.openInventory(chestListGUI.getInventory());
        }

    }

}
