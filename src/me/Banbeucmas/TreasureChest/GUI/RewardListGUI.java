package me.Banbeucmas.TreasureChest.GUI;

import me.Banbeucmas.TreasureChest.FileManagement.ChestData;
import me.Banbeucmas.TreasureChest.FileManagement.LootData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GUI For reward
 */
public class RewardListGUI {

    private ChestData data;
    private List<LootData> loots = new ArrayList<>();

    public RewardListGUI(ChestData data) {
        this.data = data;
        loots = LootData.getLootData(data.getChest());
    }

    public ItemStack getEditPane(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Edit Items");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Edit the items by dragging",
                ChatColor.GRAY + "the items above into your inventory",
                ChatColor.GRAY + "You will need to config the item chance manually",
                ChatColor.GRAY + "You can see the example of adding chance ",
                ChatColor.GRAY + "on the config.yml of the plugin download page"));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getBack(){
        ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Back");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to go back to the last page"));

        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 63, ChatColor.YELLOW + "Configuring Rewards");
        for(int i = 54; i < 63; i++){
            inv.setItem(i, getEditPane());
        }

        inv.setItem(58, getBack());

        int size = 54;
        int count = 0;
        for(LootData loot : loots){
            if(count >= size){
                break;
            }

            ItemStack item = new ItemStack(Material.NAME_TAG);
            if(loot.isCommand()){
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Command");
                meta.setLore(loot.getCommands());

                item.setItemMeta(meta);
                item = getRewardItem(item, loot);
            }
            else{
                item = getRewardItem(loot.getItemReward(), loot);
            }

            inv.setItem(count, item);
            count++;
        }

        return inv;
    }

    private ItemStack getRewardItem(ItemStack item, LootData loot){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(meta != null && meta.getLore() != null){
            lore = meta.getLore();
        }

        lore.add(ChatColor.GRAY + "Chance: " + loot.getChance());
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

}
