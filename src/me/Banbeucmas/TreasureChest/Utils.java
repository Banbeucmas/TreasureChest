package me.Banbeucmas.TreasureChest;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
/**
 * Utils for the plugin
 */
public class Utils {
    public static int getSeconds(int time){
        return time % 60;
    }

    public static int getMinutes(int time){
        return ((time - getSeconds(time)) / 60) % 60;
    }

    public static int getHours(int time){
        return ((time - getSeconds(time))/60 - getMinutes(time)) / 60;
    }

    public static void announce(String msg){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(msg);
        }
    }

    public static String getLanguageString(String key){
        return ChatColor.translateAlternateColorCodes('&', new GeneralData().getLocale().getProperty(key));
    }


}
