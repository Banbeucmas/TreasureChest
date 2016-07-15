package me.Banbeucmas.File;

import me.Banbeucmas.TreasureChest;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Handles the Treasure File
 */
public class Treasure {
    private TreasureChest pl = TreasureChest.getPlugin();
    private File f;
    private FileConfiguration config;

    public Treasure(){
        f = new File(pl.getDataFolder(), "Treasure.yml");
        if(!f.exists()){
            try{
                f.createNewFile();
            }
            catch (IOException e){
                Bukkit.getLogger().severe("Cannot create Treasure.yml");
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
            Bukkit.getLogger().severe("Error saving Treasure.yml");
        }
    }

    public void reloadConfig(){
            config = YamlConfiguration.loadConfiguration(f);
    }

    private void createData(){
        config.set("Treasure.none.World", "example");
        config.set("Treasure.none.X", 0);
        config.set("Treasure.none.Y", 0);
        config.set("Treasure.none.Z", 0);
        saveConfig();
    }
}
