package org.devathon.contest2016.intelligence;

import java.util.Arrays;

import org.devathon.contest2016.search.Direction;
import org.devathon.contest2016.search.Element;
import org.devathon.contest2016.search.Vector2;

public class MinecraftNode extends Node{

    private Element[][] view;
    
    private String viewAsString = "";

    private Vector2 pos = new Vector2(0,0);

    private Direction direction = Direction.NORTH;

    public MinecraftNode(Element[][] view, Vector2 pos){
        super(new MinecraftRating());

        this.previous = null;
        this.pos = pos;

        this.view = new Element[view.length][view[0].length];
        this.view = copyArray(view);	//TODO

        if(this.pos != null){
            this.view[this.pos.getX()][this.pos.getY()] = Element.PASSABLE;
        }

        for(int i = 0; i <  this.view.length; i++){
            this.viewAsString = this.viewAsString.concat(Arrays.toString(this.view[i]));
        }
    }

    public MinecraftNode(MinecraftNode previous, Direction direction) {
        super(previous, new MinecraftRating());

        this.direction = direction;

        this.view = new Element[previous.getView().length][previous.getView()[0].length];
        this.view = copyArray(previous.getView());	//TODO

        this.pos = calculateNewPosition(previous.getPos(), direction);

        this.view[this.pos.getX()][this.pos.getY()] = Element.PASSABLE;

        for(int i = 0; i <  this.view.length; i++){
            this.viewAsString = this.viewAsString.concat(Arrays.toString(this.view[i]));
        }
    }

    public Element[][] getView() {
        return view;
    }

    public void setView(Element[][] view) {
        this.view = view;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Direction getDirection() {
        return direction;
    }

    private Element[][] copyArray(Element[][] view) {
        Element[][] newView = new Element[view.length][view[0].length];

        for (int column = 0; column < view.length; column++) {
            for (int row = 0; row < view[0].length; row++) {
                newView[column][row] = view[column][row];
            }
        }

        return newView;
    }

    @Override
    public boolean isEqual(Node knoten) {
        MinecraftNode comparable = (MinecraftNode) knoten;

        if(this.getPos().getX() == comparable.getPos().getX()){
            if(this.getPos().getY() == comparable.getPos().getY()){
                if (this.viewAsString.equals(comparable.viewAsString)){
                    return true;
                }
            }
        }
         return false;
    }
    
    @Override
    public boolean isGoal(Node node) {
        MinecraftNode comparable = (MinecraftNode) node;

        if (this.viewAsString.equals(comparable.viewAsString)){
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        String str = "";

        str += "\n";
        str += "[" + this.pos.getX()+"," + this.pos.getY() + "]\n";
        for (int x = 0; x < view[0].length; x++) {
            for (int y = 0; y < view.length; y++) {
                str += view[y][x] + " ";
            }
            str += "\n";
        }

        return str;
    }


    private Vector2 calculateNewPosition(Vector2 previousPos, Direction direction){
        Vector2 nextPos = new Vector2(-1, -1);
        
        switch (direction) {
            case NORTH:
                nextPos.set(previousPos.getX(), previousPos.getY() - 1);
                break;
            case EAST:
                nextPos.set(previousPos.getX() + 1, previousPos.getY());
                break;
            case SOUTH:
                nextPos.set(previousPos.getX(), previousPos.getY() + 1);
                break;
            case WEST:
                nextPos.set(previousPos.getX() - 1, previousPos.getY());
                break;

            default:
                break;
        }
        
        return nextPos;
    }
    
}
