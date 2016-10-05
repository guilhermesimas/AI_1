import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Classe que representa o panel que mostra os doces e a sua distribuiçao
 * na combinatoria
 * @author Guilherme Simas
 *
 */
public class CandyPanel extends JPanel {

	private static final String RUN_COMBINATORIA_LABEL = "Run Combinatoria";
	/*
	 * Arquivos das imagens da cesta e dos doces
	 */
	private static final String FILE_BASKET ="basket.png";
	private static final String FILE_CANDY1 = "candy1.png";
	private static final String FILE_CANDY2 = "candy2.png";
	private static final String FILE_CANDY3 = "candy3.png";
	private static final String FILE_CANDY4 = "candy4.png";
	private static final String FILE_CANDY5 = "candy5.png";
	/*
	 * Numero de clareiras e doces 
	 */
	private static final int N_CLAREIRA = 10;
	private static final int N_CANDY = 5;
	
	/**
	 * Construtor que recebe a matriz combinatoria e inicializa o panel
	 * @param combinatoria
	 */
	public CandyPanel(int[][] combinatoria){
		
		this.setLayout(new BorderLayout());
		/*
		 * O Panel é formado por um panel para a combinatoria (doces) e outro para a cesta
		 */
		JPanel ClareiraPanel = new JPanel();
		JPanel BasketPanel = new JPanel();
		BasketPanel.setLayout(new BorderLayout());
		/*
		 * 
		 */
		ImageIcon basket = new ImageIcon(FILE_BASKET);
		JLabel BasketLabel = new JLabel(basket);
		BasketPanel.add(BasketLabel, BorderLayout.CENTER);
		
		ImageIcon candy[] = new ImageIcon[N_CANDY];
		
		this.add(BasketPanel,BorderLayout.NORTH);
		
		ClareiraPanel.setLayout(new GridLayout(11,6,10,10));
		
		candy[0] = new ImageIcon(FILE_CANDY1);
		candy[1] = new ImageIcon(FILE_CANDY2);
		candy[2] = new ImageIcon(FILE_CANDY3);
		candy[3] = new ImageIcon(FILE_CANDY4);
		candy[4] = new ImageIcon(FILE_CANDY5);
		
		ClareiraPanel.add(new JLabel("C"));
		
		for(int j=0;j<N_CANDY;j++)
			ClareiraPanel.add(new JLabel(candy[j]));
		
		
		for(int i=0;i<N_CLAREIRA;i++){
			ClareiraPanel.add(new JLabel("C" + i+1));
			for(int j=0;j<N_CANDY;j++){
				if(combinatoria[i][j]==1){
					ClareiraPanel.add(new JLabel(candy[j]));
				}
				else{
					ClareiraPanel.add(new JLabel(" "));
				}
			}
		}
				
		this.add(ClareiraPanel);
	}
	
	
	
}
