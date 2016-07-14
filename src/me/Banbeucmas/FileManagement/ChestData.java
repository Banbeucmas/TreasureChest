package me.Banbeucmas.FileManagement;

import me.Banbeucmas.TreasureChest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
