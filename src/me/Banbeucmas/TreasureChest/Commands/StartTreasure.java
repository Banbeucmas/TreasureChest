package me.Banbeucmas.TreasureChest.Commands;

import me.Banbeucmas.TreasureChest.FileManagement.GeneralData;
import me.Banbeucmas.TreasureChest.FileManagement.TreasureData;
import me.Banbeucmas.TreasureChest.Runnable.TreasureCreationRunnable;
import me.Banbeucmas.TreasureChest.Utils;
import org.bukkit.command.CommandSender;

/**
 * Funtion to start treasure
 */
public class StartTreasure {
    private GeneralData data = new GeneralData();
    private TreasureData treasure = new TreasureData();
    private String prefix = data.getPrefix();

    public void commandSenderState(CommandSender s){
        if(TreasureCreationRunnable.getTreasureState() == true){
            s.sendMessage(prefix + " " + Utils.getLanguageString("TreasureAlreadySpawned"));
        }
        else{
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureSpawned"));
            treasure.generateTreasure();
            TreasureCreationRunnable.setDelay(data.getDespawnDelay());
            TreasureCreationRunnable.setTreasureState(true);

            s.sendMessage(prefix + " " + Utils.getLanguageString("TreasureSpawnedAdminAnnounce"));
        }
    }

    public void defaultState(){
        Utils.announce(prefix + " " + Utils.getLanguageString("TreasureSpawned"));
        treasure.generateTreasure();
        TreasureCreationRunnable.setDelay(data.getDespawnDelay());
        TreasureCreationRunnable.setTreasureState(true);
    }
}
