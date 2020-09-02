package org.mobarena.utils.mobs.router;

import org.mobarena.utils.mobs.MobArenaWalkingEntity;

public class SimpleRouteFinder extends RouteFinder {
    public SimpleRouteFinder(MobArenaWalkingEntity entity) {
        super(entity);
    }

    @Override
    public boolean search() {
        this.resetNodes();
        this.addNode(new Node(this.destination));
        return true;
    }
}
