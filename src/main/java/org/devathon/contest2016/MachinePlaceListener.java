package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class MachinePlaceListener implements Listener{

	@EventHandler
	public void onPlaceMachine(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND){
			ItemStack stack = event.getPlayer().getItemInHand();

			boolean isGrassMachine = ItemConstants.isMachine(stack, ItemConstants.GRASS_MACHINE);
			boolean isWheatMachine = ItemConstants.isMachine(stack, ItemConstants.WHEAT_MACHINE);

			if(isGrassMachine || isWheatMachine){
				event.setCancelled(true);

				if(stack.getAmount() > 1)
					stack.setAmount(stack.getAmount() - 1);
				else
					event.getPlayer().setItemInHand(null);
				
				Location spawnLocation = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);


				if(isGrassMachine){
					Machine machine = new Machine(spawnLocation, new ExampleDefinitions.GrassHarvester.Dot(), new ExampleDefinitions.GrassHarvester.Passable(), new ExampleDefinitions.GrassHarvester.Work());
					machine.start();
				}else if(isWheatMachine){
					Machine machine = new Machine(spawnLocation, new ExampleDefinitions.WheatHarvester.Dot(), new ExampleDefinitions.WheatHarvester.Passable(), new ExampleDefinitions.WheatHarvester.Work());
					machine.start();
				}
			}
		}
	}
	
}
