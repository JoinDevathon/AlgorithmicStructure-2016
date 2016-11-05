package org.devathon.contest2016.search;

import java.util.LinkedList;
import java.util.List;

public abstract class Search {

	protected List<Node> openList = new LinkedList<>();
	protected List<Node> closedList = new LinkedList<>();
	
	public abstract Node start();
	
	public abstract void insertNode(Node expansionNode);
	
	public abstract void rateNode(Node expansionNode);
	
	protected Node popOpenList(){
		if(!openList.isEmpty()){
			return openList.remove(0);
		}
		
		return null;
	}
	
	protected void pushClosedList(Node expansionNode){
		closedList.add(expansionNode);
	}
	
}
