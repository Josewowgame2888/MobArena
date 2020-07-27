package org.mobarena.utils.mobs.entities;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.EntitySmite;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSwordGold;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import org.mobarena.utils.mobs.router.WalkerRouteFinder;
import org.mobarena.utils.mobs.types.WalkingMonster;

import java.util.HashMap;

public class ZombiePigman extends WalkingMonster implements EntitySmite {
    public static final int NETWORK_ID = 36;

    private int angry = 0;

    public ZombiePigman(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.route = new WalkerRouteFinder(this);
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
    public double getSpeed() {
        return 1.15;
    }

    @Override
    public String getName() {
        return "ZombiePigman";
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        if (this.namedTag.contains("Angry")) {
            this.angry = this.namedTag.getInt("Angry");
        }

        this.fireProof = true;
        this.setDamage(new float[] { 0, 5, 9, 13 });
        this.setMaxHealth(20);
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putInt("Angry", this.angry);
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        return this.isAngry() && super.targetOption(creature, distance);
    }

    @Override
    public void attackEntity(Entity player) {
        if (this.attackDelay > 23 && this.distanceSquared(player) < 1.44) {
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

    public boolean isAngry() {
        return true;
    }


    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getId();
        pk.item = new ItemSwordGold();
        pk.inventorySlot = 0;
        player.dataPacket(pk);
    }

    @Override
    public Item[] getDrops() {
        return new Item[] { Item.get(Item.GOLD_NUGGET,0,1) };
    }

    @Override
    public int getKillExperience() {
        return 0;
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        this.setNameTag("§l§eZombiePigman§r\n§f" + this.getHealth() + "§c❤");
        this.setNameTagVisible();
        return super.entityBaseTick(tickDiff);
    }
}
