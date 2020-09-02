package org.mobarena.utils.mobs.entities;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityArthropod;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.mobarena.utils.mobs.types.MobArenaWalkingMonster;

import java.util.HashMap;

public class MobArenaSpider extends MobArenaWalkingMonster implements EntityArthropod {
    public static final int NETWORK_ID = 35;


    public MobArenaSpider(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 1.4f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public double getSpeed() {
        return 1.13;
    }

    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(16);
        this.setDamage(new float[] { 0, 2, 2, 3 });
    }

    @Override
    public String getName() {
        return "MobArenaSpider";
    }

    @Override
    public void attackEntity(Entity player) {
        if (!this.isFriendly() || !(player instanceof Player)) {
            if (this.isAngry()) {
                if (this.attackDelay > 23 && this.distanceSquared(player) < 1.3) {
                    this.attackDelay = 0;
                    HashMap<EntityDamageEvent.DamageModifier, Float> damage = new HashMap<>();
                    damage.put(EntityDamageEvent.DamageModifier.BASE, this.getDamage());

                    if (player instanceof Player) {
                        damage.put(EntityDamageEvent.DamageModifier.ARMOR,
                                (float) (damage.getOrDefault(EntityDamageEvent.DamageModifier.ARMOR, 0f) - Math.floor(damage.getOrDefault(EntityDamageEvent.DamageModifier.BASE, 1f) * 2 * 0.04)));
                    }
                    player.attack(new EntityDamageByEntityEvent(this, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
                }
            }
        }
    }

    @Override
    public Item[] getDrops() {
        return new Item[] { Item.get(Item.GOLD_NUGGET,0,1) };
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        this.setNameTag("§l§eSpider§r\n§f" + this.getHealth() + "§c❤");
        this.setNameTagVisible();
        return super.entityBaseTick(tickDiff);
    }

    public boolean isAngry() {
        return true;
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        return this.isAngry() && super.targetOption(creature, distance);
    }
}
