package me.Banbeucmas.TreasureChest.FileManagement;

import me.Banbeucmas.TreasureChest.TreasureChest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Manage the loot
 */
public class LootData {


    public static List<LootData> getLootData(String chest){
        List<LootData> l = new ArrayList<>();
        if(!TreasureChest.getPlugin().getConfig().isSet("Chest." + chest + ".Rewards")){
            return l;
        }
       for(String id : TreasureChest.getPlugin().getConfig().getConfigurationSection("Chest." + chest + ".Rewards").getKeys(false)){
            l.add(new LootData(chest, id));
        }
        return l;
    }

    public static void clearRewards(String chest){
            TreasureChest.getPlugin().getConfig().set("Chest." + chest + ".Rewards", null);
            TreasureChest.getPlugin().saveConfig();
    }

    private String id;
    private String chest;
    private String path;
    private TreasureChest pl = TreasureChest.getPlugin();
    private FileConfiguration config = pl.getConfig();

    public LootData(String chest, String id){
        this.id = id;
        this.chest = chest;
        path = "Chest." + chest + ".Rewards." + id;
    }

    public void setItem(ItemStack item){
        if(!config.isSet(path)){
            ItemMeta meta = item.getItemMeta();
            if(item.hasItemMeta() || meta != null){

                if(meta.getDisplayName() != null){

                    if(meta.getDisplayName().equals(ChatColor.AQUA + "Command") || meta.getDisplayName().equals("Command")) {
                        config.set(path + ".Type", "Command");
                        List<String> cmdList = setChanceMeta(meta);
                        config.set(path + ".Commands", cmdList);

                        pl.saveConfig();
                        return;
                    }
                    else{
                        config.set(path + ".Name", meta.getDisplayName());
                    }
                }
                    config.set(path + ".Type", "Item");
                    config.set(path + ".Material", item.getType().toString());
                    config.set(path + ".Amount", item.getAmount());
                    if(item.getEnchantments() != null || !item.getEnchantments().isEmpty()){
                        Map<Enchantment, Integer> map = item.getEnchantments();
                        List<String> enchStringList = new ArrayList<>();
                        for(Enchantment ench : map.keySet()){
                            enchStringList.add(ench.getName()+ "-" +map.get(ench));
                        }

                        config.set(path + ".Enchantment", enchStringList);
                    }

                    List<String> l = setChanceMeta(meta);
                    config.set(path + ".Lore", l);
                    config.set(path + ".Damage", item.getDurability());
            }
        }

        pl.saveConfig();
    }

    private List<String> setChanceMeta(ItemMeta meta){
        List<String> l = new ArrayList<>();
        if(meta.getLore() != null || meta.hasLore()){
            l = meta.getLore();
            String chance = l.get(meta.getLore().size() - 1);
            if(chance.contains(ChatColor.GRAY + "Chance:")){
                String[] sChance = chance.split(": ");
                int c = Integer.parseInt(sChance[1]);
                config.set(path + ".Chance", c);
                pl.saveConfig();

                l.remove(meta.getLore().size() - 1);
            }
        }
        return l;
    }

    public boolean isCommand(){
        if(config.isSet(path + ".Type")){
            String type = config.getString(path + ".Type");
            if(type.equalsIgnoreCase("Command")){
                return true;
            }
            return false;
        }
        return false;
    }

    private Material getMaterial(){
        return Material.matchMaterial(config.getString(path + ".Material"));
    }

    private int getAmount(){
        if(!config.isSet(path + ".Amount")){
            return 1;
        }
        return config.getInt(path + ".Amount");
    }

    private Map<Enchantment, Integer> getEnchantmentLevelMap(){
        Map<Enchantment, Integer> m = new HashMap<>();
        if(config.isSet(path + ".Enchantment")){
            for(String enchant : config.getStringList(path + ".Enchantment")){
                String[] enchantArray = enchant.split("-");
                Enchantment ench = Enchantment.getByName(enchantArray[0].toUpperCase());
                int level = Integer.parseInt(enchantArray[1]);

                m.put(ench, level);
            }
        }
        return m;
    }
    private List<String> getLore(){
        List<String> lore = new ArrayList<>();
        if(config.isSet(path + ".Lore")){
            for(String l : config.getStringList(path + ".Lore")){
                lore.add(ChatColor.translateAlternateColorCodes('&', l));
            }
        }
        return lore;
    }

    private String getName(){
        if(config.isSet(path + ".Name")){
            return ChatColor.translateAlternateColorCodes('&',  config.getString(path + ".Name"));
        }
        return null;
    }

    private int getDamage(){
        if(!config.isSet(path + ".Damage")){
            return 0;
        }
        return config.getInt(path + ".Damage");
    }

    public int getChance(){
        if(config.isSet(path + ".Chance")){
            int chance = config.getInt(path + ".Chance");
            if(chance > 100){
                return 100;
            }
            else{
                return chance;
            }
        }
        return 20;
    }

    public ItemStack getItemReward(){
        ItemStack item = new ItemStack(getMaterial(), getAmount(), (short) getDamage());
        if(getEnchantmentLevelMap() != null || getEnchantmentLevelMap().size() > 0){
            item.addEnchantments(getEnchantmentLevelMap());
        }
        ItemMeta meta = item.getItemMeta();
        if(getLore() != null || getLore().size() > 0){
            meta.setLore(getLore());
        }
        if(getName() != null){
            meta.setDisplayName(getName());
        }
        item.setItemMeta(meta);
        return item;
    }

    public List<String> getCommands(){
        return config.getStringList(path + ".Commands");
    }

}
