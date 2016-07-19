package me.Banbeucmas.TreasureChest.Commands;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.GUI.AdminGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Commands to open GUIs
 */
public class GUICommand {
    private CommandSender s;
    private String prefix = new GeneralData().getPrefix();

    public GUICommand(CommandSender s) {
        this.s = s;
    }

    public void openAdminGUI(){
        if(s instanceof ConsoleCommandSender){
            s.sendMessage(prefix + ChatColor.RED + "This command only supports Players");
            return;
        }
        Player p = (Player) s;
        p.openInventory(new AdminGUI().getInventory());
    }
}
