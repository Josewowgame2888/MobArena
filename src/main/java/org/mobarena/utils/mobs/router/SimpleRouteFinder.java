package org.mobarena.utils.mobs.router;

import org.mobarena.utils.mobs.WalkingEntity;

public class SimpleRouteFinder extends RouteFinder {
    public SimpleRouteFinder(WalkingEntity entity) {
        super(entity);
    }

    @Override
    public boolean search() {
        this.resetNodes();
        this.addNode(new Node(this.destination));
        return true;
    }
}
