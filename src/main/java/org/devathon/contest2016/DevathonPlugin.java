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
    	loadRecipes();
    	Bukkit.getPluginManager().registerEvents(new MachinePlaceListener(), this);
    }
    
    private void loadRecipes(){
		ShapedRecipe recipe = new ShapedRecipe(ItemConstants.WHEAT_MACHINE);
		recipe.shape("*E*","EDE","*R*");
		recipe.setIngredient('E', Material.EXP_BOTTLE);
		recipe.setIngredient('D', Material.DAYLIGHT_DETECTOR);
		recipe.setIngredient('R', Material.REDSTONE);
		getServer().addRecipe(recipe);

		recipe = new ShapedRecipe(ItemConstants.GRASS_MACHINE);
		recipe.shape("*G*","GDG","*G*");
		recipe.setIngredient('G', Material.SEEDS);
		recipe.setIngredient('D', Material.DAYLIGHT_DETECTOR);
		getServer().addRecipe(recipe);
    }
}

