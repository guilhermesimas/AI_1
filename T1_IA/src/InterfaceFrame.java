import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.JTextComponent;
/**
 * Classe que erepresenta a interface
 * @author Guilherme Simas
 *
 */
public class InterfaceFrame {
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 1000;
	private static final String TITLE = "IA - T1";
	private static final String BUSCA_BUTTON_TITLE = "Run Busca";
	protected static final String fileName = "IA_2016.2_Trabalho_1_Mapa.txt";
	private JFrame frame;
	private JPanel panel;
	protected float totalCostPath;
	protected float totalCost;
	private static final String SOLUTION_BUTTON_TITLE = "Run Solution";
	private static final String COST_TEXT = "Cost: ";
	private static int N_ROWS = 10;
	private static int N_COLLUMNS = 5;
	private static int UPPER_SPAWN_LIMIT = 1;
	private static int CLAREIRA_COST[] = {150,140,130,120,110,100,95,90,85,80};
	private static double CANDY_FACTOR[] = {1.5,1.4,1.3,1.2,1.1};
	private static final int MAX_COST = 1100;
	
	public InterfaceFrame(){
		this.frame = new JFrame();
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setTitle(TITLE);
		
	}
	/**
	 * Funçao que cria o frame da interface 
	 */
	public void initialize(){
		panel = new JPanel();
		panel.setBounds(new Rectangle(WIDTH, HEIGHT));
		panel.setLayout(new BorderLayout());
		this.frame.add(panel);
		/*
		 * A interface tem acesso ao mapa e à busca
		 */
		
		Mapa mapa;
		Busca busca = new Busca();
		try {
			mapa = new Mapa(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Filename \""+fileName+"\" was not found");
			e.printStackTrace();
			return;
		}
		int x_I = mapa.getInitialX();
		int y_I = mapa.getInitialY();
		int x_F = mapa.getFinalX();
		int y_F = mapa.getFinalY();
		
		/*
		 * Apos inicializar o frame e as variaveis, inicializa o panel que  mostra
		 * o mapa.
		 */
		MapPanel mapPanel = new MapPanel();
		mapPanel.setMapa(mapa);
		mapPanel.setBusca(busca);
		mapPanel.setBackground(new Color(0x70C0A0));		
		panel.add(mapPanel,BorderLayout.CENTER);
	
		/*
		 * Cria o botao que executa a busca e o	que executa a animaçao da soluçao	
		 */
		
		JPanel ButtonPanel = new JPanel();
		panel.add(ButtonPanel,BorderLayout.EAST);		
		ButtonPanel.setLayout(new GridLayout(5,1,10,10));
		
		JButton solutionButton = new JButton(SOLUTION_BUTTON_TITLE);
		solutionButton.setEnabled(false);		
		solutionButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mapPanel.printSolution();
			}
		});
		ButtonPanel.add(solutionButton);
				
		JLabel costpathText = new JLabel(COST_TEXT);
		JLabel costClareira = new JLabel(COST_TEXT);
		System.out.println("Total Cost: "+ totalCostPath);
		costClareira.setText("Total Cost of Clareiras: " + totalCost);
		ButtonPanel.add(costClareira);
		
		/*
		 * O botao que realiza a busca quando apertado chama a busca e uma Thread
		 * para imprimir o progresso da busca continuamente. No final imprime a soluçao
		 * e imprime o custo.
		 */
		
		JButton buscaButton = new JButton(BUSCA_BUTTON_TITLE);		
		buscaButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Thread t1 = new Thread(mapPanel);
				t1.start();
				ArrayList<Node>solution = Busca.findPath(mapa, x_I, y_I, x_F, y_F);
				t1.stop();
				mapPanel.printPathToNode(busca.getCurrentBest(), mapPanel.getGraphics());
				solutionButton.setEnabled(true);
				totalCostPath = busca.getCurrentBest().getCost();
				totalCostPath =  totalCostPath - MAX_COST + totalCost;
				costpathText.setText(COST_TEXT+totalCostPath);
				ButtonPanel.add(costpathText);
				System.out.println("Total Cost: "+ totalCostPath);
			}
			
		});
		ButtonPanel.add(buscaButton);
		
		/*
		 * Calcula a combinatoria e imprime os doces na clareira	
		 */
		
		int[][]combinatoria = Combinatoria.run();
		
		totalCost=0;
		for(int i=0;i<N_ROWS;i++){
			float candyFactor = 0;
			for(int j=0;j<N_COLLUMNS;j++){
				candyFactor += CANDY_FACTOR[j]*combinatoria[i][j];
			}
			if(candyFactor==0){
				candyFactor = 1;
			}
			totalCost+=CLAREIRA_COST[i]/candyFactor;
		}
		
		
		
		
		CandyPanel cp = new CandyPanel(combinatoria);
		panel.add(cp,BorderLayout.WEST);
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}
	
}
