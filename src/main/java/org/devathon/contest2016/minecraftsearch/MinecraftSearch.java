package org.devathon.contest2016.minecraftsearch;

import org.devathon.contest2016.search.Direction;
import org.devathon.contest2016.search.Element;
import org.devathon.contest2016.search.Node;
import org.devathon.contest2016.search.Search;

import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class MinecraftSearch extends Search{

	private MinecraftPercept mcPercept;
	private MinecraftNode goal;
	private Stack<MinecraftAction> mcActionList;

	public MinecraftSearch(MinecraftPercept mcPercept, MinecraftNode goal){
		this.mcPercept = mcPercept;
		this.goal = goal;
	}

	@Override
	public MinecraftNode start() {
		if(goal == null || mcPercept == null){
			throw new IllegalStateException();
		}

		MinecraftNode firstNode = new MinecraftNode(mcPercept.getView(), mcPercept.getPosition());

		insertNode(firstNode);

		while(!openList.isEmpty()){
			MinecraftNode expansionNode = (MinecraftNode) popOpenList();
			pushClosedList(expansionNode);

			if(expansionNode.isGoal(goal)){
				if(firstNode.isEqual(expansionNode)){
					return null;
				}

				calculateWay(expansionNode);
				return expansionNode;
			}else{
				expandNode(expansionNode);
			}
		}

		return null;
	}

	private void expandNode(MinecraftNode previousNode){
		calculateNextNode(Direction.NORTH, previousNode);
		calculateNextNode(Direction.EAST, previousNode);
		calculateNextNode(Direction.SOUTH, previousNode);
		calculateNextNode(Direction.WEST, previousNode);
	}

	private void calculateNextNode(Direction direction, MinecraftNode previousNode){
		try{
			MinecraftNode next = new MinecraftNode(previousNode, direction);
			rateNode(next);

			boolean isInClosedList = false;
			for(Node node : closedList){
				MinecraftNode mcNode = (MinecraftNode) node;
				if(next.isEqual(mcNode)){
					isInClosedList = true;
				}
			}

			int x = next.getPos().getX();
			int y = next.getPos().getY();
			if(previousNode.getView()[x][y] == Element.NON_PASSABLE || isInClosedList){
				next = null;
			}

			if(next != null){
				insertNode(next);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			//Out of given array / perception
		}
	}

	public MinecraftAction nextAction(){
		if(mcActionList == null){
			return null;
		}

		if(!mcActionList.isEmpty()){
			return mcActionList.pop();
		}else{
			return null;
		}
	}

	private int calculateWay(MinecraftNode goalNode) {
		if (goalNode == null)
			return -1;

		mcActionList = new Stack<MinecraftAction>();

		MinecraftNode previous = goalNode;

		while (previous.getPrevious() != null) {
			mcActionList.push(createAction(previous));
			previous = (MinecraftNode) previous.getPrevious();
		}

		return mcActionList.size();
	}

	private MinecraftAction createAction(MinecraftNode node) {
		switch (node.getDirection()) {
		case NORTH:
			return MinecraftAction.GO_NORTH;
		case EAST:
			return MinecraftAction.GO_EAST;
		case SOUTH:
			return MinecraftAction.GO_SOUTH;
		case WEST:
			return MinecraftAction.GO_WEST;
		default:
			return null;
		}
	}

	@Override	//Best first search
	public void insertNode(Node expansionNode) {
		MinecraftNode exp = (MinecraftNode) expansionNode;
		if(!closedList.contains(exp)){
			openList.add(exp);
			Collections.sort(openList, new SortByFinalValueAsc());
		}
	}

	private static class SortByFinalValueAsc implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			MinecraftNode node1 = (MinecraftNode) o1;
			MinecraftNode node2 = (MinecraftNode) o2;
			return Float.compare(node1.getRating().getFinalValue(), node2.getRating().getFinalValue());
		}

	}

	@Override	//Best first search
	public void rateNode(Node expansionNode) {
		MinecraftNode exp = (MinecraftNode) expansionNode;
		MinecraftRating rating = (MinecraftRating) exp.getRating();
		rating.setCountDots(countDots(exp));
		//rating.setPathCosts(countSteps(exp));		// Don't use A* lol, we want to finish it one day 
	}

	private int countDots(MinecraftNode node){
		int dots = 0;
		Element[][] view = node.getView();
		for(int x = 0; x < view.length; x++)
			for(int y = 0; y < view[x].length; y++)
				if(view[x][y] == Element.DOT)
					dots++;
		return dots;
	}

	// Used for A*
	private int countSteps(MinecraftNode node){
		int steps = 0;
		while(node != null){
			node = (MinecraftNode) node.getPrevious();
			steps++;
		}
		System.out.println("Steps " + steps);
		return steps > 0 ? steps - 1 : 0;
	}


}
