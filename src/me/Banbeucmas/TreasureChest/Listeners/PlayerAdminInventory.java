package me.Banbeucmas.TreasureChest.Listeners;

import me.Banbeucmas.TreasureChest.FileManagement.ChestData;
import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.FileManagement.LootData;
import me.Banbeucmas.TreasureChest.GUI.AdminGUI;
import me.Banbeucmas.TreasureChest.GUI.ChestDataGUI;
import me.Banbeucmas.TreasureChest.GUI.ChestListGUI;
import me.Banbeucmas.TreasureChest.GUI.RewardListGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Listener for Admin GUI
 */
public class PlayerAdminInventory implements Listener {
    private Set<Player> chestCreateList = new HashSet<>();
    private Map<Player, RewardListGUI> rewardsManageMap = new HashMap<>();
    private Map<Player, ChestDataGUI> chestDataGUIMap = new HashMap<>();
    private String prefix = new GeneralData().getPrefix();

    @EventHandler
    public void onAdminGUI(InventoryClickEvent e){
        Player p;
        if(e.getWhoClicked() instanceof Player){
            p = (Player) e.getWhoClicked();
        }
        else{
            return;
        }

        if(!p.hasPermission("TreasureChest.Admin")
                || !e.getInventory().getTitle().equals(ChatColor.YELLOW + "Treasure Chest Config")){
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

        Map<ItemStack, ChestData> itemChestMap = chestListGUI.getChestItemDisplayMap();


        if(itemChestMap.containsKey(clicked)){
            ChestDataGUI chestDataGUI = new ChestDataGUI(itemChestMap.get(clicked));
            p.openInventory(chestDataGUI.getInventory());
            chestDataGUIMap.put(p ,chestDataGUI);
            System.out.println(chestDataGUIMap);
        }


        else if(clicked.equals(chestListGUI.getCreateNewChestItem())){
            chestCreateList.add(p);
            p.closeInventory();
            p.sendMessage(prefix + " " + ChatColor.GREEN + "Please type the name of the chest in the chat!");
        }

        if(clicked.equals(chestDataGUIMap.get(p).rewardListItem())){
            RewardListGUI rewardListGUI = new RewardListGUI(chestDataGUIMap.get(p).getData());
            p.openInventory(rewardListGUI.getInventory());
            rewardsManageMap.put(p, rewardListGUI);
        }
    }

    @EventHandler
    private void onLootGUI(InventoryClickEvent e){
        Player p;
        if(e.getWhoClicked() instanceof Player){
            p = (Player) e.getWhoClicked();
        }
        else{
            return;
        }

        if(!p.hasPermission("TreasureChest.Admin")
                || !e.getInventory().getTitle().equals(ChatColor.YELLOW + "Configuring Rewards")
                || !rewardsManageMap.containsKey(p)){
            return;
        }

        ItemStack clicked = e.getCurrentItem();
        RewardListGUI rewardListGUI = rewardsManageMap.get(p);
        if(clicked.equals(rewardListGUI.getBack()) || clicked.equals(rewardListGUI.getEditPane())){
            e.setCancelled(true);
            if(clicked.equals(rewardListGUI.getBack())){
                ChestData chestData = chestDataGUIMap.get(p).getData();

                int count = 0;
                LootData.clearRewards(chestData.getChest());
                for(ItemStack item : e.getView().getTopInventory()){
                    if(item != null && !item.equals(rewardListGUI.getBack()) && !item.equals(rewardListGUI.getEditPane())){
                        LootData lootData = new LootData(chestData.getChest(), Integer.toString(count));
                        lootData.setItem(item);
                        count++;
                    }
                }

                p.sendMessage(prefix + ChatColor.GREEN + " Updated Chest");
                chestDataGUIMap.put(p, new ChestDataGUI(new ChestData(chestData.getChest())));
                p.openInventory(chestDataGUIMap.get(p).getInventory());
            }
        }
    }

    @EventHandler
    public void onCreatingChest(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(chestCreateList.contains(p) && !ChestData.getChestList().contains(new ChestData(e.getMessage()))){
            e.setCancelled(true);
            new ChestData(e.getMessage()).createNewChest();
            chestCreateList.remove(p);

            p.sendMessage(prefix + ChatColor.GREEN + " Successfully created Chest: " + ChatColor.AQUA + e.getMessage());
            p.sendMessage(prefix + ChatColor.GREEN + " Please type " + ChatColor.YELLOW + "/tad config " + ChatColor.GREEN + "to config the chest");
            p.sendMessage(prefix + ChatColor.GREEN + " Remember to set the chest chance into 100 (or whatever you want) when you finished your first config it");
        }
        else if(ChestData.getChestList().contains(new ChestData(e.getMessage()))){
            e.setCancelled(true);
            p.sendMessage(prefix + ChatColor.RED + " This chest has already excisted");

            chestCreateList.remove(p);
        }
    }
}
