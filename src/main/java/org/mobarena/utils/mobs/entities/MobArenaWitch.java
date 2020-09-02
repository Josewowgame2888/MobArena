package org.mobarena.utils.mobs.entities;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.item.EntityPotion;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.potion.Potion;
import org.mobarena.utils.Utils;
import org.mobarena.utils.mobs.types.MobArenaWalkingMonster;

public class MobArenaWitch extends MobArenaWalkingMonster {
    public static final int NETWORK_ID = 45;

    public MobArenaWitch(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.95f;
    }

    @Override
    public String getName() {
        return "MobArenaWitch";
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setMaxHealth(26);
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        if (creature instanceof Player) {
            Player player = (Player) creature;
            return !player.closed && player.spawned && player.isAlive() && (player.isSurvival() || player.isAdventure()) && distance <= 100;
        }
        return creature.isAlive() && !creature.closed && distance <= 80;
    }

    @Override
    public boolean attack(EntityDamageEvent ev) {
        super.attack(ev);
        return true;
    }

    @Override
    public void attackEntity(Entity player) {
        if (this.attackDelay > 60 && Utils.rand(1, 3) == 2 && this.distanceSquared(player) <= 60) {
            this.attackDelay = 0;
            if (player.isAlive() && !player.closed) {

                double f = 1;
                double yaw = this.yaw + Utils.rand(-5.0, 5.0);
                Location pos = new Location(this.x - Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 0.5, this.y + this.getEyeHeight(),
                        this.z + Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 0.5, yaw, pitch, this.level);
                if (this.getLevel().getBlockIdAt((int) pos.getX(), (int) pos.getY(), (int) pos.getZ()) != Block.AIR) {
                    return;
                }
                EntityPotion thrownPotion = (EntityPotion) Entity.createEntity("ThrownPotion", pos, this);

                if (this.distance(player) <= 8) {
                    thrownPotion.potionId = Potion.POISON;
                }

                thrownPotion.setMotion(new Vector3(-Math.sin(Math.toDegrees(yaw)) * Math.cos(Math.toDegrees(pitch)) * f * f, -Math.sin(Math.toDegrees(pitch)) * f * f,
                        Math.cos(Math.toDegrees(yaw)) * Math.cos(Math.toDegrees(pitch)) * f * f));
                ProjectileLaunchEvent launch = new ProjectileLaunchEvent(thrownPotion);
                this.server.getPluginManager().callEvent(launch);
                if (launch.isCancelled()) {
                    thrownPotion.close();
                } else {
                    thrownPotion.spawnToAll();
                    this.level.addSound(this, Sound.MOB_WITCH_THROW);
                }
            }
        }
    }

    @Override
    public Item[] getDrops() {
        return new Item[] { Item.get(Item.GOLD_NUGGET,0,2) };
    }

    @Override
    public int getKillExperience() {
        return 0;
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        this.setNameTag("§l§eWitch§r\n§f" + this.getHealth() + "§c❤");
        this.setNameTagVisible();
        return super.entityBaseTick(tickDiff);
    }

    @Override
    public int nearbyDistanceMultiplier() {
        return 8;
    }
}
