import java.util.ArrayList;
import java.util.Random;

public class X implements Comparable{
	
	int matrix[][];
	private double value;
	int generation;
	
	private static int N_ROWS = 10;
	private static int N_COLLUMNS = 5;
	private static int UPPER_SPAWN_LIMIT = 1;
	private static int CLAREIRA_COST[] = {150,140,130,120,110,100,95,90,85,80};
	private static double CANDY_FACTOR[] = {1.5,1.4,1.3,1.2,1.1};
	private static int N_CANDY = 5;
	private static int N_TYPE_CANDY = 5;
	
	private static final int LOWER_BOUND = 0;
	
	
	public X(){
		this.matrix = new int[N_ROWS][N_COLLUMNS];
		Random r = new Random();
		this.generation=1;
		do{
			for(int i=0;i<N_ROWS;i++){
				for(int j=0;j<N_COLLUMNS;j++){
					this.matrix[i][j] = r.nextInt(UPPER_SPAWN_LIMIT+1);
				}
			}
		} while(!this.isValid());
		this.avaliate();
	}
	
	public X (int generation){
		this.matrix = new int[N_ROWS][N_COLLUMNS];
		this.generation = generation;
	}
	
	public double getValue(){
		
		return this.value;
	}
	public boolean isValid(){
		
		int nCandy[] = new int[N_TYPE_CANDY];
		int nTotalCandy=0;
		for(int i=0; i<N_TYPE_CANDY;i++){
			nCandy[i] = 0;
		}
		for(int j=0;j<N_COLLUMNS;j++){
			for(int i=0;i<N_ROWS;i++){
				nTotalCandy+=this.matrix[i][j];
				nCandy[j]+=this.matrix[i][j];
			}
			if(nCandy[j]>N_CANDY){
				return false;
			}
		}
		if(nTotalCandy>=N_TYPE_CANDY*N_CANDY){
			return false;
		}
		return true;
	}
	public void avaliate(){
		
		float totalCost=0;
		for(int i=0;i<N_ROWS;i++){
			float candyFactor = 0;
			for(int j=0;j<N_COLLUMNS;j++){
				candyFactor += CANDY_FACTOR[j]*this.matrix[i][j];
			}
			if(candyFactor==0){
				candyFactor = 1;
			}
			totalCost+=CLAREIRA_COST[i]/candyFactor;
		}
		this.value = totalCost;		
	}
	
	public ArrayList<X> recombine(X x){
		Random r = new Random();
		if(r.nextBoolean()){
			return recombine_1ponto_corte(x);
		} else {
			return recombine_uniforme(x);
		}
	}
	
	
	/**
	 * Generates and evaluates 2 children
	 * @param x 
	 * @return
	 */
	
	public ArrayList<X> recombine_1ponto_corte(X x){
		
		X f1 = new X(this.generation+1);
		X f2 = new X(this.generation+1);
		
		f1.initialize();
		f2.initialize();		
		
		Random r = new Random();
		int n_linhas = r.nextInt(N_ROWS-5)+5;
		
		for(int i=0;i<n_linhas;i++){
			int pos_linha = r.nextInt(N_ROWS);
			int pos_corte = r.nextInt(N_COLLUMNS);
			
			for(int j=0; j<pos_corte ; j++ ){

				f1.matrix[pos_linha][j] = this.matrix[pos_linha][j];
				f2.matrix[pos_linha][j] = x.matrix[pos_linha][j];
				
			}
			
			for(int j=pos_corte; j<N_COLLUMNS ; j++ ){
				
				f2.matrix[pos_linha][j] = this.matrix[pos_linha][j];
				f1.matrix[pos_linha][j] = x.matrix[pos_linha][j];
				
			}
			
		}
		
		for(int i=0;i<N_ROWS;i++){
			if(f1.matrix[i][0] == -1){
				for(int j=0; j<N_COLLUMNS ; j++ ){		
					f1.matrix[i][j] = this.matrix[i][j];
					f2.matrix[i][j] = x.matrix[i][j];
		
				}
			}
		}
		
		int linha1 = r.nextInt(N_ROWS);
		int linha2 = r.nextInt(N_ROWS);
		
		for(int j=0; j<N_COLLUMNS ; j++ ){
			int temp = f1.matrix[linha1][j];
			f1.matrix[linha1][j] = this.matrix[linha2][j];
			f1.matrix[linha2][j] = temp;
		}

		ArrayList<X> children = new ArrayList<X>();
		//f1.avaliate();
		//f2.avaliate();
		children.add(f1);
		children.add(f2);
		return children;
	}
	
public ArrayList<X> recombine_uniforme(X x){
		
		
		X f1 = new X(this.generation+1);
		X f2 = new X(this.generation+1);
		
		f1.initialize();
		f2.initialize();		
		
		Random r = new Random();
		int n_linhas = r.nextInt(N_ROWS);
		
		for(int i=0;i<n_linhas;i++){
			int pos_linha = r.nextInt(n_linhas);
			for(int j=0; j<N_COLLUMNS ; j++ ){
				boolean flag = r.nextBoolean();
				
				if(flag){
					f1.matrix[pos_linha][j] = this.matrix[pos_linha][j];
					f2.matrix[pos_linha][j] = x.matrix[pos_linha][j];
				}
				else{
					f2.matrix[pos_linha][j] = this.matrix[pos_linha][j];
					f1.matrix[pos_linha][j] = x.matrix[pos_linha][j];
				}
			}
		}
		
		for(int i=0;i<N_ROWS;i++){
			if(f1.matrix[i][0] == -1){
				for(int j=0; j<N_COLLUMNS ; j++ ){		
					f1.matrix[i][j] = this.matrix[i][j];
					f2.matrix[i][j] = x.matrix[i][j];
		
				}
			}
		}
		
		ArrayList<X> children = new ArrayList<X>();
		//f1.avaliate();
		//f2.avaliate();
		children.add(f1);
		children.add(f2);
		return children;
	}

