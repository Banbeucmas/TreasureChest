package me.Banbeucmas.TreasureChest;

import me.Banbeucmas.TreasureChest.Commands.AdminCommands;
import me.Banbeucmas.TreasureChest.Commands.PlayerCommands;
import me.Banbeucmas.TreasureChest.ConfigVersionManagement.ConfigV200;
import me.Banbeucmas.TreasureChest.File.PlayerData;
import me.Banbeucmas.TreasureChest.File.Treasure;
import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.Listeners.PlayerAdminInventory;
import me.Banbeucmas.TreasureChest.Listeners.PlayerRegister;
import me.Banbeucmas.TreasureChest.Listeners.PlayerTreasure;
import me.Banbeucmas.TreasureChest.Runnable.TreasureCreationRunnable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of he plugin
 */
public class TreasureChest extends JavaPlugin{
    private static TreasureChest plugin;
    private static String configVersion = "2.0.1";

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
        getServer().getPluginManager().registerEvents(new PlayerAdminInventory(), this);
    }

    private void configManager(){
        if(configVersion != new GeneralData().getConfigVersion()){
            new ConfigV200().V201Update();
        }
    }
}
