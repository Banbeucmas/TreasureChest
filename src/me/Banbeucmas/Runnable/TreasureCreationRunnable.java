package me.Banbeucmas.Runnable;

import me.Banbeucmas.Commands.StartTreasure;
import me.Banbeucmas.Commands.StopTreasure;
import me.Banbeucmas.FileManagement.GeneralData;
import me.Banbeucmas.FileManagement.TreasureData;
import me.Banbeucmas.Utils;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by DELL on 5/19/2016.
 */
public class TreasureCreationRunnable extends BukkitRunnable{

    private GeneralData data = new GeneralData();
    private static int delay;
    private String prefix = data.getPrefix();
    private static boolean treasureState = new TreasureData().getTreasureState();
    //Treasure State: True = Active; False = Wait

    public static void setDelay(int d){
        delay = d;
    }

    public static void setTreasureState(boolean b){
        treasureState = b;
    }

    public static boolean getTreasureState(){
        return treasureState;
    }

    public static int getDelay(){
        return delay;
    }

    public TreasureCreationRunnable(){
        if(treasureState){
            delay = data.getDespawnDelay();
        }
        else{
            delay = data.getSpawnDelay();
        }
    }

    @Override
    public void run(){
        delay--;
        if(treasureState == true){
            treasureCleaning(delay);
        }
        else{
            treasureSpawnWait(delay);
        }
    }


    private void treasureSpawnWait(int delay){
        if(delay == 900 || delay == 300 || delay == 60){
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureSpawnMinuteLeft").replaceAll("%time%", Integer.toString(delay / 60)));
        }
        else if(delay == 10 || delay == 5 || delay == 4 || delay == 3 || delay == 2 || delay == 1){
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureSpawnSecondLeft").replaceAll("%time%", Integer.toString(delay)));
        }
        else if(delay < 0){
            new StartTreasure().defaultState();
        }
    }

    private void treasureCleaning(int delay){
        if(delay < 0){
            new StopTreasure().defaultState();
        }
    }
}
