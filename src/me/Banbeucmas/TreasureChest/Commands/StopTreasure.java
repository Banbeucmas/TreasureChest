package me.Banbeucmas.TreasureChest.Commands;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.FileManagement.TreasureData;
import me.Banbeucmas.TreasureChest.Runnable.TreasureCreationRunnable;
import me.Banbeucmas.TreasureChest.Utils;
import org.bukkit.command.CommandSender;

/**
 * Function to stop treasure
 */
public class StopTreasure {
    private GeneralData data = new GeneralData();
    private TreasureData treasure = new TreasureData();
    private String prefix = data.getPrefix();

    public void commandSenderState(CommandSender s){
        if(TreasureCreationRunnable.getTreasureState() == false){
            s.sendMessage(prefix + " " + Utils.getLanguageString("NoTreasureRunning"));
        }
        else{
            treasure.clearTreasure();
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureCleared"));
            TreasureCreationRunnable.setTreasureState(false);
            TreasureCreationRunnable.setDelay(data.getSpawnDelay());
            s.sendMessage(prefix + " " + Utils.getLanguageString("TreasureClearedAdminAnnounce"));
        }
    }

    public void defaultState(){
            treasure.clearTreasure();
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureCleared"));
            TreasureCreationRunnable.setTreasureState(false);
            TreasureCreationRunnable.setDelay(data.getSpawnDelay());
    }

    public void endState(){
        treasure.clearTreasure();
        Utils.announce(prefix + " " + Utils.getLanguageString("EventEnded"));
        TreasureCreationRunnable.setTreasureState(false);
        TreasureCreationRunnable.setDelay(data.getSpawnDelay());
    }
}
