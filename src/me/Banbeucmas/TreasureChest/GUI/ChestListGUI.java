package me.Banbeucmas.TreasureChest.GUI;

import me.Banbeucmas.TreasureChest.FileManagement.ChestData;
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
 * Listing the chest List
 */
public class ChestListGUI {


    public Map<ItemStack, ChestData> getChestItemDisplayMap(){
        Map<ItemStack, ChestData> map = new HashMap<>();

        for(ChestData data : ChestData.getChestList()){
            ItemStack item = new ItemStack(Material.ENDER_CHEST);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + data.getChest());
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Chance: " + ChatColor.WHITE + data.getChance() + "%",
                    ChatColor.GRAY + "Type: " + ChatColor.GRAY + data.getType().toString()));

            item.setItemMeta(meta);

            map.put(item, data);
        }

        return map;
    }
     public ItemStack getCreateNewChestItem(){
         ItemStack item = new ItemStack(Material.ANVIL);
         ItemMeta meta = item.getItemMeta();
         meta.setDisplayName(ChatColor.GREEN + "Create new Chest");
         meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to create new Chest"));

         item.setItemMeta(meta);

         return item;
     }

    public Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Treasure Chest Config");
        int counter = 0;
        int size = 44;

        for(ItemStack item : getChestItemDisplayMap().keySet()){
            if(counter >= size){
                break;
            }
            inv.setItem(counter, item);
            counter++;
        }

        for(int i = 45; i < 54; i++){
            inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE));
        }

        inv.setItem(49, getCreateNewChestItem());

        return inv;
    }

}
