package me.Banbeucmas.TreasureChest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * AdminCommands
 */
public class AdminCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String labels, String[] args) {
        if(s.hasPermission("TreasureChest.Admin")) {
            if (args.length == 0) {
                new HelpPage(s).showAdminPage();
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    new StartTreasure().commandSenderState(s);
                } else if (args[0].equalsIgnoreCase("stop")) {
                    new StopTreasure().commandSenderState(s);
                } else if (args[0].equalsIgnoreCase("freeze")) {
                    new FreezeTreasure(s);
                } else if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
                    new ConfigReload(s).reloadConfig();
                } else if (args[0].equalsIgnoreCase("help")) {
                    new HelpPage(s).showAdminPage();
                } else if (args[0].equalsIgnoreCase("config")){
                    new GUICommand(s).openAdminGUI();
                }
            }
        }
        return true;
    }
}
