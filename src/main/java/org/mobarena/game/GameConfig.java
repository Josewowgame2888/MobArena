package org.mobarena.game;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import org.mobarena.Loader;

import java.io.File;

public class GameConfig {
    public static void loadResources() {
        File file = new File(String.valueOf(Loader.instance.getDataFolder()));
        if (!file.exists()) {
            if (file.mkdir()) {
                Loader.instance.getLogger().info("Resources created");
            } else {
                Loader.instance.getLogger().error("A problem occurred while trying to create the plugin resources");
                Loader.instance.getServer().getPluginManager().disablePlugin(Loader.instance);
            }
        }
    }

    public static void create(Player player, String id, String level_name) {
        if (Loader.manager.exists(id)) {
            player.sendMessage("§9[mba]§c The arena §e" + id + " §calready exists!");
        } else if ((new File(Loader.instance.getServer().getDataPath() + "/worlds/" + level_name)).exists()) {
            Config config = new Config(Loader.instance.getDataFolder() + "/" + id + ".dat", Config.YAML);
            config.set("level", level_name);
            config.set("lobby", 0);
            config.set("spawn-1", 0);
            config.set("spawn-2", 0);
            config.set("spawn-3", 0);
            config.set("spawn-4", 0);
            config.set("mob-spawn-1", 0);
            config.set("mob-spawn-2", 0);
            config.set("mob-spawn-3", 0);
            config.set("mob-spawn-4", 0);
            config.set("mob-spawn-5", 0);
            config.set("mob-spawn-6", 0);
            config.save();
            if (!Loader.instance.getServer().isLevelLoaded(level_name)) {
                Loader.instance.getServer().loadLevel(level_name);
            }
            player.teleport(Loader.instance.getServer().getLevelByName(level_name).getSpawnLocation());
            player.setGamemode(1);
            player.sendMessage("§9[mba] §aThe arena §d" + id + " §awas created correctly");
            player.sendMessage("§9[mba] §7Next step §e/mba lobby " + id + " §7to configure the waiting lobby");
        } else {
            player.sendMessage("§9[mba]§c The world §e" + level_name + " §cdoes not exist");
        }
    }

    public static void setLobby(Player player, String id) {
        if (!Loader.manager.exists(id)) {
            player.sendMessage("§9[mba] §cThe arena " + id + " does not exist");
        } else {
            Config config = new Config(Loader.instance.getDataFolder() + "/" + id + ".dat", Config.YAML);
            double[] xyz = new double[]{ player.x, player.y, player.z };
            config.set("lobby", xyz);
            config.save();
            player.sendMessage("§9[mba]§7 Selected §e[lobby] §7of §e" + id);
            player.sendMessage("§9[mba] §7Next step §e/mba spawn " + id + " <1-4> §7to configure spawns");
        }
    }

    public static void setSpawn(Player player, String id, String number) {
        if (!Loader.manager.exists(id)) {
            player.sendMessage("§9[mba] §cThe arena " + id + " does not exist");
        } else if (number.equals("1") || number.equals("2") || number.equals("3") || number.equals("4")){
            Config config = new Config(Loader.instance.getDataFolder() + "/" + id + ".dat", Config.YAML);
            double[] xyz = new double[]{ player.x, player.y, player.z };
            config.set("spawn-" + number, xyz);
            config.save();
            player.sendMessage("§9[mba]§7 Selected §e[spawn-" + number + "] §7of §e" + id);
            if (number.equals("4")) {
                player.sendMessage("§9[mba] §7Next step §e/mba mob_spawn " + id + " <1-6> §7to configure mob spawns");
            }
        } else {
            player.sendMessage("§9[mba]§c Invalid argument §e[number] §c(1-4)");
        }
    }

    public static void setMobSpawn(Player player, String id, String number) {
        if (!Loader.manager.exists(id)) {
            player.sendMessage("§9[mba] §cThe arena " + id + " does not exist");
        } else if (number.equals("1") || number.equals("2") || number.equals("3") || number.equals("4")
        || number.equals("5") || number.equals("6")){
            Config config = new Config(Loader.instance.getDataFolder() + "/" + id + ".dat", Config.YAML);
            double[] xyz = new double[]{ player.x, player.y, player.z };
            config.set("mob-spawn-" + number, xyz);
            config.save();
            player.sendMessage("§9[mba]§7 Selected §e[mob-spawn" + number + "] §7of §e" + id);
            if (number.equals("6")) {
                player.sendMessage("§9[mba] §7Next step §e/mba save " + id + " §7to save the arena");
            }
        } else {
            player.sendMessage("§9[mba]§c Invalid argument §e[number] §c(1-6)");
        }
    }

    public static void save(Player player, String id) {
        if (!Loader.manager.exists(id)) {
            player.sendMessage("§9[mba] §cThe arena " + id + " does not exist");
        } else if (!Loader.manager.isLoaded(id)){
            player.teleport(Loader.instance.getServer().getDefaultLevel().getSpawnLocation());
            player.sendMessage("§9[mba]§a The area §d" + id + " §awas saved correctly");
            Loader.manager.load(id);
        } else {
            player.sendMessage("§9[mba] §cThe arena is already loaded");
        }
    }

}
