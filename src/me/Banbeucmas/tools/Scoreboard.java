package me.Banbeucmas.tools;

import me.Banbeucmas.FileManagement.PlayerManage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * To-do: adding Scoreboard for top players.
 */
public class Scoreboard {
    private List<PlayerManage> pMList = PlayerManage.getPlayerManageList();

    public Scoreboard(){
        for(int i = 0; i < pMList.size() - 1; i++){
            for(int j = i; j < pMList.size(); j++){
                PlayerManage pMF = pMList.get(i);
                PlayerManage pMS = pMList.get(j);
                if(pMF.getTotalPoint() <= pMS.getTotalPoint()){
                    PlayerManage c = pMF;
                    pMList.set(i, pMS);
                    pMList.set(j, c);
                }
            }
        }
    }

    public void show(CommandSender s){
        int count = 0;
        for(PlayerManage pM : pMList){
            if(count >= 10){
                break;
            }
            count++;
            s.sendMessage(ChatColor.YELLOW + Integer.toString(count) + ". " + pM.getPlayerName() + ": " + ChatColor.WHITE + Integer.toString(pM.getTotalPoint()));
        }
    }

    public List<PlayerManage> getTopList(){
        return pMList;
    }
}
