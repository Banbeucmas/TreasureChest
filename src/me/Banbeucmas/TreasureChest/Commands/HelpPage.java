package me.Banbeucmas.TreasureChest.Commands;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Help page
 */
public class HelpPage {
    private CommandSender s;
    private String prefix = new GeneralData().getPrefix();
    public HelpPage(CommandSender s) {
        this.s = s;
        s.sendMessage(ChatColor.YELLOW + "========" + prefix + ChatColor.YELLOW + "========");
    }

    public void showAdminPage(){
        s.sendMessage(ChatColor.AQUA + "/tad help » " + ChatColor.WHITE + "Show help page");
        s.sendMessage(ChatColor.AQUA + "/tad reload » " + ChatColor.WHITE + "Reload config.yml");
        s.sendMessage(ChatColor.AQUA + "/tad start » " + ChatColor.WHITE + "Start Treasure hunting event");
        s.sendMessage(ChatColor.AQUA + "/tad stop » " + ChatColor.WHITE + "Stop Treasure hunting event");
        s.sendMessage(ChatColor.AQUA + "/tad config » " + ChatColor.WHITE + "Open config GUI");
        s.sendMessage(ChatColor.AQUA + "/tad freeze » " + ChatColor.WHITE + "Freeze Treasure timer <Untested, may break the plugin or server>");
    }

    public void showPlayerPage(){
        s.sendMessage(ChatColor.AQUA + "/tre time » " + Utils.getLanguageString("TreasurePlayerHelpTime"));
        s.sendMessage(ChatColor.AQUA + "/tre location » " + Utils.getLanguageString("TreasurePlayerHelpLocation"));
        s.sendMessage(ChatColor.AQUA + "/tre Scoreboard » " + Utils.getLanguageString("TreasurePlayerHelpScoreboard"));
    }
}
