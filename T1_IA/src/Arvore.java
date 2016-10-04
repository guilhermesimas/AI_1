//Arvore Class

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Arvore {

	public Arvore(){
		
	}	
	
	
	
	public static ArrayList<Node> Solution(Node n){
		
		ArrayList<Node> solution = new ArrayList<Node>();
		Node current = n;
		
		while(current != null){
			
			solution.add(current);
			
			current = current.getParent();
			//System.out.println("tem pai :)");
		}
		
		return solution;
	}
	
	public static ArrayList<Node> BuscaLargura(char[][] map, Node start){
		
		Queue<Node> borders = new LinkedList<Node>();
		LinkedList<Node> visited = new LinkedList<Node>(){
			@Override
			public boolean contains(Object o){
				for(int i = 0; i<this.size(); i++){
					if(((Node)o).x == this.get(i).x && ((Node )o).y == this.get(i).y){
						return true;
					}
				}
				
				return false;
			}
		};
		
		Node current;
		
		borders.add(start);
		
		while(true){
			
			if(borders.isEmpty())
				return null;
			
			current = borders.remove();
			
			if(!visited.contains(current)){
				
				visited.add(current);
				
				if(map[current.x][current.y] == 'F'){
					System.out.println("coordenadas:" + current.x + ", y:" + current.y );
					return Solution(current);
				}
				
//				ExpandBorders(borders, map, current);
			}
			
		}
		
	}
	
}
