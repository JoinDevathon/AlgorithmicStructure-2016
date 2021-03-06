package org.devathon.contest2016.minecraftsearch;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.devathon.contest2016.search.Element;
import org.devathon.contest2016.search.Vector2;

import java.util.function.Function;

public class InfoLoader {
	
	public Function<Block, Boolean> dotDefinition;
	public Function<Block, Boolean> passableDefinition;
	
	public InfoLoader(Function<Block, Boolean> dotDefinition, Function<Block, Boolean> passableDefinition){
		this.dotDefinition = dotDefinition;
		this.passableDefinition = passableDefinition;
	}
	
	public RectangleInfo loadRectInfoFromPositionInWorld(Location location){
		int countDots = 0;
		int prevDotCount = 0;
		
		int x_extension = 1;
		int y_extension = 1;
		
		do{
			prevDotCount = countDots;
			countDots = countDotsInRect(location, x_extension, y_extension);
			x_extension++;
			y_extension++;
		}while(countDots > prevDotCount);
		
		int side_length_x = x_extension*2 + 1;
		int side_length_y = y_extension*2 + 1;
		
		Location zeroZero = new Location(location.getWorld(), location.getBlockX() - side_length_x / 2, location.getBlockY(), location.getBlockZ() - side_length_y / 2);
		
		Location playerLocationDifference = location.clone().add(zeroZero.clone().multiply(-1));
		Vector2 locationPlayer = new Vector2(playerLocationDifference.getBlockX(), playerLocationDifference.getBlockZ());
		
		return new RectangleInfo(new Vector2(side_length_x, side_length_y), zeroZero, locationPlayer);
	}
	
	private int countDotsInRect(Location locationMiddle, int x_extension, int y_extension){
		int startingX = locationMiddle.getBlockX() - x_extension;
		int startingY = locationMiddle.getBlockZ() - y_extension;
		
		int dots = 0;
		
		for(int x = 0; x < x_extension * 2; x++){
			for(int y = 0; y < y_extension * 2; y++){
				int x_coor = startingX + x;
				int y_coor = startingY + y;	//TODO not working
				dots += hasDot(new Location(locationMiddle.getWorld(), x_coor, locationMiddle.getBlockY(), y_coor)) ? 1 : 0;
			}
		}
		
		return dots;
	}
	
	private boolean hasDot(Location loc){
		return dotDefinition.apply(loc.getBlock());
	}
	
	public MinecraftNode loadGoalFromView(Element[][] view){
		Element[][] finalView = new Element[view.length][view[0].length];
		
		for (int column = 0; column < view.length; column++) {
            for (int row = 0; row < view[0].length; row++) {
                if (view[column][row] == Element.DOT){
                	finalView[column][row] = Element.PASSABLE;
                }else{
                	finalView[column][row] = view[column][row];
                }
            }
        }
		
		return new MinecraftNode(finalView, null);
	}
	
	public Element[][] loadViewFromWorld(RectangleInfo info){
		int lengthX = info.getRectSideLengths().getX();
		int lengthY = info.getRectSideLengths().getY();
		
		Location locationZeroZero = info.getZeroZeroLocation();
		
		Element[][] view = new Element[lengthX][lengthY];
		for(int x = 0; x < lengthX; x++){
			for(int y = 0; y < lengthY; y++){
				Location cur_loc = locationZeroZero.clone().add(x, 0, y);
				if(dotDefinition.apply(cur_loc.getBlock())){
					view[x][y] = Element.DOT;
				}else if(passableDefinition.apply(cur_loc.getBlock())){
					view[x][y] = Element.PASSABLE;
				}else{
					view[x][y] = Element.NON_PASSABLE;
				}
			}
		}
		return view;		
	}
	
	public static class RectangleInfo{
		
		private Vector2 vec;
		private Location zeroZero;
		private Vector2 player;
		
		public RectangleInfo(Vector2 vec, Location zeroZero, Vector2 player){
			this.vec = vec;
			this.zeroZero = zeroZero;
			this.player = player;
		}
		
		public Vector2 getRectSideLengths(){
			return vec;
		}
		
		public Location getZeroZeroLocation(){
			return zeroZero;
		}
		
		public Vector2 getPlayerLocation(){
			return player;
		}
		
	}
	
}
