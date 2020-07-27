package org.mobarena.task;

import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;
import org.mobarena.Loader;
import org.mobarena.game.GameArena;
import org.mobarena.utils.BossBar;
import org.mobarena.utils.Utils;

public class MatchTask extends Task {
    private final GameArena arena;
    private boolean start = false;
    private int delayed = 10;

    public MatchTask(GameArena arena) {
        this.arena = arena;
    }

    @Override
    public void onRun(int i) {
        if (!start) {
            for (Player player : arena.level.getPlayers().values()) {
                Utils.playSound(player, "mob.blaze.shoot");
                arena.giveBasicKit(player);
                player.sendMessage("§l§a==========================");
                player.sendMessage("§fSurvive as many rounds as possible!");
                player.sendMessage("§fKill the mobs to get gold nuggets and upgrade your equipment.");
                player.sendMessage(" ");
                player.sendMessage("§eMap§7:§d " + arena.id);
                player.sendMessage("§l§a==========================");
            }
            start = true;
        }
        //waiting time at start and change of round
        if (arena.getCountMobs() > 0 && delayed != 10) {
            delayed = 10;
        }
        if (arena.getCountMobs() == 0 && delayed > 0) {
            delayed--;
        }
        if (arena.getCountMobs() == 0 && delayed == 0) {
            //new round
            arena.round = arena.round + 1;
            for (Player player : arena.level.getPlayers().values()) {
                Utils.playSound(player, "raid.horn");
                player.sendMessage("§l§cA new wave of mobs is here!");
            }
            arena.removeMobsStuff();
            arena.spawnEntities();
            delayed = 10;
        }

        //bossbar
        for (Player player : arena.level.getPlayers().values()) {
            if (BossBar.hasBossBar(player)) {
                BossBar.setText(player, BossBar.getBossBarTextByArena(arena));
            }
        }

        //check victories
        if (arena.status.equals("stop")) {
            Loader.instance.getServer().getScheduler().cancelTask(this.getTaskId());
        }
        if (arena.getAlive() == 1) {//a live player
            for (Player player : arena.level.getPlayers().values()) {
                if (player.isSpectator()) {
                    player.sendTitle("§l§3You lost", "§7More luck for the next time");
                } else {
                    player.sendTitle("§l§3Victory", "§7You defeated the monsters");
                }
                arena.quit(player);
            }
            Loader.manager.load(arena.id);
            Loader.instance.getServer().getScheduler().cancelTask(this.getTaskId());
        }
        if (arena.getAlive() == 0) {//no winner
            for (Player player : arena.level.getPlayers().values()) {
                player.sendTitle("§l§3You lost", "§7More luck for the next time");
                arena.quit(player);
            }
            Loader.manager.load(arena.id);
            Loader.instance.getServer().getScheduler().cancelTask(this.getTaskId());
        }
        if (arena.round == 21 && arena.getAlive() > 0) {//more than one winner
            for (Player player : arena.level.getPlayers().values()) {
                if (player.isSpectator()) {
                    player.sendTitle("§l§3You lost", "§7More luck for the next time");
                } else {
                    player.sendTitle("§l§3Victory", "§7You defeated the monsters");
                }
                arena.quit(player);
            }
            Loader.manager.load(arena.id);
            Loader.instance.getServer().getScheduler().cancelTask(this.getTaskId());
        }
    }
}
