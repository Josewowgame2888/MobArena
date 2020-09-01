package org.mobarena.game;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.DummyBossBar;
import org.mobarena.Loader;
import org.mobarena.utils.BossBar;
import org.mobarena.utils.Utils;
import org.mobarena.utils.mobs.MobArenaBaseMob;

public class GameArena {
    public String id;
    public Config config;
    public String status = "on";
    public Level level;
    public int start_time = 30;
    public int round = 0;

    public GameArena(String id) {
        this.id = id;
        this.config = new Config(Loader.instance.getDataFolder() + "/" + id + ".dat", Config.YAML);

        if (!Loader.instance.getServer().isLevelLoaded(this.config.getString("level"))) {
            Loader.instance.getServer().loadLevel(this.config.getString("level"));
        }

        this.level = Loader.instance.getServer().getLevelByName(this.config.getString("level"));
        this.level.setThundering(false);
        this.level.setTime(0);
        this.level.stopTime();

        this.removeMobsStuff();
    }

    public void join(Player player) {
        Loader.manager.global_playing.put(player.getName(), 0);
        player.getInventory().clearAll();
        player.removeAllEffects();
        player.setGamemode(2);
        player.setMaxHealth(20);
        player.setHealth(20);
        Utils.setFood(player, 20);
        player.getInventory().setItem(8, Item.get(Item.TOTEM).setCustomName("§l§eLEAVE"));
        player.teleport(new Position(
                this.config.getDoubleList("lobby").get(0),
                this.config.getDoubleList("lobby").get(1),
                this.config.getDoubleList("lobby").get(2),
                this.level
        ));

        for (Player source : this.level.getPlayers().values()) {
            source.sendMessage("§8" + player.getName() + "§7 joined the game (§3" + this.getCountPlaying() + "§7/§38§7)!");
            Utils.playSound(source, "random.orb");
        }

        if (!BossBar.hasBossBar(player)) {
            DummyBossBar bossBar = new DummyBossBar.Builder(player)
                    .text(BossBar.getBossBarTextByArena(this))
                    .build();
            player.createBossBar(bossBar);
            BossBar.sendBossBar(player, bossBar);
        }
    }

    public void quit(Player player) {
        player.getInventory().clearAll();
        player.removeAllEffects();
        player.setGamemode(0);
        player.setMaxHealth(20);
        player.setHealth(20);
        Utils.setFood(player, 20);

        if (BossBar.hasBossBar(player)) {
            BossBar.removeBossBar(player);
        }
        player.teleport(Loader.instance.getServer().getDefaultLevel().getSpawnLocation());
        Loader.manager.global_playing.remove(player.getName());
    }

    public int getCountPlaying() {
        int i = 0;
        for (Player player : this.level.getPlayers().values()) {
            if (Loader.manager.global_playing.containsKey(player.getName())) {
                i++;
            }
        }
        return i;
    }

    public int getAlive() {
        int i = 0;
        for (Player player : this.level.getPlayers().values()) {
            if (!player.isSpectator() && Loader.manager.global_playing.containsKey(player.getName())) {
                i++;
            }
        }
        return i;
    }

    public int getCountMobs() {
        int i = 0;
        for (Entity entity : this.level.getEntities()) {
            if (entity instanceof MobArenaBaseMob) {
                i++;
            }
        }
        return i;
    }

    public void removeMobsStuff() {
        for (Entity entity : this.level.getEntities()) {
            if (entity instanceof EntityArrow || entity instanceof EntityItem) {
                entity.close();
            }
        }
    }

    public void removeAllMobs() {
        for (Entity entity : this.level.getEntities()) {
            if (entity instanceof MobArenaBaseMob) {
                entity.close();
            }
        }
    }

    public void removeNotFromGame() {
        for (Entity entity : this.level.getEntities()) {
            if (!(entity instanceof MobArenaBaseMob)) {
                if (!(entity instanceof Player)) {
                    entity.close();
                }
            }
        }
    }

    public void teleportRandom(Player player) {
        String random = "spawn-" + (int) (Math.random()*3+1);
        player.teleport(new Vector3(
                this.config.getDoubleList(random).get(0),
                this.config.getDoubleList(random).get(1),
                this.config.getDoubleList(random).get(2)
        ));
    }

    public void giveBasicKit(Player player) {
        player.getInventory().clearAll();
        player.setGamemode(0);
        player.setHealth(20);
        Utils.setFood(player, 20);
        player.getInventory().setItem(0, Item.get(Item.PAPER).setCustomName("§l§eSHOP"));
        player.getInventory().setItem(1, Item.get(Item.WOODEN_SWORD));
        player.getInventory().setHelmet(Item.get(Item.LEATHER_CAP));
        player.getInventory().setChestplate(Item.get(Item.LEATHER_TUNIC));
        player.getInventory().setLeggings(Item.get(Item.LEATHER_PANTS));
        player.getInventory().setBoots(Item.get(Item.LEATHER_BOOTS));
    }

