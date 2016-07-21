package me.Banbeucmas.TreasureChest.Listeners;

import me.Banbeucmas.TreasureChest.FileManagement.ChestData;
import me.Banbeucmas.TreasureChest.FileManagement.ChestType;
import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.FileManagement.LootData;
import me.Banbeucmas.TreasureChest.GUI.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Listener for Admin GUI
 */
public class PlayerAdminInventory implements Listener {
    private Set<Player> chestCreateList = new HashSet<>();
    private Map<Player, ChestTypeGUI> chestTypeGUIMap = new HashMap<>();
    private Map<Player, ChestDataGUI> chestChanceMap = new HashMap<>();
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
            return;
        }

        Map<ItemStack, ChestData> itemChestMap = chestListGUI.getChestItemDisplayMap();


        if(itemChestMap.containsKey(clicked)){
            ChestDataGUI chestDataGUI = new ChestDataGUI(itemChestMap.get(clicked));
            p.openInventory(chestDataGUI.getInventory());
            chestDataGUIMap.put(p ,chestDataGUI);
        }


        else if(clicked.equals(chestListGUI.getCreateNewChestItem())){
            chestCreateList.add(p);
            p.closeInventory();
            p.sendMessage(prefix + " " + ChatColor.GREEN + "Please type the name of the chest in the chat!");
            p.sendMessage(prefix + ChatColor.GREEN + " Type" + ChatColor.RED + " Cancel to cancel the event");
        }

        else if(clicked.equals(chestDataGUIMap.get(p).rewardListItem())){
            RewardListGUI rewardListGUI = new RewardListGUI(chestDataGUIMap.get(p).getData());
            p.openInventory(rewardListGUI.getInventory());
            rewardsManageMap.put(p, rewardListGUI);
        }

        else if(clicked.equals(chestDataGUIMap.get(p).chanceItem())){
            chestChanceMap.put(p, chestDataGUIMap.get(p));
            p.closeInventory();
            p.sendMessage(prefix + ChatColor.GREEN + " Please enter the chance of getting the item in the chat");
            p.sendMessage(prefix + ChatColor.GREEN + " Type" + ChatColor.RED + " Cancel" + ChatColor.GREEN + " to cancel the event");
        }

        else if(clicked.equals(chestDataGUIMap.get(p).chestTypeItem())){
            ChestTypeGUI chestTypeGUI = new ChestTypeGUI(chestDataGUIMap.get(p).getData());
            chestDataGUIMap.put(p, chestDataGUIMap.get(p));
            chestTypeGUIMap.put(p, chestTypeGUI);
            p.openInventory(chestTypeGUI.getInventory());
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

        if(clicked == null){
            return;
        }

        RewardListGUI rewardListGUI = rewardsManageMap.get(p);
        if(clicked.equals(rewardListGUI.getBack()) || clicked.equals(rewardListGUI.getEditPane())){
            e.setCancelled(true);
            if(clicked.equals(rewardListGUI.getBack())){
                ChestData chestData = chestDataGUIMap.get(p).getData();
                chestDataGUIMap.put(p, new ChestDataGUI(new ChestData(chestData.getChest())));
                p.openInventory(chestDataGUIMap.get(p).getInventory());
            }
        }
    }

    @EventHandler
    public void onChestTypeInventory(InventoryClickEvent e){
        Player p;
        if(e.getWhoClicked() instanceof Player){
            p = (Player) e.getWhoClicked();
        }
        else{
            return;
        }

        if(!p.hasPermission("TreasureChest.Admin")
                || !e.getInventory().getTitle().equals("Change Chest Type")
                || !chestTypeGUIMap.containsKey(p)){
            return;
        }

        ItemStack clicked = e.getCurrentItem();

        if(clicked == null){
            return;
        }

        ChestTypeGUI chestTypeGUI = chestTypeGUIMap.get(p);
        if(chestTypeGUI.getChestTypeItems().containsKey(clicked)){
            e.setCancelled(true);
            ChestData data = chestTypeGUI.getData();
            data.setType(chestTypeGUI.getChestTypeItems().get(clicked));
            p.openInventory(chestDataGUIMap.get(p).getInventory());

            p.sendMessage(prefix + ChatColor.GREEN + " Set the type to: " + ChatColor.YELLOW + chestTypeGUI.getChestTypeItems().get(clicked).toString());
            chestTypeGUIMap.remove(p);
        }
    }

    @EventHandler
    public void onClosingInventory(InventoryCloseEvent e){
        Player p;
        if(e.getPlayer() instanceof Player){
            p = (Player) e.getPlayer();
        }
        else{
            return;
        }

        if(e.getInventory().getTitle().equals(ChatColor.YELLOW + "Configuring Rewards")) {
            ChestData chestData = chestDataGUIMap.get(p).getData();
            RewardListGUI rewardListGUI = rewardsManageMap.get(p);

            int count = 0;
            LootData.clearRewards(chestData.getChest());
            for (ItemStack item : e.getView().getTopInventory()) {
                if (item != null && !item.equals(rewardListGUI.getBack()) && !item.equals(rewardListGUI.getEditPane())) {
                    LootData lootData = new LootData(chestData.getChest(), Integer.toString(count));
                    lootData.setItem(item);
                    count++;
                }
            }

            rewardsManageMap.remove(p);
            p.sendMessage(prefix + ChatColor.GREEN + " Updated Chest");
        }
    }

    @EventHandler
    public void onConfiguringChest(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(chestCreateList.contains(p) && !ChestData.getChestList().contains(new ChestData(e.getMessage()))) {
            e.setCancelled(true);
            if (e.getMessage().equals("Cancel")) {
                chestCreateList.remove(p);
                p.sendMessage(prefix + ChatColor.YELLOW + " Cancelled creating the chest");
                return;
            }

            new ChestData(e.getMessage()).createNewChest();
            chestCreateList.remove(p);

            p.sendMessage(prefix + ChatColor.GREEN + " Successfully created Chest: " + ChatColor.AQUA + e.getMessage());
            p.sendMessage(prefix + ChatColor.GREEN + " Please type " + ChatColor.YELLOW + "/tad config " + ChatColor.GREEN + "to config the chest");
            p.sendMessage(prefix + ChatColor.GREEN + " Remember to set the chest chance into 100 (or whatever you want) when you finished your first config it");
            return;
        }
        else if(ChestData.getChestList().contains(new ChestData(e.getMessage()))){
            e.setCancelled(true);
            p.sendMessage(prefix + ChatColor.RED + " This chest has already excisted");

            chestCreateList.remove(p);
            return;
        }

        if(chestChanceMap.containsKey(p)){
            e.setCancelled(true);
            if(e.getMessage().equals("Cancel")){
                chestChanceMap.remove(p);
                p.sendMessage(prefix + ChatColor.YELLOW + " Cancelled changing the chance");
                return;
            }

            int chance = 0;
            try{
                chance = Integer.parseInt(e.getMessage());
            }
            catch (NumberFormatException ex){
                p.sendMessage(prefix + ChatColor.RED + " That's not a number");
            }

            if(chance > 100){
                chance = 100;
            }
            else if(chance < 0){
                chance = 0;
            }

            chestChanceMap.get(p).getData().setChance(chance);
            p.openInventory(chestChanceMap.get(p).getInventory());
            chestChanceMap.remove(p);

            p.sendMessage(prefix + ChatColor.GREEN + " Set the chance into: " + ChatColor.YELLOW + Integer.toString(chance));
            return;
        }
    }
}
