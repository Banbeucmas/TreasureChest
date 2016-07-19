package me.Banbeucmas.TreasureChest.Commands;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Reload the config
 */
public class ConfigReload {
    private CommandSender s;
    private String prefix = new GeneralData().getPrefix();
    public ConfigReload(CommandSender s){
        this.s = s;
    }

    public void reloadConfig(){
        TreasureChest.getPlugin().reloadConfig();
        s.sendMessage(prefix + ChatColor.AQUA + " Config.yml reloaded!");
    }
}
