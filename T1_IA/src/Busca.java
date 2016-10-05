import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 * Essa classe realiza a busca A* com base no mapa que lhe é fornecido.
 * @author Guilherme Simas
 *
 */
public class Busca{
	/* Variaveis usadas para controle da busca. 
	Fronteiras, o nó mais recente com o menor custo, No inicial e no Final*/
	private static ArrayList<Node> borders;
	private static Node currentBest;
	private static Node I;
	private static Node F;
	/* Constantes que marcam o numero de colunas e fileiras do mapa recebido*/
	private static final int VERTICAL_BOUND = 41;
	private static final int HORIZONTAL_BOUND = 41;
	/*
	 * Matriz ultilizada para controlar o menor custo para chegar em cada casa do mapa
	 */
	private static int visitedCost[][];
	/*
	 * Custos de cada clareira
	 */
	private static final int[] clareiraCost = {150,140,120,130,120,110,100,95,90,85,80};
	
	/**
	 * Função que recebe um Mapa, as coordenadas do no Inicio e no Fim e retorna uma lista de nos
	 * do meno caminho do inicio ao fim
	 * @param map
	 * @param x_I
	 * @param y_I
	 * @param x_F
	 * @param y_F
	 * @return
	 */
	
	public static ArrayList<Node> findPath (Mapa map,int x_I,int y_I, int x_F, int y_F){
		visitedCost  = new int[VERTICAL_BOUND][HORIZONTAL_BOUND];
		/*
		 * Inicializa os dados
		 */
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
		/*
		 * Loop da busca. Será rodado enquanto o final nao for encontrado
		 */
		while(!found){
			/*
			 * Encontra a melhor fronteira para expandir (menor custo)
			 */
			currentBest=borders.get(0);
			int minCost = borders.get(0).getCost()+calc_heuristica(currentBest.getX(),currentBest.getY());
			/*
			 * Se a fronteira for o nó final, retorne ela
			 */
			for(Node n:borders){
				if(Busca.isFinal(n,map)){
				
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
			/*
			 * Expande o no de menor custo
			 */
			expand(currentBest,map);
			/*
			 * Pequeno delay para motivos de animação na interface
			 */
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		
		}
		return null;
	}
	/**
	 * Expande os quatro nós adjacentes ao Nó n e inclui eles na fronteira se pertinente
	 * @param n
	 * @param map
	 */
	private static void expand(Node n, Mapa map){
		Node child;
		int x = n.getX();
		int y = n.getY();
		/*
		 * Checa se o custo do No é o menor do que o do menor no que chegou àquela casa.
		 * Se não for, não expande as fronteiras do nó.
		 */
		if(visitedCost[x][y]==-1){
			visitedCost[x][y] = n.getCost();
		}else if(visitedCost[x][y]<=n.getCost()){
			return;
		} else {
			visitedCost[x][y] = n.getCost();
		}
		/*
		 * Para todos os nós adjacentes, cria o nó e inicialzia ele com o custo atualizado
		 * e insere na fronteira
		 */
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
	/**
	 * Checa se o Nó passado é o nó final
	 * @param n
	 * @param mapa
	 * @return
	 */
	private static boolean isFinal(Node n,Mapa mapa) {
		
		return mapa.getChar(n.getX(), n.getY())=='F';
	}
	/**
	 * Calcula a distancia euclidiana do ponto das coordenadas passadas
	 * até o nó final
	 * @param x
	 * @param y
	 * @return
	 */
	private static int calc_heuristica(int x, int y){
		
		return (int)Math.sqrt(Math.abs(((x-F.getX())^2)+((y-F.getY())^2)));
		

	}
	/**
	 * Retorna o custo do nó com base no char representativo no mapa
	 * @param mapa
	 * @param n
	 * @return
	 */
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
	/**
	 * Retorna a lista de nós do nó passado até a raiz
	 * @param n
	 * @return
	 */
	private static ArrayList<Node> Solution(Node n){
		
		ArrayList<Node> solution = new ArrayList<Node>();
		Node current = n;
		
		while(current != null){
			
			solution.add(current);
			
			current = current.getParent();
		}
		
		return solution;
	}
	/**
	 * Retorna o numero de antepassados do nó que são clareiras.
	 * @param n
	 * @param mapa
	 * @return
	 */
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
	
		return this.visitedCost;
	}

	public Node getCurrentBest() {
	
		return this.currentBest;
	}

	public ArrayList<Node> getBorders() {
	
		return this.borders;
	}
}
