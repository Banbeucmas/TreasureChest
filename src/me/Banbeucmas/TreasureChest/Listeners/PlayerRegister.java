package me.Banbeucmas.TreasureChest.Listeners;

import me.Banbeucmas.TreasureChest.FileManagement.PlayerManage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by DELL on 5/22/2016.
 */
public class PlayerRegister implements Listener {

    @EventHandler
    public void registerPlayers(PlayerJoinEvent e){
        Player p = e.getPlayer();
        PlayerManage pManage = new PlayerManage(p);
        if(!pManage.excist()){
            pManage.setPlayerData();
        }
        if(!p.getName().equals(pManage.getPlayerName())){
            PlayerManage.setPlayerName(p);
        }
    }
}
