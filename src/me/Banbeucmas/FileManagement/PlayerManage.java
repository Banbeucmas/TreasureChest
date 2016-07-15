package me.Banbeucmas.FileManagement;

import me.Banbeucmas.File.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manage Player Data
 */
public class PlayerManage {
    private PlayerData pData = new PlayerData();
    private FileConfiguration config = pData.getConfig();
    private Player p;
    private String uuid;

    public static List<PlayerManage> getPlayerManageList(){
        List<PlayerManage> l = new ArrayList<>();
        FileConfiguration con = new PlayerData().getConfig();
        for(String uuid : con.getConfigurationSection("").getKeys(false)){
            if(uuid.equals("A-Example-UUID")){
                continue;
            }
            l.add(new PlayerManage(uuid));
        }
        return l;
    }

    public PlayerManage(Player p){
        this.p = p;
        uuid = p.getUniqueId().toString();
    }

    public PlayerManage(String uuid){
        this.uuid = uuid;
        this.p = Bukkit.getPlayer(UUID.fromString(uuid));
    }

    public int getPoint(ChestType type){
        return config.getInt(uuid + ".Point." + type.toString());
    }

    public int getTotalPoint(){
        if(!config.isSet(uuid + ".Point")){
            return 0;
        }
        int total = 0;
        for(String type : config.getConfigurationSection(uuid + ".Point").getKeys(false)){
            total += config.getInt(uuid + ".Point." + type);
        }
        return total;
    }

    public void addPoint(int point, ChestType type){
        config.set(uuid + ".Point." + type.toString(), getPoint(type) + point);
        pData.saveConfig();
    }

    public void setPlayerData(){
        config.set(uuid + ".Player Name", p.getName());
        pData.saveConfig();
    }

    public boolean excist(){
        return config.isSet(uuid);
    }

    public String getPlayerName(){
        return config.getString(uuid + ".Player Name");
    }

    public static void setPlayerName(Player p){
        PlayerData pData = new PlayerData();
        FileConfiguration config = pData.getConfig();
        config.set(p.getUniqueId().toString() + ".Player Name", p.getName());
        pData.saveConfig();
    }

}
