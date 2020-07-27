package org.mobarena.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import org.mobarena.Loader;
import org.mobarena.game.GameConfig;
import org.mobarena.utils.NPC;
import org.mobarena.utils.Utils;

public class SetupCommand extends VanillaCommand {
    public SetupCommand() {
        super("mba", "Mob Arena Command", "/mba <args>", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1 && sender.isOp()) {
                if (args[0].equals("create")) {//mba create <name> <level>
                    if (args.length == 3) {
                        GameConfig.create((Player) sender, args[1], args[2]);
                    } else {
                        sender.sendMessage("§9[mba]§7 usage: /mba create <name> <level>");
                    }
                }
                if (args[0].equals("lobby")) {//mba lobby <name>
                    if (args.length == 2) {
                        GameConfig.setLobby((Player) sender, args[1]);
                    } else {
                        sender.sendMessage("§9[mba]§7 usage: /mba lobby <name>");
                    }
                }
                if (args[0].equals("spawn")) {//mba spawn <name> <1-4>
                    if (args.length == 3) {
                        GameConfig.setSpawn((Player) sender, args[1], args[2]);
                    } else {
                        sender.sendMessage("§9[mba]§7 usage: /mba spawn <name> <1-4>");
                    }
                }
                if (args[0].equals("mob_spawn")) {//mba mob_spawn <name> <1-6>
                    if (args.length == 3) {
                        GameConfig.setMobSpawn((Player) sender, args[1], args[2]);
                    } else {
                        sender.sendMessage("§9[mba]§7 usage: /mba mob_spawn <name> <1-6>");
                    }
                }
                if (args[0].equals("save")) {//mba save <name>
                    if (args.length == 2) {
                        GameConfig.save((Player) sender, args[1]);
                    } else {
                        sender.sendMessage("§9[mba]§7 usage: /mba save <name>");
                    }
                }
                if (args[0].equals("npc")) {
                    for (Level level : Loader.instance.getServer().getLevels().values()) {
                        for (Entity entity : level.getEntities()) {
                            if (entity instanceof NPC) {
                                entity.close();
                            }
                        }
                    }
                    NPC npc = new NPC(((Player) sender).chunk, Utils.createBaseNBT((Player) sender));
                    npc.setNameTag("§l§6MobArena§r\n§70 Players\n§l§cCLICK TO PLAY");
                    npc.setNameTagAlwaysVisible();
                    npc.setNameTagAlwaysVisible();
                    npc.spawnToAll();
                }
            } else if (sender.isOp()) {
                sender.sendMessage("§a/mba npc");
                sender.sendMessage("§a/mba create <name> <level>");
                sender.sendMessage("§a/mba lobby <name>");
                sender.sendMessage("§a/mba spawn <name> <1-4>");
                sender.sendMessage("§a/mba mob_spawn <name> <1-6>");
                sender.sendMessage("§a/mba save <name>");
            }
        } else {
            sender.sendMessage("§cYou cannot use the command in Console!");
        }
        return false;
    }
}
