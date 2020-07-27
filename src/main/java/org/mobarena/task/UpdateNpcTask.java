package org.mobarena.task;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;
import org.mobarena.Loader;
import org.mobarena.utils.NPC;

public class UpdateNpcTask extends Task {
    private int time = 60;
    @Override
    public void onRun(int i) {
        /*
         * We don't need to update the counter every second!
         */
        if (time > 0) {
            time--;
        }
        if (time == 0) {
            for (Level level : Loader.instance.getServer().getLevels().values()) {
                for (Entity entity : level.getEntities()) {
                    if (entity instanceof NPC) {
                        entity.setNameTag("§l§6MobArena§r\n§7" + Loader.manager.getCountAllPlayers() + " Players\n§l§cCLICK TO PLAY");
                        entity.setNameTagVisible();
                        entity.setNameTagAlwaysVisible();
                    }
                }
            }
            time = 60;
        }
    }
}
