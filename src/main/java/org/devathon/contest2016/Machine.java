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
import org.devathon.contest2016.search.Vector2;

public class Machine extends BukkitRunnable {

	private ArmorStand stand;
	private MinecraftSearch search;
	
	public Machine(Location location){
		stand = location.getWorld().spawn(location, ArmorStand.class);
		stand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		findWay();
		this.runTaskTimer(DevathonPlugin.instance, 1, 1);
	}

	@Override
	public void run() {
		MinecraftAction action = search.nextAction();
		
	}
	
	private void findWay(){
		RectangleInfo info = Loader.loadRectInfoFromPosition(stand.getLocation());
		Element[][] view = Loader.loadFromWorld(info.getZeroZeroLocation(), info.getRectSideLengths().getX(), info.getRectSideLengths().getY());
		
		boolean hasDotBelowPlayer = Loader.goalDefinition.apply(stand.getLocation().getBlock());
		Vector2 vec = new Vector2(stand.getLocation().getBlockX(), stand.getLocation().getBlockZ());
		
		MinecraftPercept percept = new MinecraftPercept(hasDotBelowPlayer, view, vec);
		MinecraftSearch search = new MinecraftSearch(percept, Loader.loadGoalFromView(view));
		
		MinecraftNode node = search.start();
		
		if(node == null)
			Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Couldn't find a way :(");
		else
			Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Found a way! :)");
		
	}
}
