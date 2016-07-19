package me.Banbeucmas.TreasureChest.GUI;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Handle the Config GUI
 */
public class AdminGUI {
    private Inventory inv = Bukkit.createInventory(null, 9, ChatColor.YELLOW + "Treasure Chest Config");

    public ItemStack getChestMenuItem(){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to config the Chest"));
        meta.setDisplayName(ChatColor.YELLOW + "Config Chests");

        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInventory(){
        inv.setItem(0, getChestMenuItem());
        return inv;
    }
}
