package org.mobarena.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import org.mobarena.Loader;
import org.mobarena.utils.ShopManager;
import org.mobarena.utils.Utils;

public class ArenaListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBreak(BlockBreakEvent event) {
        if (Loader.manager.checkPlayerInArena(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlace(BlockPlaceEvent event) {
        if (Loader.manager.checkPlayerInArena(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDrop(PlayerDropItemEvent event) {
        if (Loader.manager.checkPlayerInArena(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCraft(CraftItemEvent event) {
        if (Loader.manager.checkPlayerInArena(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventory(InventoryTransactionEvent event) {
        if (Loader.manager.checkPlayerInArena(event.getTransaction().getSource())) {
            if (Loader.manager.getArenaWithPlayer(event.getTransaction().getSource()).status.equals("on")) {
                event.setCancelled();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (Loader.manager.checkPlayerInArena(player)) {
            if (item.getCustomName().equals("§l§eLEAVE")) {
                Loader.manager.getArenaWithPlayer(player).quit(player);
            } else if (item.getCustomName().equals("§l§eSHOP")) {
                ShopManager.addForm(player);
            } else if (item.getCustomName().equals("§l§bClear Effects")) {
                Utils.playSound(player, "random.potion.brewed");
                Utils.removeItem(player, Item.get(Item.NETHER_STAR, 0), 1);
                player.removeAllEffects();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (Loader.manager.checkPlayerInArena(player)) {
            if (item.getCustomName().equals("§l§eLEAVE") && player.isSpectator()) {
                Loader.manager.getArenaWithPlayer(player).quit(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        if (Loader.manager.checkPlayerInArena(event.getPlayer())) {
            event.getPlayer().teleport(Loader.instance.getServer().getDefaultLevel().getSpawnLocation());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        Loader.manager.global_playing.remove(event.getPlayer().getName());
    }
}
