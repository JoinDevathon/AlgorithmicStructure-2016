package org.devathon.contest2016.minecraftsearch;

import org.devathon.contest2016.search.Element;
import org.devathon.contest2016.search.Vector2;

public class MinecraftPercept {
	
    private boolean dot = false;
    private Element[][] view;
    private Vector2 position;

    public MinecraftPercept(boolean dot, Element[][] view, Vector2 position) {
        this.dot = dot;
        this.view = view;
        this.position = position;
    }

    public Element[][] getView() {
        return this.view;
    }

    public boolean isDotBelow() {
        return this.dot;
    }

    public Vector2 getPosition() {
        return this.position;
    }
}