	public void mutate (){
		Random r = new Random();
		if(r.nextBoolean()){
			this.mutate_swap_line();
		} else {
			this.mutate_1bit();
		}
	}
	
	public void mutate_swap_line(){
		Random r = new Random();
		int i1 = r.nextInt(N_ROWS);
		int i2 = i1;
		//Until it finds a different line
		while(i2==i1){
			i2 = r.nextInt(N_ROWS);
		}
		for(int j=0;j<N_COLLUMNS;j++){
			int temp = this.matrix[i1][j];
			this.matrix[i1][j] = this.matrix[i2][j];
			this.matrix[i2][j]=temp;
		}
		
	}

	public void mutate_1bit(){
		/*TODO: implement
		 * 
		 */
		
		Random r = new Random();
		int i = r.nextInt(N_ROWS);
		int j = r.nextInt(N_COLLUMNS);
		
		if(this.matrix[i][j] == 0)
			this.matrix[i][j] = 1;
		else
			this.matrix[i][j] = 0;
		
//		if(!this.isValid()){
//			if(this.matrix[i][j] == 0)
//				this.matrix[i][j] = 1;
//			else
//				this.matrix[i][j] = 0;
//		}
				
	
//		Random r = new Random();
//		int i1 = r.nextInt(N_ROWS);
//		int i2 = r.nextInt(N_ROWS);
//		int j = r.nextInt(N_COLLUMNS);
//		
//		if(this.matrix[i1][j] != this.matrix[i2][j]){
//			int temp = this.matrix[i1][j];
//			this.matrix[i1][j] = this.matrix[i2][j];
//			this.matrix[i2][j] = temp;
//		}
//		
//		for(int i = 0; i<N_ROWS;i++){
//			if(this.matrix[i1][j] != this.matrix[(i2+i)%N_ROWS][j]){
//				int temp = this.matrix[i1][j];
//				this.matrix[i1][j] = this.matrix[(i2+i)%N_ROWS][j];
//				this.matrix[(i2+i)%N_ROWS][j] = temp;
//			}
//		}
		
	}
	
	void initialize(){
		for(int i=0;i<N_ROWS;i++){
			for(int j=0;j<N_COLLUMNS;j++){
				this.matrix[i][j] = -1;
			}
		}
	}
	
	public void print (){
		for(int i=0;i<N_ROWS;i++){
			for(int j=0;j<N_COLLUMNS;j++){
				System.out.print("\'"+this.matrix[i][j]+"\' "); 
			}
			System.out.println();
		}
		if(!this.isValid()){
			System.out.println("NOT VALID");
		}
		System.out.println("Value: "+this.value);
		System.out.println("Gen: "+this.generation);
		
	}
	
	public X getCopy(){
		X x = new X(this.generation);
		
		for(int i=0;i<N_ROWS;i++){
			for(int j=0;j<N_COLLUMNS;j++){
				x.matrix[i][j] = this.matrix[i][j];
			}
		}
		
		x.value = this.value;
		
		return x;
	}

	@Override
	public int compareTo(Object arg0) {
		X x = (X)arg0;
		this.avaliate();
		x.avaliate();
		if(this.isValid() && !x.isValid()){
			return 1;
		}
		if(!this.isValid() && x.isValid()){
			return -1;
		}
		
		if(this.getValue()>x.getValue()){
			return -1;
		}
		if(this.getValue()<x.getValue()){
			return 1;
		}
		return 0;
	}
}
