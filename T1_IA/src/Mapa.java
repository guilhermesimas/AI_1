import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mapa {

	char mapa[][];

	private int x_I;

	private int y_I;

	private int x_F;

	private int y_F;
	
	private static final int N_ROWS = 41;
	private static final int N_COLLUMNS = 41;
	
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

	public char getChar(int x, int y) {
		// TODO Auto-generated method stub
		
		return this.mapa[y][x];
	}

	public int getInitialX() {
		// TODO Auto-generated method stub
		return this.x_I;
	}

	public int getInitialY() {
		// TODO Auto-generated method stub
		return this.y_I;
	}

	public int getFinalX() {
		// TODO Auto-generated method stub
		return this.x_F;
	}

	public int getFinalY() {
		// TODO Auto-generated method stub
		return this.y_F;
	}

	public void printSolution(ArrayList<Node> solution) {
		// TODO Auto-generated method stub
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

	public int getRows() {
		// TODO Auto-generated method stub
		return N_ROWS;
	}

	public int getCollumns() {
		// TODO Auto-generated method stub
		return N_COLLUMNS;
	}
}
