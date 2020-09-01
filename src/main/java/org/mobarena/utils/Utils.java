package org.mobarena.utils;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.network.protocol.UpdateAttributesPacket;

import java.nio.charset.StandardCharsets;
import java.util.SplittableRandom;
import java.util.UUID;

public class Utils {
    public static final SplittableRandom random = new SplittableRandom();

    public static void playSound(Player player, String sound) {
        PlaySoundPacket pk = new PlaySoundPacket();
        pk.pitch = 1;
        pk.volume = 1;
        pk.x = (int) player.x;
        pk.y = (int) player.y;
        pk.z = (int) player.z;
        pk.name = sound;
        player.dataPacket(pk);
    }

    public static void setFood(Player player, int food) {
        UpdateAttributesPacket upk = new UpdateAttributesPacket();
        upk.entityId = player.getId();
        Attribute attr = Attribute.getAttribute((int)7);
        attr.setMaxValue(20.0f);
        attr.setMinValue(0.0f);
        attr.setValue((float)food);
        upk.entries = new Attribute[]{attr};
        player.dataPacket(upk);
    }

    public static int rand(int min, int max) {
        if (min == max) {
            return max;
        }
        return random.nextInt(max + 1 - min) + min;
    }

    public static double rand(double min, double max) {
        if (min == max) {
            return max;
        }
        return min + Math.random() * (max-min);
    }

    public static float rand(float min, float max) {
        if (min == max) {
            return max;
        }
        return min + (float) Math.random() * (max-min);
    }

    public static boolean rand() {
        return random.nextBoolean();
    }

    public static final int ACCORDING_X_OBTAIN_Y = 0;
    public static final int ACCORDING_Y_OBTAIN_X = 1;

    public static double calLinearFunction(Vector3 pos1, Vector3 pos2, double element, int type) {
        if (pos1.getFloorY() != pos2.getFloorY()) return Double.MAX_VALUE;
        if (pos1.getX() == pos2.getX()) {
            if (type == ACCORDING_Y_OBTAIN_X) return pos1.getX();
            else return Double.MAX_VALUE;
        } else if (pos1.getZ() == pos2.getZ()) {
            if (type == ACCORDING_X_OBTAIN_Y) return pos1.getZ();
            else return Double.MAX_VALUE;
        } else {
            if (type == ACCORDING_X_OBTAIN_Y) {
                return (element-pos1.getX()) * (pos1.getZ()-pos2.getZ()) / (pos1.getX()-pos2.getX()) + pos1.getZ();
            } else {
                return (element-pos1.getZ()) * (pos1.getX()-pos2.getX()) / (pos1.getZ()-pos2.getZ()) + pos1.getX();
            }
        }
    }

    public static boolean entityInsideWaterFast(Entity ent) {
        double y = ent.y + ent.getEyeHeight();
        int b = ent.level.getBlockIdAt(NukkitMath.floorDouble(ent.x), NukkitMath.floorDouble(y), NukkitMath.floorDouble(ent.z));
        return b == BlockID.WATER || b == BlockID.STILL_WATER;
    }

    public static void removeItem(Player player, Item item, int amount) {
        int slot = -1;
        int size = player.getInventory().getSize();

        for (int i = size; i > 0; i--) {
            if (player.getInventory().getItem(i).getId() == item.getId()
                    && player.getInventory().getItem(i).getDamage() == item.getDamage()
                    && player.getInventory().getItem(i).getCount() >= amount) {
                slot = i;
                break;
            }
        }

        if (slot != -1) {
            int max = player.getInventory().getItem(slot).getCount();
            int result = max - amount;

            if (result <= 0) {
                player.getInventory().setItem(slot, Item.get(item.getId(), item.getDamage(), 0));
            } else {
                player.getInventory().setItem(slot, Item.get(item.getId(), item.getDamage(), result));
            }
        }
    }

    public static CompoundTag createBaseNBT(Player sender) {
        sender.saveNBT();
        CompoundTag nbt = new CompoundTag()
                .put("Pos", sender.namedTag.get("Pos").copy())
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .put("Rotation", sender.namedTag.get("Rotation").copy())
                .putBoolean("Invulnerable", true)
                .putFloat("scale", 1);
        nbt.put("Skin", sender.namedTag.get("Skin").copy());
        nbt.putBoolean("ishuman", true);
        nbt.putString("Item", sender.getInventory().getItemInHand().getName());
        nbt.putString("Helmet", sender.getInventory().getHelmet().getName());
        nbt.putString("Chestplate", sender.getInventory().getChestplate().getName());
        nbt.putString("Leggings", sender.getInventory().getLeggings().getName());
        nbt.putString("Boots", sender.getInventory().getBoots().getName());
        return nbt;
    }

}
