package me.Banbeucmas.TreasureChest.GUI;

import me.Banbeucmas.TreasureChest.FileManagement.ChestData;
import me.Banbeucmas.TreasureChest.FileManagement.LootData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * GUI for managing ChestData
 */
public class ChestDataGUI {

    //TODO: Create getBack() ItemStack method

    private ChestData data;

    public ChestDataGUI(ChestData data) {
        this.data = data;
    }

    public ChestData getData() {
        return data;
    }

    public ItemStack rewardListItem(){
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Reward List");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to manage the reward",
                ChatColor.GRAY + "Numbers of rewards: " + ChatColor.WHITE + LootData.getLootData(data.getChest()).size()));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack chanceItem(){
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Chance");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to manage the chance of the chest",
                ChatColor.GRAY + "Chance: " + ChatColor.WHITE + data.getChance()));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack chestTypeItem(){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Chance");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to manage the Type of the chest",
                ChatColor.GRAY + "Type: " + ChatColor.WHITE + data.getType().toString()));

        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "Treasure Chest Config");
        inv.setItem(0, rewardListItem());
        inv.setItem(4, chanceItem());
        inv.setItem(8, chestTypeItem());

        return inv;
    }
}
