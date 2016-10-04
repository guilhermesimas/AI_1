import java.util.ArrayList;
import java.util.Random;

public class Combinatoria {
	static ArrayList<X> population;

	private static final int N_INDIVIDUALS = 100;

	private static final int N_ITERATION = 10000;
	
	private static final int MAX_COST = 1100;

	private static double INVALID_PENALTY = 10;

	private static double MUTATION_FACTOR = 0.5;

	private static int N_ELITE = 20;
	
	public Combinatoria(){
	}
	
	/**
	 * Removes the element drawn
	 * @return
	 */
	public static X draw(){
		
		double total_cost=0;
		double probability[] = new double[population.size()];
		
		for(int i=0;i<population.size();i++){
			X x = population.get(i);
			if(x.isValid())
				total_cost+= 1/x.getValue();
			else
				total_cost+= 1/(x.getValue()*INVALID_PENALTY);
		}
		
		for(int i=0;i<population.size();i++){
			X x = population.get(i);
			if(x.isValid())
				probability[i] = (1/x.getValue())/total_cost;
			else
				probability[i] = (1/(x.getValue()*INVALID_PENALTY))/total_cost;
		}
		
		Random r = new Random();
		
		double draw_value = r.nextDouble();
		double current_value = 0;
		int i=0;
		while(current_value<draw_value){
			current_value += probability[i];
			i++;
		}
		
		
		return population.get(i-1);
	}
	
	public static int[][] run(){
		int iteration=0;
		population = new ArrayList();
		
		for(int i=0; i<N_INDIVIDUALS; i++){
			X x = new X();
			x.mutate();
			x.avaliate();
			population.add(x);
		}
		
		while(iteration<N_ITERATION){
			
			double factor =  ((double)iteration)/N_ITERATION;
			MUTATION_FACTOR = 0.5 +0.5*factor;
			N_ELITE = (int)(20 *factor);
			//INVALID_PENALTY = 10 * factor;
			ArrayList<X> elite = new ArrayList<X>();
			
			population.sort(null);
			
			for(int i=0; i<N_ELITE; i++){
				
				elite.add(population.get(population.size()-i-1));
				
			}
			
			ArrayList<X> children = new ArrayList<X>(); 
			
			for(int i=0;i<N_INDIVIDUALS/2;i++){
				
				X p1 = draw();
				X p2 = draw();
				
				population.add(p1);
				population.add(p2);
				
				children.addAll(p1.recombine(p2));
				
			}
			
			
			ArrayList<X> copy_elite = new ArrayList<X>(N_ELITE);
			
			for(X e:elite){
				copy_elite.add(e.getCopy());
			}
			
			children.addAll(elite);
			
			for(X c:children){
				Random r = new Random();
				if(r.nextDouble()<MUTATION_FACTOR){
					c.mutate();
					c.avaliate();
				}
			}
			
			children.addAll(copy_elite);
			
			children.sort(null);
			
			for(int i=0; i<2*N_ELITE; i++){
				children.remove(0);
			}
			
			
					
			population = children;
			iteration++;
			
			System.out.print("Value: "+population.get(population.size()-1).getValue()+"\t"+(((double)iteration)/N_ITERATION)*100+"%");
			if(population.get(population.size()-1).isValid())
				System.out.println("\tVALID!!");
			else
				System.out.println();
			
			
			
		}
		
		population.sort(null);
		//for(int i=0;i<N_INDIVIDUALS;i++){
		X x = population.get(population.size()-1);
		x.print();
		//}
		return x.matrix;
	}
	
	
}
