package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.intelligence.MinecraftAction;
import org.devathon.contest2016.intelligence.MinecraftNode;
import org.devathon.contest2016.intelligence.MinecraftSearch;
import org.devathon.contest2016.minecraftsearch.Loader;
import org.devathon.contest2016.minecraftsearch.Loader.RectangleInfo;
import org.devathon.contest2016.minecraftsearch.MinecraftPercept;
import org.devathon.contest2016.search.Element;

public class Machine extends BukkitRunnable {

	private ArmorStand stand;
	private MinecraftSearch search;
	
	public Machine(Location location){
		stand = location.getWorld().spawn(location, ArmorStand.class);
		stand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		findWay();
		this.runTaskTimer(DevathonPlugin.instance, 20, 20);
	}

	@Override
	public void run() {
		
		MinecraftAction action = search.nextAction();
		if(action != null){
			System.out.println("Doing: " + action.name());
			switch(action){
			case GO_EAST:
			case GO_NORTH:
			case GO_SOUTH:
			case GO_WEST:
				stand.teleport(stand.getLocation().add(action.getVector()));
				break;			
			}
		}
	}
	
	private void findWay(){
		RectangleInfo info = Loader.loadRectInfoFromPosition(stand.getLocation());
		Element[][] view = Loader.loadFromWorld(info.getZeroZeroLocation(), info.getRectSideLengths().getX(), info.getRectSideLengths().getY());
		
		printView(view);
		
		boolean hasDotBelowPlayer = Loader.dotDefinition.apply(stand.getLocation().getBlock());
		
		MinecraftPercept percept = new MinecraftPercept(hasDotBelowPlayer, view, info.getPlayerLocation());
		search = new MinecraftSearch(percept, Loader.loadGoalFromView(view));
		
		MinecraftNode node = search.start();
		
		if(node == null)
			Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Couldn't find a way :(");
		else
			Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Found a way! :)");
		
	}
	
	private void printView(Element[][] view){
		StringBuilder builder = new StringBuilder();
		for(int x = 0; x < view.length; x++){
			for(int y = 0; y < view[x].length; y++){
				builder.append(view[x][y].name().charAt(0));
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}
}
