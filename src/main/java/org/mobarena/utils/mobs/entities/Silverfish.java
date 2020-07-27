package org.mobarena.utils.mobs.entities;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityArthropod;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.mobarena.utils.mobs.router.WalkerRouteFinder;
import org.mobarena.utils.mobs.types.WalkingMonster;

import java.util.HashMap;

public class Silverfish extends WalkingMonster implements EntityArthropod {
    public static final int NETWORK_ID = 39;

    public Silverfish(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.route = new WalkerRouteFinder(this);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.4f;
    }

    @Override
    public float getHeight() {
        return 0.3f;
    }

    @Override
    public double getSpeed() {
        return 1.4;
    }

    @Override
    public String getName() {
        return "Silverfish";
    }

    @Override
    public void initEntity() {
        super.initEntity();

        this.setMaxHealth(8);
        this.setDamage(new float[] { 0, 1, 1, 1 });
    }

    @Override
    public void attackEntity(Entity player) {
        if (this.attackDelay > 23 && this.distanceSquared(player) < 1) {
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
        this.setNameTag("§l§eSilverfish§r\n§f" + this.getHealth() + "§c❤");
        this.setNameTagVisible();
        return super.entityBaseTick(tickDiff);
    }
}
