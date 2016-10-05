

import java.util.ArrayList;

public class Node {

	public int x;
	public int y;
	
	private int cost;
	
	private Node parent;
	ArrayList<Node> children;
	
	public Node(int x, int y, Node parent, int cost){
		
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.cost = cost;
		 
	}
	
	public Node getParent(){
		return parent;
	}
		
	public int getCost(){
		
		return this.cost;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public boolean isDescendantOf(int x,int y){
		Node parent = this.parent;
		while(parent!=null){
			if(parent.getX()==x && parent.getY()==y){
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

	public void setCost(int cost) {
		this.cost=cost;
	}
	
}
