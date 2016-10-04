import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Busca{
	private static ArrayList<Node> borders;
	private static Node currentBest;
	private static Node I;
	private static Node F;
	private static final int VERTICAL_BOUND = 41;
	private static final int HORIZONTAL_BOUND = 41;
	private static int visitedCost[][];
	private static final int[] clareiraCost = {150,140,120,130,120,110,100,95,90,85,80};
	
	
	
	public static ArrayList<Node> findPath (Mapa map,int x_I,int y_I, int x_F, int y_F){
		visitedCost  = new int[VERTICAL_BOUND][HORIZONTAL_BOUND];
		
		for(int i =0; i<VERTICAL_BOUND;i++){
			for(int j=0; j<HORIZONTAL_BOUND ; j++){
				visitedCost[i][j] = -1;
			}
		}
		I = new Node(x_I,y_I,null,0);
		F = new Node(x_F,y_F,null,0);
		borders = new ArrayList<>();
		borders.add(I);
		boolean found = false;
		while(!found){
			currentBest=borders.get(0);
			int minCost = borders.get(0).getCost()+calc_heuristica(currentBest.getX(),currentBest.getY());
			for(Node n:borders){
				if(Busca.isFinal(n,map)){
					//ACHOOOOOOOOO‘‘‘‘‘‘!!!!111!!!um!
					currentBest = n;
					return Solution(n);
				}
				if(n.getCost() + calc_heuristica(n.getX(),n.getY()) 
					< minCost){
					minCost = n.getCost()+calc_heuristica(n.getX(),n.getY());
					currentBest = n;
				}
				
			}
			
			borders.remove(currentBest);
//			System.out.println("Expanded x:"+currentBest.getX()+" y:"+currentBest.getY()+" cost:"+minCost);
			expand(currentBest,map);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return null;
	}
	
	private static void expand(Node n, Mapa map){
		Node child;
		int x = n.getX();
		int y = n.getY();
		
		if(visitedCost[x][y]==-1){
			visitedCost[x][y] = n.getCost();
		}else if(visitedCost[x][y]<=n.getCost()){
			return;
		} else {
			visitedCost[x][y] = n.getCost();
		}
		
		if(x+1<HORIZONTAL_BOUND){
			child = new Node(x+1,y,n,0);
			int cost = n.getCost() 
						+ getCost(map,child);
			child.setCost(cost);
			borders.add(child);
		}
		if(x-1>=0){
			child = new Node(x-1,y,n,0);
			int cost = n.getCost() 
						+ getCost(map,child);
			child.setCost(cost);
			borders.add(child);
		}
		if(y+1<VERTICAL_BOUND){
			child = new Node(x,y+1,n,0);
			int cost = n.getCost() 
						+ getCost(map,child);
			child.setCost(cost);
			borders.add(child);
		}
		if(y-1>=0){
			child = new Node(x,y-1,n,0);
			int cost = n.getCost() 
						+ getCost(map,child);
			child.setCost(cost);
			borders.add(child);
		}
	}
	private static boolean isFinal(Node n,Mapa mapa) {
		// TODO Auto-generated method stub
		return mapa.getChar(n.getX(), n.getY())=='F';
	}
	
	private static int calc_heuristica(int x, int y){
		
		return (int)Math.sqrt(Math.abs(((x-F.getX())^2)+((y-F.getY())^2)));
		
//		return Math.abs(x - F.getX()) + Math.abs(y - F.getY());
	}

	public static int getCost (Mapa mapa,Node n){
		char c = mapa.getChar(n.getX(),n.getY());
		if(c == '.' || c == 'I' || c == 'F')
			return 1;
		if(c == 'G')
			return 5;
		if(c == 'D')
			return 200;
		if(c == 'C')
			return clareiraCost[getNClareira(n,mapa)];
		
		return 0;
	}
	
	private static ArrayList<Node> Solution(Node n){
		
		ArrayList<Node> solution = new ArrayList<Node>();
		Node current = n;
		
		while(current != null){
			
			solution.add(current);
			
			current = current.getParent();
		}
		
		return solution;
	}
	private static int getNClareira(Node n,Mapa mapa){
		Node parent = n.getParent();
		int q=0;
		while(parent!=null){
			if(mapa.getChar(parent.getX(), parent.getY())=='C'){
				q++;
			}
			parent = parent.getParent();
		}
		return q;
	}

	public int[][] getVisited() {
		// TODO Auto-generated method stub
		return this.visitedCost;
	}

	public Node getCurrentBest() {
		// TODO Auto-generated method stub
		return this.currentBest;
	}

	public ArrayList<Node> getBorders() {
		// TODO Auto-generated method stub
		return this.borders;
	}
}
