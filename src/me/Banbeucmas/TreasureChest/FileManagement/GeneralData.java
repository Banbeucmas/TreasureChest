package me.Banbeucmas.TreasureChest.FileManagement;

import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manage others config data
 */
public class GeneralData {
    private TreasureChest pl = TreasureChest.getPlugin();
    private FileConfiguration config = pl.getConfig();

    public int getSpawnDelay(){
        return config.getInt("SpawnDelay");
    }

    public int getDespawnDelay(){
        return config.getInt("DespawnDelay");
    }

    public int getChestLimit(){
        return config.getInt("ChestLimit");
    }

    public int getHiddenChestLimit(){ return config.getInt("HiddenChestLimit");}

    public int getRewardLimit(){
        return config.getInt("RewardLimit");
    }

    public String getPrefix(){
        return ChatColor.translateAlternateColorCodes('&', config.getString("Prefix"));
    }

    public String getConfigVersion(){ return config.getString("configVersion");}

    public Properties getLocale(){
        Properties p = new Properties();
        try{
            InputStream stream = pl.getClass().getResourceAsStream("/Language_" + config.getString("Locale") + ".properties");
            p.load(stream);
        }
        catch (IOException e){
            e.printStackTrace();
            Bukkit.getLogger().severe("Treasure Chest cannot load the language files");
        }
        return p;
    }
}
