package org.mobarena.utils.mobs.router;

public class RouteFinderSearchTask implements Runnable {
    private RouteFinder route;
    private int retryTime = 0;

    public RouteFinderSearchTask(RouteFinder route) {
        this.route = route;
    }

    @Override
    public void run() {
        if (this.route == null) return;
        while (retryTime < 50) {
            if (!this.route.isSearching()) {
                this.route.research();
                return;
            } else {
                retryTime += 10;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignore) {}
            }
        }
        route.interrupt();
    }
}
