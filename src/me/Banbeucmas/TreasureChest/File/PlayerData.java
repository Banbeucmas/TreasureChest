package me.Banbeucmas.TreasureChest.File;

import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Handles the Player File
 */
public class PlayerData {
    private TreasureChest pl = TreasureChest.getPlugin();
    private File f;
    private FileConfiguration config;

    public PlayerData(){
        f = new File(pl.getDataFolder(), "players.yml");
        if(!f.exists()){
            try{
                f.createNewFile();
            }
            catch (IOException e){
                Bukkit.getLogger().severe("Cannot create players.yml");
            }
        }
        config = YamlConfiguration.loadConfiguration(f);
        createData();
    }

    public FileConfiguration getConfig(){
        return config;
    }

    public void saveConfig(){
        try {
            config.save(f);
        }
        catch (IOException e){
            Bukkit.getLogger().severe("Error saving players.yml");
        }
    }

    private void createData(){
        config.set("A-Example-UUID.Normal Treasure", 0);
        saveConfig();
    }

}
