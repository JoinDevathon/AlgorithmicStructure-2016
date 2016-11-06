package org.devathon.contest2016;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConstants {

	public static final ItemStack WHEAT_MACHINE;
	public static final ItemStack GRASS_MACHINE;
	
	static{
		WHEAT_MACHINE = new ItemStack(Material.DAYLIGHT_DETECTOR);
		ItemMeta meta = WHEAT_MACHINE.getItemMeta();
		meta.setDisplayName("\u00a75Wheat Harvester");
		WHEAT_MACHINE.setItemMeta(meta);

		GRASS_MACHINE = new ItemStack(Material.DAYLIGHT_DETECTOR);
		ItemMeta meta2 = GRASS_MACHINE.getItemMeta();
		meta2.setDisplayName("\u00a77Grass Harvester");
		GRASS_MACHINE.setItemMeta(meta2);
	}
	
	public static boolean isMachine(ItemStack machine, ItemStack item){
		if(item == null)
			return false;
		
		if(item.hasItemMeta() && machine.hasItemMeta()){
			ItemMeta meta = item.getItemMeta();
			if(meta.getDisplayName().equals(machine.getItemMeta().getDisplayName())){
				return true;
			}
		}
		
		return false;
	}


}
