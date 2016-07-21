package me.Banbeucmas.TreasureChest.GUI;

import me.Banbeucmas.TreasureChest.FileManagement.ChestData;
import me.Banbeucmas.TreasureChest.FileManagement.ChestType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * GUI For selecting the chest type
 */
public class ChestTypeGUI {
    private ChestData data;

    public ChestTypeGUI(ChestData data) {
        this.data = data;
    }

    public ChestData getData() {
        return data;
    }

    public Map<ItemStack, ChestType> getChestTypeItems(){
        Map<ItemStack, ChestType> map = new HashMap<>();
        for(ChestType type : ChestType.values()){
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.AQUA + StringUtils.capitalize(type.toString().toLowerCase()));
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to change the type"));

            item.setItemMeta(meta);
            map.put(item, type);
        }
        return map;
    }

    public Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 18, "Change Chest Type");
        int counter = 0;
        for(ItemStack item : getChestTypeItems().keySet()){
            inv.setItem(counter, item);
            counter++;
        }
        return inv;
    }
}
