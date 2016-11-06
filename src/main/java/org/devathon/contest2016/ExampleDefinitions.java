package org.devathon.contest2016;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExampleDefinitions {

	public static class GrassHarvester{

		public static class Passable implements Function<Block, Boolean>{

			@Override
			public Boolean apply(Block block){
				boolean canPassBlock = !block.getType().isSolid();
				canPassBlock = canPassBlock && !block.getLocation().add(0, 1, 0).getBlock().getType().isSolid();

				return canPassBlock;
			}

		}

		public static class Dot implements Function<Block, Boolean>{

			@Override
			public Boolean apply(Block block){
				if(block.getType() == Material.LONG_GRASS){
					return true;
				}
				return false;
			}

		}

		public static class Work implements Consumer<Block>{

			@Override
			public void accept(Block block){
				block.breakNaturally();
				block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GRASS_BREAK, 1.0f, 1.0f);
			}

		}

	}


	public static class WheatHarvester{

		public static class Passable implements Function<Block, Boolean>{

			@Override
			public Boolean apply(Block block){
				boolean canPassBlock = !block.getType().isSolid();
				canPassBlock = canPassBlock && !block.getLocation().add(0, 1, 0).getBlock().getType().isSolid();

				return canPassBlock;
			}

		}

		public static class Dot implements Function<Block, Boolean>{

			@Override
			public Boolean apply(Block block){
				if(block.getType() == Material.CROPS && block.getData() == 7){
					return true;
				}
				return false;
			}

		}

		public static class Work implements Consumer<Block>{

			@Override
			public void accept(Block block){
				block.breakNaturally();
			}

		}

	}


}
