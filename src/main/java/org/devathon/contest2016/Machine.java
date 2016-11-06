package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.minecraftsearch.*;
import org.devathon.contest2016.minecraftsearch.InfoLoader.RectangleInfo;
import org.devathon.contest2016.search.Element;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class Machine extends BukkitRunnable {

	private ArmorStand stand;
	private MinecraftSearch search;
	private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

	private Function<Block, Boolean> dotDefinition, passableDefinition;
	private Consumer<Block> doWork;
	
	// Prevents the worker from doing work multiple times on the same block
	private boolean didWorkLastTime = false;

	// Tick cooldown between each execution (moving, working)
	private int tick_delay = 20;
	
	
	// dotDefinition: Returns true if the worker shall do his work on the tested block.
	// e.g. returns true if it's wheat, so it will harvest the wheat.

	// passableDefinition: Returns true if the worker can pass this block.
	// e.g. returns false if there is a solid block
	
	// doWork: Do whatever the worker should do on a "dot" 
	
	public Machine(Location location, Function<Block, Boolean> dotDefinition, Function<Block, Boolean> passableDefinition, Consumer<Block> doWork){
		stand = location.getWorld().spawn(location, ArmorStand.class);
		stand.setGravity(false);
		stand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		stand.setHelmet(new ItemStack(Material.IRON_HELMET));
		stand.setBoots(new ItemStack(Material.IRON_BOOTS));
		stand.setBoots(new ItemStack(Material.LEATHER_LEGGINGS));
		stand.setItemInHand(new ItemStack(Material.STONE_HOE));
		stand.setInvulnerable(true);
		
		this.doWork = doWork;
		this.passableDefinition = passableDefinition;
		this.dotDefinition = dotDefinition;
	}
	
	// Must be called before #start
	public void setTickDelay(int ticks){
		tick_delay = ticks;
	}
	
	public void start(){
		findWay();
	}
	
	public ArmorStand getArmorStand(){
		return stand;
	}

	@Override
	public void run() {
		if(stand.isDead()){
			this.cancel();
			return;
		}
		
		Location standLocation = stand.getLocation(); 
		
		if(!didWorkLastTime && dotDefinition.apply(standLocation.getBlock())){
			doWork.accept(standLocation.getBlock());
			didWorkLastTime = true;
		}else{
			MinecraftAction action = search.nextAction();
			if(action != null){
				switch(action){
				case GO_EAST:
				case GO_NORTH:
				case GO_SOUTH:
				case GO_WEST:
					Location newLocation = standLocation.add(action.getVector());
					newLocation.setDirection(action.getVector());
					stand.teleport(newLocation);
					break;			
				}
			}
			didWorkLastTime = false;
		}
	}

	private void findWay(){
		InfoLoader loader = new InfoLoader(dotDefinition, passableDefinition);
		
		//Expands a rectangle until the outter border does not contain any new "dots" 
		RectangleInfo info = loader.loadRectInfoFromPositionInWorld(stand.getLocation());
		
		Element[][] view = loader.loadViewFromWorld(info);

		//printView(view);

		boolean hasDotBelowPlayer = dotDefinition.apply(stand.getLocation().getBlock());

		MinecraftPercept percept = new MinecraftPercept(hasDotBelowPlayer, view, info.getPlayerLocation());
		MinecraftNode goalNode = loader.loadGoalFromView(view);
		
		search = new MinecraftSearch(percept, goalNode);

		service.schedule(new SearchExecutor(), 0, TimeUnit.SECONDS);
	}


	private void printView(Element[][] view){
		StringBuilder builder = new StringBuilder("\n");
		for(int x = 0; x < view.length; x++){
			for(int y = 0; y < view[x].length; y++){
				builder.append(view[x][y].name().charAt(0));
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}
	
	private class SearchExecutor implements Runnable{

		@Override
		public void run() {
			Bukkit.broadcastMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Searching ...");
			
			MinecraftNode node = search.start();

			if(node == null)
				Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Couldn't find a way :(");
			else
				Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Found a way! :)");
			
			Machine.this.runTaskTimer(DevathonPlugin.instance, 0, tick_delay);
		}
		
	}
}
