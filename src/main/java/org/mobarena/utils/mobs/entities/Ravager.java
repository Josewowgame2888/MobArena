package org.mobarena.utils.mobs.entities;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.mobarena.utils.mobs.types.WalkingMonster;

import java.util.HashMap;

public class Ravager extends WalkingMonster {
    public static final int NETWORK_ID = 59;

    public Ravager(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        this.setMaxHealth(100);
        this.setDamage(new float[] { 0, 7, 12, 18 });
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public float getWidth() {
        return 1.2f;
    }

    @Override
    public String getName() {
        return "Ravager";
    }

    @Override
    public void attackEntity(Entity player) {
        if (this.attackDelay > 80 && player.distanceSquared(this) <= 1.5) {
            this.attackDelay = 0;
            HashMap<EntityDamageEvent.DamageModifier, Float> damage = new HashMap<>();
            damage.put(EntityDamageEvent.DamageModifier.BASE, this.getDamage());

            if (player instanceof Player) {
                damage.put(EntityDamageEvent.DamageModifier.ARMOR, (float) (damage.getOrDefault(EntityDamageEvent.DamageModifier.ARMOR, 0f) - Math.floor(damage.getOrDefault(EntityDamageEvent.DamageModifier.BASE, 1f) * 2 * 0.04)));
            }

            player.attack(new EntityDamageByEntityEvent(this, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
        }
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        this.setNameTag("§l§eRavager§r\n§f" + this.getHealth() + "§c❤");
        this.setNameTagVisible();
        return super.entityBaseTick(tickDiff);
    }

    @Override
    public int getKillExperience() {
        return 0;
    }

    @Override
    public Item[] getDrops() {
        return new Item[] { Item.get(Item.GOLD_NUGGET,0,2) };
    }
}
