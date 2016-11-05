package org.devathon.contest2016.minecraftsearch;

import org.bukkit.util.Vector;

public enum MinecraftAction {
    START(0, 0),
    GO_NORTH(0, -1),
    GO_EAST(1, 0),
    GO_SOUTH(0, 1),
    GO_WEST(-1, 0),
    DO_STUFF(0,0),
    EXIT(0, 0);

    public final Vector vector;
    
    private MinecraftAction() {
    	this.vector = new Vector(0, 0, 0);
    }

    private MinecraftAction(int x, int z) {
        this.vector = new Vector(x, 0, z);
    }

    public Vector getVector() {
        return this.vector;
    }
}
