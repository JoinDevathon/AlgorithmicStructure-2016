package org.devathon.contest2016.minecraftsearch;

import org.devathon.contest2016.search.Rating;

public class MinecraftRating extends Rating{

    public static final float RATING_DOT = 1;
    public static final float RATING_PATH = 1;

    private float pathCosts;
    private int countDots;

    
    public static float getRatingDot() {
        return RATING_DOT;
    }

    public static float getRatingPath() {
        return RATING_PATH;
    }

    public float getPathCosts() {
        return pathCosts;
    }

    public void setPathCosts(int pathCosts) {
        this.pathCosts = pathCosts + RATING_PATH;
    }

    public int getCountDots() {
        return countDots;
    }

    public void setCountDots(int countDots) {
        this.countDots = countDots;
    }

    @Override
    public float getFinalValue() {
        return pathCosts + countDots * RATING_DOT;
    }
}
