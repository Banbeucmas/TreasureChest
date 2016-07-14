package me.Banbeucmas;

import me.Banbeucmas.FileManagement.ChestData;
import me.Banbeucmas.FileManagement.GeneralData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by DELL on 5/19/2016.
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