    public void spawnEntities() {
        if (round == 1) {
            /*
             + 3 Zombies
             * TOTAL: 3
             */
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaZombie");
            }
        }
        if (round == 2) {
            /*
             + 4 Zombies
             * TOTAL: 4
             */
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZombie");
            }
        }
        if (round == 3) {
            /*
             + 4 Zombies
             * 2 skeleton
             * TOTAL: 6
             */
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
        }
        if (round == 4) {
            /*
             + 5 Zombies
             * 3 skeleton
             * TOTAL: 8
             */
            for (int i = 0; i < 5; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
        }
        if (round == 5) {
            /*
             + 4 Zombies
             * 6 skeleton
             * TOTAL: 10
             */
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
        }
        if (round == 6) {
            /*
             + 4 Zombies
             * 7 skeleton
             * 2 spiders
             * TOTAL: 13
             */
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaSpider");
            }
        }
        if (round == 7) {
            /*
             + 4 Zombies
             * 6 Skeletons
             * 5 spiders
             * TOTAL: 15
             */
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
            for (int i = 0; i < 5; i++) {
                this.spawnEntity("MobArenaSpider");
            }
        }
        if (round == 8) {
            /*
             + 3 Zombies
             * 4 Skeletons
             * 9 spiders
             * TOTAL: 17
             */
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
            for (int i = 0; i < 9; i++) {
                this.spawnEntity("MobArenaSpider");
            }
        }
        if (round == 9) {
            /*
             + 3 Zombies
             * 3 Skeletons
             * 6 spiders
             * 8 ZombiePigmans
             * TOTAL: 20
             */
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaSpider");
            }
            for (int i = 0; i < 8; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
        }
        if (round == 10) {
            /*
             + 3 Zombies
             * 1 Skeletons
             * 6 spiders
             * 12 ZombiePigmans
             * TOTAL: 22
             */
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 1; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaSpider");
            }
            for (int i = 0; i < 12; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
        }
        if (round == 11) {
            /*
             + 3 Zombies
             * 6 spiders
             * 8 ZombiePigmans
             * 7 WitherSkeleton
             * TOTAL: 24
             */
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaZombie");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaSpider");
            }
            for (int i = 0; i < 8; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
        }
        if (round == 12) {
            /*
             * 7 spiders
             * 9 ZombiePigmans
             * 9 WitherSkeleton
             * TOTAL: 25
             */
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaSpider");
            }
            for (int i = 0; i < 9; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
            for (int i = 0; i < 9; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
        }
        if (round == 13) {
            /*
             * 4 spiders
             * 7 ZombiePigmans
             * 7 WitherSkeleton
             * 8 Witch
             * TOTAL: 26
             */
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaSpider");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
            for (int i = 0; i < 8; i++) {
                this.spawnEntity("MobArenaWitch");
            }
        }
        if (round == 14) {
            /*
             * 3 spiders
             * 7 ZombiePigmans
             * 7 WitherSkeleton
             * 9 Witch
             * TOTAL: 26
             */
            for (int i = 0; i < 3; i++) {
                this.spawnEntity("MobArenaSpider");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
            for (int i = 0; i < 9; i++) {
                this.spawnEntity("MobArenaWitch");
            }
        }
        if (round == 15) {
            /*
             * 2 ZombiePigmans
             * 2 WitherSkeleton
             * 7 Witch
             * 9 Zoglin
             * TOTAL: 20
             */
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
            for (int i = 0; i < 7; i++) {
                this.spawnEntity("MobArenaWitch");
            }
            for (int i = 0; i < 9; i++) {
                this.spawnEntity("MobArenaZoglin");
            }
        }
        if (round == 16) {
            /*
             * 2 ZombiePigmans
             * 2 WitherSkeleton
             * 6 Witch
             * 10 Zoglin
             * TOTAL: 20
             */
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaZombiePigman");
            }
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaWitch");
            }
            for (int i = 0; i < 10; i++) {
                this.spawnEntity("MobArenaZoglin");
            }
        }
        if (round == 17) {
            /*
             * 2 WitherSkeleton
             * 6 Witch
             * 4 Zoglin
             * 6 Silverfish
             * TOTAL: 18
             */
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaWitch");
            }
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZoglin");
            }
            for (int i = 0; i < 6; i++) {
                this.spawnEntity("MobArenaSilverfish");
            }
        }
        if (round == 18) {
            /*
             * 2 WitherSkeleton
             * 2 Witch
             * 4 Zoglin
             * 10 Silverfish
             * TOTAL: 18
             */
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitherSkeleton");
            }
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitch");
            }
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZoglin");
            }
            for (int i = 0; i < 10; i++) {
                this.spawnEntity("MobArenaSilverfish");
            }
        }
        if (round == 19) {
            /*
             * 2 Witch
             * 4 Zoglin
             * 10 Silverfish
             * 2 Ravager
             * TOTAL: 18
             */
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitch");
            }
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaZoglin");
            }
            for (int i = 0; i < 10; i++) {
                this.spawnEntity("MobArenaSilverfish");
            }
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaRavager");
            }
        }
        if (round == 20) {
            /*
             * 2 Witch
             * 4 Skeleton
             * 8 Silverfish
             * 10 Ravager
             * TOTAL: 18
             */
            for (int i = 0; i < 2; i++) {
                this.spawnEntity("MobArenaWitch");
            }
            for (int i = 0; i < 4; i++) {
                this.spawnEntity("MobArenaSkeleton");
            }
            for (int i = 0; i < 8; i++) {
                this.spawnEntity("MobArenaSilverfish");
            }
            for (int i = 0; i < 10; i++) {//hard :D
                this.spawnEntity("MobArenaRavager");
            }
        }
    }

    private void spawnEntity(String type) {
        int max = (int) (Math.random()*5+1);
        String random = "mob-spawn-" + max;
        Entity entity = Entity.createEntity(type, new Position(
                this.config.getDoubleList(random).get(0),
                this.config.getDoubleList(random).get(1),
                this.config.getDoubleList(random).get(2),
                this.level
        ));
        entity.spawnToAll();
    }

}
