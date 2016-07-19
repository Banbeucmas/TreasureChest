package me.Banbeucmas.TreasureChest.FileManagement;

import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles the chest data
 */
public class ChestData {

    public static List<ChestData> getChestList(){
        List<ChestData> l = new ArrayList<>();
        for(String chest : TreasureChest.getPlugin().getConfig().getConfigurationSection("Chest").getKeys(false)){
            ChestData data = new ChestData(chest);
            l.add(data);
        }
        return l;
    }


    public static boolean hasChestType(ChestType type){
        for(ChestData data : getChestList()){
            if(data.getType() == type){
                return true;
            }
        }
        return false;
    }

    private String chest;
    private TreasureChest pl = TreasureChest.getPlugin();
    private FileConfiguration config = pl.getConfig();


    public ChestData(String chest){
        this.chest = chest;
    }

    public void createNewChest(){
        if(!config.isSet("Chest." + chest)){
            config.set("Chest." + chest + ".Type", ChestType.NORMAL.toString());
            config.set("Chest." + chest + ".Chance", 0);
            config.set("Chest." + chest + ".Rewards.0.Type", "Item");
            config.set("Chest." + chest + ".Rewards.0.Material", Material.DIAMOND_SWORD.toString());
            config.set("Chest." + chest + ".Rewards.0.Amount", 1);
            config.set("Chest." + chest + ".Rewards.0.Enchantment", Arrays.asList("Damage_All-1"));
            config.set("Chest." + chest + ".Rewards.0.Lore", Arrays.asList(ChatColor.YELLOW + "This is an example sword"));
            config.set("Chest." + chest + ".Rewards.0.Name", ChatColor.YELLOW + "Example Sword");
            config.set("Chest." + chest + ".Rewards.0.Damage", 0);
            config.set("Chest." + chest + ".Rewards.0.Chance", 100);
            pl.saveConfig();
        }
    }

    public String getChest() {
        return chest;
    }

    public int getChance(){
        if(config.isSet("Chest." + chest + ".Chance")){
            return config.getInt("Chest." + chest + ".Chance");
        }
        return 20;
    }

    public ChestType getType(){
        if(!config.isSet("Chest." + chest + ".Type")){
            return ChestType.NORMAL;
        }
        return ChestType.valueOf(config.getString("Chest." + chest + ".Type").toUpperCase());
    }



}
