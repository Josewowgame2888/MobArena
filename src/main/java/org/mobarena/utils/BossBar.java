package org.mobarena.utils;

import cn.nukkit.Player;
import cn.nukkit.utils.DummyBossBar;
import org.mobarena.game.GameArena;

import java.util.HashMap;
import java.util.UUID;

public class BossBar {
    private static HashMap<UUID, Long> bossBars = new HashMap<UUID, Long>();

    public static void sendBossBar(Player player, DummyBossBar bossBar) {
        bossBars.put(player.getUniqueId(), bossBar.getBossBarId());
    }

    public static void removeBossBar(Player player) {
        if (hasBossBar(player)) {
            DummyBossBar bossBar = getPlayerBossBar(player);
            bossBar.destroy();
            bossBars.remove(player.getUniqueId());
        }
    }

    public static boolean hasBossBar (Player player) {
        Long id = bossBars.get(player.getUniqueId());
        return id != null;
    }

    public static DummyBossBar getPlayerBossBar (Player player) {
        Long id = bossBars.get(player.getUniqueId());
        return player.getDummyBossBar(id);
    }

    public static void setText(Player player, String text) {
        getPlayerBossBar(player).setText(text);
    }

    public static String getBossBarTextByArena(GameArena arena) {
        if (arena.getCountPlaying() < 1 || arena.getCountPlaying() == 1 && arena.status.equals("on")) {
            return "§l§eLooking for more players...§r";
        }
        if (arena.getCountPlaying() >= 2 && arena.status.equals("on")) {
            return "§l§aStarting in §r§3" + arena.start_time + " §l§asecond(s)§r";
        }
        if (arena.getCountPlaying() >= 2 && arena.status.equals("off") && arena.start_time > 0) {
            return "§l§aStarting in §r§3" + arena.start_time + " §l§asecond(s)§r";
        }
        if (arena.status.equals("off") && arena.start_time <= 0 && arena.getCountMobs() > 0) {
            return "§l§cROUND #" + arena.round + "§r§e || §3" + arena.getCountMobs() + " §7mobs remaining";
        }
        if (arena.status.equals("off") && arena.start_time <= 0 && arena.getCountMobs() == 0) {
            return "§l§ePreparing round...§r";
        }
        return "";
    }
}
