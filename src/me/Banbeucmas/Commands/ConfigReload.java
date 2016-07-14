package me.Banbeucmas.Commands;

import me.Banbeucmas.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by DELL on 5/20/2016.
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
