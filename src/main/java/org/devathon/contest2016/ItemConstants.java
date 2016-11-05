package org.devathon.contest2016;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConstants {

	public static final ItemStack MACHINE_ITEM;
	
	static{
		MACHINE_ITEM = new ItemStack(Material.DAYLIGHT_DETECTOR);
		ItemMeta meta = MACHINE_ITEM.getItemMeta();
		meta.setDisplayName("§5Wheat Collector");
		MACHINE_ITEM.setItemMeta(meta);
	}
	
	public static boolean isMachine(ItemStack item){
		if(item == null)
			return false;
		
		if(item.hasItemMeta()){
			ItemMeta meta = item.getItemMeta();
			meta.getDisplayName().equals(MACHINE_ITEM.getItemMeta().getDisplayName());
			return true;
		}
		
		return false;
	}


}
