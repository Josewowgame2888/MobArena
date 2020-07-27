package org.mobarena.utils;

import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class NPC extends EntityHuman {
    public NPC(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
}
