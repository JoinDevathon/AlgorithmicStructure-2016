package org.devathon.contest2016.search;

public class Vector2 {

    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void set(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Vector2 add(Vector2 o) {
        this.x += o.x;
        this.y += o.y;
        return this;
    }

    public Vector2 sub(Vector2 o) {
        this.x -= o.x;
        this.y -= o.y;
        return this;
    }

    public Vector2 mul(float mul) {
        this.x = (int)((float)this.x * mul);
        this.y = (int)((float)this.y * mul);
        return this;
    }

    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }

    public boolean equals(Object obj) {
        if(obj instanceof Vector2) {
            Vector2 o = (Vector2)obj;
            if(this.x == o.x && this.y == o.y) {
                return true;
            }
        }

        return false;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
