package org.mobarena.game;

import cn.nukkit.Player;
import org.mobarena.Loader;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameManager {
    public HashMap<String, GameArena> arenas = new HashMap<>();

    /*
     * It will allow us to get the players who are really playing
     * Possibly in the future it will serve to store important data
     */
    public HashMap<String, Integer> global_playing = new HashMap<>();

    public void loadAll() {
        String[] games;
        for (String game : Objects.requireNonNull(games = new File(Loader.instance.getDataFolder() + "/").list())) {
            load(game.replace(".dat", ""));
        }
        Loader.instance.getLogger().info("§9Loaded (" + games.length + ") arena(s)");
    }

    public void load(String name) {
        if (exists(name)) {
            this.unLoad(name);
            arenas.put(name, new GameArena(name));
        }
    }

    public void unLoad(String name) {
        if (isLoaded(name)) {
            arenas.get(name).status = "stop";
            for (Player player : arenas.get(name).level.getPlayers().values()) {
                arenas.get(name).quit(player);
            }
            if (Loader.instance.getServer().isLevelLoaded(arenas.get(name).config.getString("level"))) {
                Loader.instance.getServer().unloadLevel(arenas.get(name).level);
            }
            arenas.remove(name);
        }
    }

    public boolean exists(String name) {
        return new File(Loader.instance.getDataFolder() + "/" + name + ".dat").exists();
    }

    public boolean isLoaded(String name) {
        return arenas.containsKey(name);
    }

    public void searchGame(Player player) {
        if (arenas.size() == 0) {
            player.sendMessage("§cAn attempt to connect to a game failed. There is no configured arena");
        } else {
            HashMap<String, Integer> available = new HashMap<>();
            for (GameArena arena : arenas.values()) {
                if (arena.status.equals("on") && arena.getCountPlaying() < 8) {
                    available.put(arena.id, arena.getCountPlaying());
                }
            }
            if (available.size() == 0) {
                player.sendMessage("§cNo available game found. Try again later!");
            } else {
                Object maxEntry = Collections.max(available.entrySet(), Map.Entry.comparingByValue()).getKey();
                arenas.get(maxEntry.toString()).join(player);
            }
            available.clear();
        }
    }

    public boolean checkPlayerInArena(Player player) {
        for (GameArena arena : arenas.values()) {
            if (player.getLevel().getFolderName().equals(arena.level.getFolderName())) {
                return true;
            }
        }
        return false;
    }

    public GameArena getArenaWithPlayer(Player player) {
        for (GameArena arena : arenas.values()) {
            if (player.getLevel().getFolderName().equals(arena.level.getFolderName())) {
                return arena;
            }
        }
        return null;
    }

    public int getCountAllPlayers() {
        int i = 0;
        for (GameArena arena : arenas.values()) {
            for (Player player : arena.level.getPlayers().values()) {
                if (global_playing.containsKey(player.getName())) {
                    i++;
                }
            }
        }
        return  i;
    }
}
