package org.devathon.contest2016;

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
			if(ItemConstants.isMachine(stack)){
				event.setCancelled(true);
				new Machine(event.getClickedBlock().getLocation().add(0.5, 1, 0.5));
			}
		}
	}
	
}
