package me.Banbeucmas.Commands;

import me.Banbeucmas.FileManagement.GeneralData;
import me.Banbeucmas.GUI.AdminGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by DELL on 7/18/2016.
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
