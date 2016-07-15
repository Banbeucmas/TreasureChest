package me.Banbeucmas.Commands;

import me.Banbeucmas.Runnable.TreasureCreationRunnable;
import me.Banbeucmas.TreasureChest;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Untested freezing treasure
 */
public class FreezeTreasure {
    private static boolean freeze = false;
    private int id;
    public FreezeTreasure(CommandSender s){
           new BukkitRunnable() {
                @Override
                public void run() {
                    if(freeze == true){
                        TreasureCreationRunnable.setDelay(TreasureCreationRunnable.getDelay() + 1);
                    }
                    else{
                        cancel();
                    }
                }
           }.runTaskTimer(TreasureChest.getPlugin(), 20, 20).getTaskId();
     }
}
