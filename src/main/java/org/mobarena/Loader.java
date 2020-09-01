/*
   _____        ___.       _____
  /     \   ____\_ |__    /  _  \_______   ____   ____ _____
 /  \ /  \ /  _ \| __ \  /  /_\  \_  __ \_/ __ \ /    \\__  \
/    Y    (  <_> ) \_\ \/    |    \  | \/\  ___/|   |  \/ __ \_
\____|__  /\____/|___  /\____|__  /__|    \___  >___|  (____  /
        \/           \/         \/            \/     \/     \/

 *        Adapted from the Wizardry License

 * Copyright (c) 2020-2022 Josewowgame2888 and contributors

 * Permission is hereby granted to any persons and/or organizations
 * using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or
 * any derivatives of the work for commercial use or any other means to generate
 * income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing
 * and/or trademarking this software without explicit permission from Josewowgame2888.
 *
 * Any persons and/or organizations using this software must disclose their
 * source code and have it publicly available, include this license,
 * provide sufficient credit to the original authors of the project (IE: Josewowgame2888),
 * as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,FITNESS FOR A PARTICULAR
 * PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * CREDITS
 * https://github.com/Nukkit-coders/MobPlugin
 */

package org.mobarena;

import cn.nukkit.entity.Entity;
import cn.nukkit.plugin.PluginBase;
import org.mobarena.command.SetupCommand;
import org.mobarena.game.GameConfig;
import org.mobarena.game.GameManager;
import org.mobarena.listener.ArenaListener;
import org.mobarena.listener.DamageListener;
import org.mobarena.listener.FormResponseEvent;
import org.mobarena.task.UpdateNpcTask;
import org.mobarena.task.WaitingLobbyTask;
import org.mobarena.utils.NPC;
import org.mobarena.utils.mobs.entities.*;

public class Loader extends PluginBase {
    public static Loader instance;
    public static GameManager manager;

    @Override
    public void onEnable() {
        instance = this;

        //game
        GameConfig.loadResources();
        manager = new GameManager();
        manager.loadAll();

        //commands
        getServer().getCommandMap().register("/mba", new SetupCommand());

        //listener
        getServer().getPluginManager().registerEvents(new ArenaListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new FormResponseEvent(), this);

        //scheduler
        getServer().getScheduler().scheduleRepeatingTask(new WaitingLobbyTask(), 20);
        getServer().getScheduler().scheduleRepeatingTask(new UpdateNpcTask(), 20);

        //entities
        Entity.registerEntity(MobArenaZombie.class.getSimpleName(), MobArenaZombie.class);
        Entity.registerEntity(MobArenaSkeleton.class.getSimpleName(), MobArenaSkeleton.class);
        Entity.registerEntity(MobArenaSpider.class.getSimpleName(), MobArenaSpider.class);
        Entity.registerEntity(MobArenaMobArenaZombiePigman.class.getSimpleName(), MobArenaMobArenaZombiePigman.class);
        Entity.registerEntity(MobArenaWitherSkeleton.class.getSimpleName(), MobArenaWitherSkeleton.class);
        Entity.registerEntity(MobArenaWitch.class.getSimpleName(), MobArenaWitch.class);
        Entity.registerEntity(MobArenaZoglin.class.getSimpleName(), MobArenaZoglin.class);
        Entity.registerEntity(MobArenaSilverfish.class.getSimpleName(), MobArenaSilverfish.class);
        Entity.registerEntity(MobArenaRavager.class.getSimpleName(), MobArenaRavager.class);
        Entity.registerEntity(NPC.class.getSimpleName(), NPC.class);
    }

    @Override
    public void onDisable() {}
}
