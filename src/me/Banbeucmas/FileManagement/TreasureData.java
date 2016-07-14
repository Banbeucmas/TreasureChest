package me.Banbeucmas.FileManagement;

import com.mysql.jdbc.Util;
import me.Banbeucmas.File.Treasure;
import me.Banbeucmas.TreasureChest;
import me.Banbeucmas.Utils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Managing Treasure data
 */
public class TreasureData {
    private Treasure t = new Treasure();
    private FileConfiguration config = t.getConfig();

    public Map<Location, String> getTreasureLoc(){
        Map<Location, String> list = new HashMap<>();
        for(String id : config.getConfigurationSection("Treasure").getKeys(false)){
            if(id.equalsIgnoreCase("none")){
                continue;
            }
            String path = "Treasure." + id;
            World w = Bukkit.getWorld(config.getString(path + ".World"));
            int x = config.getInt(path + ".X");
            int y = config.getInt(path + ".Y");
            int z = config.getInt(path + ".Z");
            list.put(new Location(w, x, y, z), id);
        }
        return list;
    }

    public boolean getTreasureState(){
        int amount = getRegularTreasureAmount();
        if(amount > 0){
            return true;
        }
        return false;
    }

    public int getRegularTreasureAmount(){
        int amount = 0;
        for(String id : config.getConfigurationSection("Treasure").getKeys(false)){
            if(id.equals("none")){
                continue;
            }

            ChestData chestData = getChest(id);
            if(chestData.getType() != ChestType.HIDDEN){
                amount++;
            }
        }

        return amount;
    }

    public ChestData getChest(String id){
        return new ChestData(config.getString("Treasure." + id + ".Chest"));
    }

    public ChestData getChest(Location loc){
        return getChest(getTreasureLoc().get(loc));
    }

    public void setTreasure(World w, int x, int y, int z, int id, String chest){
            String path = "Treasure." + id;
            config.set(path + ".World", w.getName());
            config.set(path + ".X", x);
            config.set(path + ".Y", y);
            config.set(path + ".Z", z);
            config.set(path + ".Chest", chest);
            t.saveConfig();
    }

    public void clearTreasure(){
        for(String id : config.getConfigurationSection("Treasure").getKeys(false)){
            if(id.equalsIgnoreCase("none")){
                continue;
            }
                String path = "Treasure." + id;
                World w = Bukkit.getWorld(config.getString(path + ".World"));
                int x = config.getInt(path + ".X");
                int y = config.getInt(path + ".Y");
                int z = config.getInt(path + ".Z");

                Location loc = new Location(w, x, y, z);
                loc.getBlock().setType(Material.AIR);
                config.set(path, null);
                t.saveConfig();
        }
    }

    public void generateTreasure() {
        GeneralData data = new GeneralData();
        String prefix = data.getPrefix();
        int count = 0;
        for (World w : WorldManage.getWorlds()) {
            WorldManage wM = new WorldManage(w);
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureLocated")
                    .replaceAll("%spawnLimit%", Integer.toString(data.getChestLimit()))
                    .replaceAll("%world%", w.getName()));
            for (int i = 0; i < data.getChestLimit(); i++) {
                Location loc = wM.setupChest(i + count);
                System.out.println("Chest limit" + data.getChestLimit());
                System.out.println(i + count);

                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();

                Utils.announce(prefix + ChatColor.YELLOW + " X: " + x + ChatColor.YELLOW + " Y: " + y + ChatColor.YELLOW + " Z:" + z);
            }

            for (int i = 0; i < data.getHiddenChestLimit(); i++) {
                if(!ChestData.hasChestType(ChestType.HIDDEN)){
                    break;
                }
                wM.setupHiddenChest(data.getChestLimit() + i + count);
            }

            count += data.getChestLimit();
        }
    }

    public void deleteTreasure(Location loc){
        String id = getTreasureLoc().get(loc);
        loc.getBlock().setType(Material.AIR);
        config.set("Treasure." + id, null);
        t.saveConfig();
        getTreasureLoc().remove(loc);
    }
}
