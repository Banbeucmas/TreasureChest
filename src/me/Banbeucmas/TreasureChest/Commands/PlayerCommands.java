package me.Banbeucmas.TreasureChest.Commands;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.FileManagement.TreasureData;
import me.Banbeucmas.TreasureChest.Runnable.TreasureCreationRunnable;
import me.Banbeucmas.TreasureChest.Utils;
import me.Banbeucmas.TreasureChest.tools.Scoreboard;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Commands used by players
 */
public class PlayerCommands implements CommandExecutor{
    private String prefix = new GeneralData().getPrefix();
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("TreasureChest.Player")){
            if(args.length == 0){
                new HelpPage(s).showPlayerPage();
            }
            else if(args.length >= 1){
                if(args[0].equalsIgnoreCase("help")){
                    new HelpPage(s).showPlayerPage();
                }
                else if(args[0].equalsIgnoreCase("time")){
                    boolean state = TreasureCreationRunnable.getTreasureState();
                    if(state == false){
                        int delay = TreasureCreationRunnable.getDelay();
                        s.sendMessage(prefix + " " + Utils.getLanguageString("TreasureSpawnTime")
                                .replaceAll("%hour%", Integer.toString(Utils.getHours(delay)))
                                .replaceAll("%minute%", Integer.toString(Utils.getMinutes(delay)))
                                .replaceAll("%second%", Integer.toString(Utils.getSeconds(delay))));
                    }
                    else{
                        s.sendMessage(prefix + Utils.getLanguageString("TreasureAlreadySpawned"));
                    }
                }
                else if(args[0].equalsIgnoreCase("location") || args[0].equalsIgnoreCase("loc")){
                    s.sendMessage(prefix + " " + Utils.getLanguageString("TreasureLocatedCommand"));
                    for(Location loc : new TreasureData().getTreasureLoc().keySet()){
                        s.sendMessage(prefix + ChatColor.YELLOW + " X: " + loc.getBlockX() + ChatColor.YELLOW + " Y: " + loc.getBlockY() + ChatColor.YELLOW + " Z: "+ loc.getBlockZ() + ChatColor.YELLOW + " World: " + loc.getWorld().getName());
                    }
                }
                else if(args[0].equalsIgnoreCase("Scoreboard") || args[0].equalsIgnoreCase("sb")){
                    s.sendMessage(prefix + " " + Utils.getLanguageString("TreasureTopTen"));
                    new Scoreboard().show(s);
                }
            }
        }
        else{
            s.sendMessage(prefix + " " + Utils.getLanguageString("NoPermission"));
        }
        return true;
    }
}
