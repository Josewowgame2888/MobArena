package org.mobarena.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;
import org.mobarena.Loader;
import org.mobarena.game.GameArena;
import org.mobarena.utils.BossBar;
import org.mobarena.utils.Utils;

public class WaitingLobbyTask extends Task {
    @Override
    public void onRun(int i) {
        if (Loader.manager.arenas.size() > 0) {//basic check
            for (GameArena arena : Loader.manager.arenas.values()) {//all arenas
                if (Loader.manager.isLoaded(arena.id)) {//check if is loaded
                    if (arena.getCountPlaying() >= 2 && arena.status.equals("on") && arena.start_time > 0) {
                        int time = arena.start_time;
                        time--;
                        arena.start_time = time;
                        for (Player player : arena.level.getPlayers().values()) {
                            if (BossBar.hasBossBar(player)) {
                                BossBar.setText(player, BossBar.getBossBarTextByArena(arena));
                                Utils.setFood(player, 20);
                            }
                        }

                        if (arena.getCountMobs() > 0) {
                            arena.removeAllMobs();
                            arena.removeMobsStuff();
                        }

                        if (time == 20 || time == 10 || time <= 5 && time > 0) {
                            for (Player player : arena.level.getPlayers().values()) {
                                Utils.playSound(player, "random.pop");
                            }
                        }
                        if (time == 5) {
                            for (Player player : arena.level.getPlayers().values()) {
                                player.getInventory().clearAll();
                            }
                        }
                        if (time == 0) {
                            arena.status = "off";
                            for (Player player : arena.level.getPlayers().values()) {
                                arena.teleportRandom(player);
                            }
                            arena.removeMobsStuff();//In case there are debris left
                            Loader.instance.getServer().getScheduler().scheduleRepeatingTask(new MatchTask(arena), 20);
                        }
                    } else if (arena.getCountPlaying() < 2 && arena.status.equals("on")) {
                        if (arena.start_time != 30) {
                            arena.start_time = 30;
                            arena.round = 0;
                        }
                        if (arena.getCountPlaying() == 1) {//this repairs some things
                            if (arena.getCountMobs() > 0) {
                                arena.removeAllMobs();
                                arena.removeMobsStuff();
                            }
                            for (Player player : arena.level.getPlayers().values()) {
                                Utils.setFood(player, 20);
                            }
                        }
                    }
                }
            }
        }
    }
}
