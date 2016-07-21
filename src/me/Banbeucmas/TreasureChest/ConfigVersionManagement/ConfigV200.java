package me.Banbeucmas.TreasureChest.ConfigVersionManagement;

import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

/**
 * Updating config into 2.0.0
 */
public class ConfigV200 {
    private TreasureChest pl = TreasureChest.getPlugin();
    private FileConfiguration config = pl.getConfig();

    public ConfigV200(){
        if(!config.isSet("Chest")){
            config.set("Rewards", null);
            pl.saveConfig();

            config.set("Chest.Example.Type", "NORMAL");
            config.set("Chest.Example.Chance", 20);
            config.set("Chest.Example.Rewards.0.Type", "Item");
            config.set("Chest.Example.Rewards.0.Material", "Diamond_Sword");
            config.set("Chest.Example.Rewards.0.Amount", 1);
            config.set("Chest.Example.Rewards.0.Enchantment", Arrays.asList("Damage_All-1"));
            config.set("Chest.Example.Rewards.0.Lore", Arrays.asList("&3This is an example sword!"));
            config.set("Chest.Example.Rewards.0.Name", "&3Example sword");
            config.set("Chest.Example.Rewards.0.Damage", 0);
            config.set("Chest.Example.Rewards.0.Chance", 100);
            config.set("Chest.Example.Rewards.1.Type", "Command");
            config.set("Chest.Example.Rewards.1.Commands", Arrays.asList("give %player% 264 1"));
            config.set("Chest.Example.Rewards.0.Chance", 20);
            pl.saveConfig();
        }

        if (!config.isSet("HiddenChestLimit")){
            config.set("HiddenChestLimit", 0);
            pl.saveConfig();
        }
    }

    public void V201Update(){
        if (!config.isSet("EnableScoreboard")){
            config.set("EnableScoreboard", false);
            config.set("configVersion", "2.0.1");
        }
        pl.saveConfig();
    }

}
