package me.Banbeucmas.Listeners;

import me.Banbeucmas.Commands.StopTreasure;
import me.Banbeucmas.FileManagement.*;
import me.Banbeucmas.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.*;

/**
 * Handles the glory moment when player open the Treasure
 */
public class PlayerTreasure implements Listener {
    private GeneralData data = new GeneralData();
    private String prefix = data.getPrefix();
    private Map<Player, List<ChestData>> playerTreasure = new HashMap<>();

    @EventHandler
    public void onPlayerOpenTreasure(PlayerInteractEvent e){
        Block b = e.getClickedBlock();
        Player p = e.getPlayer();
        if(b == null){
            return;
        }
        if(b.getType() == Material.ENDER_CHEST && checkTreasureLocation(b.getLocation())){
            e.setCancelled(true);

            ChestData chestData = new TreasureData().getChest(b.getLocation());

            if(playerTreasure.keySet().contains(p)){
                List<ChestData> l = playerTreasure.get(p);
                l.add(chestData);
                playerTreasure.put(p, l);
            }
            else{
                List<ChestData> l = new ArrayList<>();
                l.add(chestData);
                playerTreasure.put(p, l);
            }

            new TreasureData().deleteTreasure(b.getLocation());
            Utils.announce(prefix + " " + Utils.getLanguageString("TreasureFound")
                    .replaceAll("%player%", p.getName())
                    .replaceAll("%treasure%", StringUtils.capitalize(chestData.getType().toString().toLowerCase())));
            spawnFirework(b.getLocation());
            new PlayerManage(p).addPoint(chestData.getType().getPoint() ,chestData.getType());
            giveRewards(p, chestData);
            if(new TreasureData().getRegularTreasureAmount() <= 0){
                new StopTreasure().endState();
            }
        }
    }


    private boolean checkTreasureLocation(Location loc){
        for(Location l : new TreasureData().getTreasureLoc().keySet()){
            if(loc.getBlockX() == l.getBlockX()
                    && loc.getBlockY() == l.getBlockY()
                    && loc.getBlockZ() == l.getBlockZ()
                    && loc.getWorld() == l.getWorld()){
                return true;
            }
        }
        return false;
    }


    private void giveRewards(Player p, ChestData chestData){
        Inventory inv = Bukkit.createInventory(null, 27, Utils.getLanguageString("RewardInventory"));

            List<LootData> loots = LootData.getLootData(chestData.getChest());
        int slot = 0;
        for(int i = 0; i < data.getRewardLimit(); i++){
            Random r = new Random();
            int chooser = r.nextInt(loots.size());
            LootData l = loots.get(chooser);

            int chance = r.nextInt(100);
            if(chance > l.getChance()){
                i--;
                continue;
            }
            else{
                if(l.isCommand()){
                    for(String cmd : l.getCommands()){
                        String newCmd = cmd.replaceAll("%player%", p.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), newCmd);
                    }
                }
                else{
                    inv.setItem(slot, l.getItemReward());
                    slot++;
                }
            }
        }
        p.openInventory(inv);
        List<ChestData> l = playerTreasure.get(p);
        l.remove(chestData);
        playerTreasure.put(p, l);
    }

    private void spawnFirework(Location loc){
        Firework f = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = f.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(FireworkEffect.Type.BURST)
                .withColor(Color.AQUA)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .build());
        meta.setPower(2);
        f.setFireworkMeta(meta);
    }
}
