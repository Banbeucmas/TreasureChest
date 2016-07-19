package me.Banbeucmas.TreasureChest.FileManagement;

import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manage the world data
 */
public class WorldManage {
    private TreasureChest pl = TreasureChest.getPlugin();
    private FileConfiguration config = pl.getConfig();
    private World w;
    private String wName;

    public static List<World> getWorlds() {
        FileConfiguration config = TreasureChest.getPlugin().getConfig();
        List<World> l = new ArrayList<>();
        for (String w : config.getConfigurationSection("World").getKeys(false)) {
            if (w.equalsIgnoreCase("Example")) {
                continue;
            }
            l.add(Bukkit.getWorld(w));
        }
        return l;
    }

    public WorldManage(World w) {
        this.w = w;
        wName = w.getName();
    }

    private int getXFrom() {
        return config.getInt("World." + wName + ".XFrom");
    }

    private int getZFrom() {
        return config.getInt("World." + wName + ".ZFrom");
    }

    private int getXTo() {
        return config.getInt("World." + wName + ".XTo");
    }

    private int getZTo() {
        return config.getInt("World." + wName + ".ZTo");
    }

    public Location setupChest(int id) {
        Random r = new Random();
        TreasureData tData = new TreasureData();
        int x = r.nextInt(getXTo() + 1 - getXFrom()) + getXFrom();
        int z = r.nextInt(getZTo() + 1 - getZFrom()) + getZFrom();
        int y = w.getHighestBlockYAt(x, z);

        for (int i = 0; i <= 1; i++) {
            int index = r.nextInt(ChestData.getChestList().size());
            ChestData chestData = ChestData.getChestList().get(index);
            if (chestData.getType() == ChestType.HIDDEN) {
                i--;
                continue;
            }

            float chestChance = (float) chestData.getChance();
            int chance = r.nextInt(100);
            if (chance > chestChance) {
                i--;
                continue;
            }

                int secondChance = r.nextInt(100);
                switch (chestData.getType()){
                    case NORMAL:
                        chestChance = 100;
                        break;
                    case UNIQUE:
                        chestChance /= 3/4;
                        break;
                    case RARE:
                        chestChance /= 1/2;
                        break;
                    case EPIC:
                        chestChance /= 1/5;
                        break;
                    case LEGENDARY:
                        chestChance /= 1/10;
                        break;
                }
                if(secondChance > chestChance){
                    i--;
                    continue;
                }
            tData.setTreasure(w, x, y, z, id, chestData.getChest());
            }

        Location loc = new Location(w, x, y, z);
        loc.getBlock().setType(Material.ENDER_CHEST);
        return loc;
    }

    public Location setupHiddenChest(int id) {
        Random r = new Random();
        TreasureData tData = new TreasureData();
        int x = r.nextInt(getXTo()) + getXFrom();
        int z = r.nextInt(getZTo()) + getZFrom();
        int y = w.getHighestBlockYAt(x, z);

        for (int i = 0; i <= 1; i++) {
            int index = r.nextInt(ChestData.getChestList().size());
            ChestData chestData = ChestData.getChestList().get(index);
            if (chestData.getType() != ChestType.HIDDEN) {
                i--;
                continue;
            }

            int chestChance = chestData.getChance();
            int chance = r.nextInt(100);
            if (chance > chestChance) {
                i--;
                continue;
            }
            tData.setTreasure(w, x, y, z, id, chestData.getChest());
        }

        Location loc = new Location(w, x, y, z);
        loc.getBlock().setType(Material.ENDER_CHEST);
        return loc;
    }
}
