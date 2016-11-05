package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {

	public static JavaPlugin instance;
	
	public DevathonPlugin(){
		instance = this;
	}
	
    @Override
    public void onEnable() {
    	loadRecipe();
    	Bukkit.getPluginManager().registerEvents(new MachinePlaceListener(), this);
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }
    
    private void loadRecipe(){
    	ShapedRecipe recipe = new ShapedRecipe(ItemConstants.MACHINE_ITEM);
    	recipe.shape("*E*","EDE","*R*");
    	recipe.setIngredient('E', Material.EXP_BOTTLE);
    	recipe.setIngredient('D', Material.DAYLIGHT_DETECTOR);
    	recipe.setIngredient('R', Material.REDSTONE);
    	getServer().addRecipe(recipe);
    }
}

