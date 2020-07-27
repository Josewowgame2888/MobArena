package org.mobarena.utils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import org.mobarena.utils.form.elements.ImageType;
import org.mobarena.utils.form.elements.SimpleForm;

public class ShopManager {
    public static void addForm(Player player) {
        SimpleForm form = new SimpleForm("§l§6SHOP§r")
                .addButton("§l§dChainmail Helmet§r\n5\uE102", ImageType.PATH, "textures/items/chainmail_helmet")
                .addButton("§l§dChainmail Chestplate§r\n5\uE102", ImageType.PATH, "textures/items/chainmail_chestplate")
                .addButton("§l§dChainmail Leggings§r\n5\uE102", ImageType.PATH, "textures/items/chainmail_leggings")
                .addButton("§l§dChainmail Boots§r\n5\uE102", ImageType.PATH, "textures/items/chainmail_boots")
                .addButton("§l§dIron Helmet§r\n10\uE102", ImageType.PATH, "textures/items/iron_helmet")
                .addButton("§l§dIron Chestplate§r10\n\uE102", ImageType.PATH, "textures/items/iron_chestplate")
                .addButton("§l§dIron Leggings§r\n10\uE102", ImageType.PATH, "textures/items/iron_leggings")
                .addButton("§l§dIron Boots§r\n10\uE102", ImageType.PATH, "textures/items/iron_boots")
                .addButton("§l§dDiamond Helmet§r\n20\uE102", ImageType.PATH, "textures/items/diamond_helmet")
                .addButton("§l§dDiamond Chestplate§r\n20\uE102", ImageType.PATH, "textures/items/diamond_chestplate")
                .addButton("§l§dDiamond Leggings§r\n20\uE102", ImageType.PATH, "textures/items/diamond_leggings")
                .addButton("§l§dDiamond Boots§r\n20\uE102", ImageType.PATH, "textures/items/diamond_boots")
                .addButton("§l§dWood Sword§r\n3\uE102", ImageType.PATH, "textures/items/wood_sword")
                .addButton("§l§dStone Sword§r\n15\uE102", ImageType.PATH, "textures/items/stone_sword")
                .addButton("§l§dIron Sword§r\n25\uE102", ImageType.PATH, "textures/items/iron_sword")
                .addButton("§l§dDiamond Sword§r\n40\uE102", ImageType.PATH, "textures/items/diamond_sword")
                .addButton("§l§dCooked Steak§r\n3\uE102", ImageType.PATH, "textures/items/hoglin_meat_cooked")
                .addButton("§l§dGolden Apple§r\n5\uE102", ImageType.PATH, "textures/items/apple_golden")
                .addButton("§l§dBow§r\n10\uE102", ImageType.PATH, "textures/items/bow_standby")
                .addButton("§l§dArrow§r\n2\uE102", ImageType.PATH, "textures/items/arrow")
                .addButton("§l§dClear Effects§r\n5\uE102", ImageType.PATH, "textures/items/nether_star");
        form.send(player, (targetPlayer, targetForm, data) -> {
            if (data == -1) return;
            if (data == 0) {
                if (hasEnoughNuggets(targetPlayer, 5)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 5);
                    targetPlayer.getInventory().addItem(Item.get(Item.CHAIN_HELMET, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 1) {
                if (hasEnoughNuggets(targetPlayer, 5)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 5);
                    targetPlayer.getInventory().addItem(Item.get(Item.CHAIN_CHESTPLATE, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 2) {
                if (hasEnoughNuggets(targetPlayer, 5)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 5);
                    targetPlayer.getInventory().addItem(Item.get(Item.CHAIN_LEGGINGS, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 3) {
                if (hasEnoughNuggets(targetPlayer, 5)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 5);
                    targetPlayer.getInventory().addItem(Item.get(Item.CHAIN_BOOTS, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 4) {
                if (hasEnoughNuggets(targetPlayer, 10)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 10);
                    targetPlayer.getInventory().addItem(Item.get(Item.IRON_HELMET, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 5) {
                if (hasEnoughNuggets(targetPlayer, 10)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 10);
                    targetPlayer.getInventory().addItem(Item.get(Item.IRON_CHESTPLATE, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 6) {
                if (hasEnoughNuggets(targetPlayer, 10)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 10);
                    targetPlayer.getInventory().addItem(Item.get(Item.IRON_LEGGINGS, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 7) {
                if (hasEnoughNuggets(targetPlayer, 10)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 10);
                    targetPlayer.getInventory().addItem(Item.get(Item.IRON_BOOTS, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 8) {
                if (hasEnoughNuggets(targetPlayer, 20)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 20);
                    targetPlayer.getInventory().addItem(Item.get(Item.DIAMOND_HELMET, 0, 1));
                }
            }
            if (data == 9) {
                if (hasEnoughNuggets(targetPlayer, 20)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 20);
                    targetPlayer.getInventory().addItem(Item.get(Item.DIAMOND_CHESTPLATE, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 10) {
                if (hasEnoughNuggets(targetPlayer, 20)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 20);
                    targetPlayer.getInventory().addItem(Item.get(Item.DIAMOND_LEGGINGS, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 11) {
                if (hasEnoughNuggets(targetPlayer, 20)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 20);
                    targetPlayer.getInventory().addItem(Item.get(Item.DIAMOND_BOOTS, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }

            if (data == 12) {
                if (hasEnoughNuggets(targetPlayer, 3)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 3);
                    targetPlayer.getInventory().addItem(Item.get(Item.WOODEN_SWORD, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 13) {
                if (hasEnoughNuggets(targetPlayer, 15)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 15);
                    targetPlayer.getInventory().addItem(Item.get(Item.STONE_SWORD, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 14) {
                if (hasEnoughNuggets(targetPlayer, 25)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 25);
                    targetPlayer.getInventory().addItem(Item.get(Item.IRON_SWORD, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 15) {
                if (hasEnoughNuggets(targetPlayer, 40)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 40);
                    targetPlayer.getInventory().addItem(Item.get(Item.DIAMOND_SWORD, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }

            if (data == 16) {
                if (hasEnoughNuggets(targetPlayer, 3)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 3);
                    targetPlayer.getInventory().addItem(Item.get(Item.COOKED_PORKCHOP, 0, 2));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 17) {
                if (hasEnoughNuggets(targetPlayer, 5)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 5);
                    targetPlayer.getInventory().addItem(Item.get(Item.GOLDEN_APPLE, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }

            if (data == 18) {
                if (hasEnoughNuggets(targetPlayer, 10)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 10);
                    targetPlayer.getInventory().addItem(Item.get(Item.BOW, 0, 1));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 19) {
                if (hasEnoughNuggets(targetPlayer, 2)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 2);
                    targetPlayer.getInventory().addItem(Item.get(Item.ARROW, 0, 2));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
            if (data == 20) {
                if (hasEnoughNuggets(targetPlayer, 5)) {
                    Utils.removeItem(targetPlayer, Item.get(Item.GOLD_NUGGET, 0), 5);
                    targetPlayer.getInventory().addItem(Item.get(Item.NETHER_STAR, 0, 1).setCustomName("§l§bClear Effects"));
                } else {
                    targetPlayer.sendMessage("§cYou don't have enough gold nuggets");
                    Utils.playSound(targetPlayer, "item.trident.throw");
                }
            }
        });
    }

    private static boolean hasEnoughNuggets(Player player, int amount) {
        return player.getInventory().contains(Item.get(Item.GOLD_NUGGET, 0, amount));
    }

}
