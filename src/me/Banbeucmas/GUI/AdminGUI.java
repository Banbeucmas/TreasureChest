package me.Banbeucmas.GUI;

import me.Banbeucmas.FileManagement.GeneralData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

/**
 * Created by DELL on 7/18/2016.
 */
public class AdminGUI {
    private Inventory inv;
    private GeneralData data;

    public AdminGUI() {
        inv = Bukkit.createInventory(null, 9, ChatColor.YELLOW + "Treasure Chest Config");
        data = new GeneralData();
    }
}
