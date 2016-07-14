package me.Banbeucmas;

import me.Banbeucmas.Commands.AdminCommands;
import me.Banbeucmas.Commands.PlayerCommands;
import me.Banbeucmas.ConfigVersionManagement.ConfigV200;
import me.Banbeucmas.File.PlayerData;
import me.Banbeucmas.File.Treasure;
import me.Banbeucmas.FileManagement.GeneralData;
import me.Banbeucmas.Listeners.PlayerRegister;
import me.Banbeucmas.Listeners.PlayerTreasure;
import me.Banbeucmas.Runnable.TreasureCreationRunnable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by DELL on 5/19/2016.
 */
public class TreasureChest extends JavaPlugin{
    private static TreasureChest plugin;
    private static String configVersion = "2.0.0";

    @Override
    public void onEnable(){
        plugin = this;
        fileGeneration();
        configManager();
        registerCommands();
        registerEvents();
        new TreasureCreationRunnable().runTaskTimer(this, 20, 20).getTaskId();
        getLogger().info("TreasureChest has been enabled!");
    }

    public static TreasureChest getPlugin(){
        return plugin;
    }

    private void fileGeneration(){
        saveDefaultConfig();
        new Treasure();
        new PlayerData();
    }

    private void registerCommands(){
        getCommand("Treasureadmin").setExecutor(new AdminCommands());
        getCommand("Treasure").setExecutor(new PlayerCommands());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerTreasure(), this);
        getServer().getPluginManager().registerEvents(new PlayerRegister(), this);
    }

    private void configManager(){
        if(configVersion != new GeneralData().getConfigVersion()){
            new ConfigV200();
        }
    }
}
