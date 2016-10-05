import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Classe que representa o mapa
 * @author Guilherme Simas
 *
 */
public class Mapa {
	/*
	 * Mapa é representado como uma matriz de char
	 */
	char mapa[][];
	private int x_I;
	private int y_I;
	private int x_F;
	private int y_F;	
	private static final int N_ROWS = 41;
	private static final int N_COLLUMNS = 41;
	
	/*
	 * Inicializa o mapa lendo o arquivo e preenchendo a matriz
	 */
	public Mapa(String name) throws FileNotFoundException{
		
		this.mapa = new char[N_ROWS][N_COLLUMNS];		
		Scanner s = new Scanner(new File(name));		
			
		for(int i =0; i<N_ROWS;i++){			
			String line = s.nextLine();
			for(int j=0;j<N_COLLUMNS;j++){
				mapa[i][j] = line.charAt(j);
				if(mapa[i][j]=='I'){
					x_I = j;
					y_I = i;
				} else if (mapa[i][j]=='F'){
					x_F = j;
					y_F = i;
				}				
			}
		}		
		s.close();
	}
	/**
	 * retorna o custo da casa com base no caracter em x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public int getCost (int x, int y){
		if(this.mapa[x][y] == '.' || this.mapa[x][y] == 'I' || this.mapa[x][y] == 'F')
			return 1;
		if(this.mapa[x][y] == 'G')
			return 5;
		if(this.mapa[x][y] == 'D')
			return 200;
		if(this.mapa[x][y] == 'C')
			return 150;
		
		return 0;
	}
	/*
	 * retorna o caracter em x,y
	 */
	public char getChar(int x, int y) {
		
		return this.mapa[y][x];
	}
	/**
	 * retorna o X inicial
	 * @return
	 */
	public int getInitialX() {
		
		return this.x_I;
	}
	/**
	 * retorna o Y inicial
	 * @return
	 */
	public int getInitialY() {
		
		return this.y_I;
	}
	/**
	 * retorna o X do final
	 * @return
	 */
	public int getFinalX() {
		
		return this.x_F;
	}
	/**
	 * retorna o Y do final
	 * @return
	 */
	public int getFinalY() {
		
		return this.y_F;
	}
	/**
	 * imprime a soluçao no console
	 * @param solution
	 */
	public void printSolution(ArrayList<Node> solution) {
		/*
		 * como após a busca o mapa é modificado para '@' na soluçao é
		 * possível imprimir no console
		 */
		for(Node s:solution){
			this.mapa[s.getY()][s.getX()] = '@';
		}
		for(int i=0;i<N_ROWS;i++){
			for(int j=0;j<N_COLLUMNS;j++){
				System.out.print(this.mapa[i][j]);
			}
			System.out.println();
		}
	}
	/**
	 * retorna o numero de fileiras
	 * @return
	 */
	public int getRows() {
		
		return N_ROWS;
	}
	/**
	 * retorna o numero de colunas
	 * @return
	 */
	public int getCollumns() {
		
		return N_COLLUMNS;
	}
}
