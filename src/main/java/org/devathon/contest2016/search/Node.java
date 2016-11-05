package org.devathon.contest2016.search;

public abstract class Node {

	protected Node previous;
	protected Rating rating;
	
	public Node(Rating rating){
		this.rating = rating;
	}
	
	public Node(Node previous, Rating rating){
		this.rating = rating;
		this.previous = previous;
	}
	
	public Node getPrevious(){
		return previous;
	}
	
	public Rating getRating(){
		return rating;
	}
	
	public void setRating(Rating rating){
		this.rating = rating;
	}
	
	public abstract boolean isEqual(Node node);
	
	public abstract boolean isGoal(Node node);
	
}
