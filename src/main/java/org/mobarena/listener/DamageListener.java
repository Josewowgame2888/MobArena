package org.mobarena.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import org.mobarena.Loader;
import org.mobarena.utils.NPC;
import org.mobarena.utils.Utils;

public class DamageListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageInWaitingLobby(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            if (Loader.manager.checkPlayerInArena((Player) entity)) {
                if (Loader.manager.getArenaWithPlayer((Player)entity).status.equals("on")) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.HUNGER) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        event.setCancelled();
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageInGame(EntityDamageEvent event) {
        Entity player = event.getEntity();
        if (player instanceof Player) {
            if (Loader.manager.checkPlayerInArena((Player) player)) {
                if (Loader.manager.getArenaWithPlayer((Player) player).status.equals("off")) {
                    if (event instanceof EntityDamageByEntityEvent) {
                        Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                        if (damager instanceof Player) {
                            event.setCancelled();
                        }
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.HUNGER) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        event.setCancelled();
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                        event.setCancelled();
                    }
                    if (event.getDamage() >= player.getHealth()) {
                        event.setCancelled();

                        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                        event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                            if (event instanceof EntityDamageByEntityEvent) {
                                Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                                for (Player source : Loader.manager.getArenaWithPlayer((Player) player).level.getPlayers().values()) {
                                    source.sendMessage("§m§3" + player.getName() + "§r §7died because of §a" + damager.getName());
                                }
                                ((Player) player).setGamemode(3);
                                player.setHealth(player.getMaxHealth());
                                ((Player) player).getInventory().clearAll();
                                ((Player) player).getInventory().setHeldItemSlot(0);
                                ((Player) player).getInventory().setItem(8, Item.get(Item.TOTEM).setCustomName("§l§eLEAVE"));
                                Utils.setFood((Player) player, 20);
                                player.removeAllEffects();
                                ((Player) player).sendTitle("§l§3You died", "§7More luck next time");
                                Utils.playSound((Player) player, "item.trident.thunder");
                            }
                        } else {
                            for (Player source : Loader.manager.getArenaWithPlayer((Player) player).level.getPlayers().values()) {
                                source.sendMessage("§m§3" + player.getName() + "§r §7died");
                            }
                            ((Player) player).setGamemode(3);
                            player.setHealth(player.getMaxHealth());
                            ((Player) player).getInventory().clearAll();
                            ((Player) player).getInventory().setHeldItemSlot(0);
                            ((Player) player).getInventory().setItem(8, Item.get(Item.TOTEM).setCustomName("§l§eLEAVE"));
                            Utils.setFood((Player) player, 20);
                            player.removeAllEffects();
                            ((Player) player).sendTitle("§l§3You died", "§7More luck next time");
                            Utils.playSound((Player) player, "item.trident.thunder");
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onNpcDamage(EntityDamageEvent event) {
        Entity npc = event.getEntity();
        if (npc instanceof NPC && event instanceof EntityDamageByEntityEvent) {
            event.setCancelled();
            Player player = (Player) ((EntityDamageByEntityEvent) event).getDamager();
            Loader.manager.searchGame(player);
        }
    }
}
