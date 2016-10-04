import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MapPanel extends JComponent implements Runnable{
	
	private static final Color COLOR_PATH = Color.LIGHT_GRAY;
	private static final Color COLOR_GALHO = Color.GRAY;
	private static final Color COLOR_DENSE = Color.GREEN;
	private static final Color COLOR_INICIO = Color.ORANGE;
	private static final Color COLOR_FINAL = Color.PINK;
	private static final Color COLOR_CLAREIRA = Color.YELLOW;
	private static final Color COLOR_SOLUTION = Color.RED;
	private static final int GRID_OFFSET = 8;
	private static final int REFRESH_RATE = 15;
	
	private static final String FILE_PATH = "path_treated.png";
	private static final String FILE_GRASS = "grass_treated.png";
	private static final String FILE_DENSE = "tree_treated.png";
	private static final String FILE_INITIAL = "red_treated.png";
	private static final String FILE_FINAL = "house_treated.png";
	private static final String FILE_CLEAR = "wolf_treated.png";
	
	private static BufferedImage IMAGE_PATH ;
	private static BufferedImage IMAGE_GRASS;
	private static BufferedImage IMAGE_DENSE ;
	private static BufferedImage IMAGE_INITIAL;
	private static BufferedImage IMAGE_FINAL ;
	private static BufferedImage IMAGE_CLEAR;
	
	
	private Busca busca;
	private Mapa mapa;
	{
		try {
			IMAGE_PATH = ImageIO.read(new File(FILE_PATH));
			IMAGE_GRASS = ImageIO.read(new File(FILE_GRASS));
			IMAGE_DENSE = ImageIO.read(new File(FILE_DENSE));
			IMAGE_INITIAL = ImageIO.read(new File(FILE_INITIAL));
			IMAGE_FINAL = ImageIO.read(new File(FILE_FINAL));
			IMAGE_CLEAR = ImageIO.read(new File(FILE_CLEAR));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setMapa (Mapa mapa){
		this.mapa = mapa;
	}
	
	@Override
	protected void paintComponent (Graphics g){
		super.paintComponent(g);
		System.out.println("paintComponent");
		int rows = mapa.getRows();
		int collumns = mapa.getCollumns();
		int x_offset = super.getWidth()/rows;
		int y_offset = super.getHeight()/collumns;
		for(int i = 0; i< rows; i++){
			for(int j=0; j<collumns ; j++){
				drawTile(g,mapa.getChar(j, i),
						new Rectangle(super.getX()+j*x_offset,
								super.getY()+i*y_offset,
								x_offset,y_offset)
						);
			}
		}
//		Node cBest = busca.getCurrentBest();
		//ArrayList<Node> borders = busca.getBorders();
//		int visited[][] = busca.getVisited();
		

		//printBorders(borders,g);
//		printVisited(visited,g);
//		printPathToNode(cBest,g);
	}
	private void printVisited(int[][] visited, Graphics g) {
		// TODO Auto-generated method stub
		if(visited == null){
			return;
		}
		int rows = mapa.getRows();
		int collumns = mapa.getCollumns();
		int x_offset = super.getWidth()/rows;
		int y_offset = super.getHeight()/collumns;
		
		for(int i =0;i<mapa.getCollumns();i++){
			for(int j=0;j<mapa.getRows();j++){
				if(visited[i][j] != -1){
//					drawTile(g,mapa.getChar(j, i),
//							new Rectangle(super.getX()+j*x_offset,
//									super.getY()+i*y_offset,
//									x_offset,y_offset)
//							);
					drawVisited(i,j,g);
				}
			}
		}
		
	}
	private void drawVisited(int j, int i, Graphics g) {
		// TODO Auto-generated method stub
		int rows = mapa.getRows();
		int collumns = mapa.getCollumns();
		int x_offset = super.getWidth()/rows;
		int y_offset = super.getHeight()/collumns;
		Rectangle r = new Rectangle(super.getX()+j*x_offset,
				super.getY()+i*y_offset,
				x_offset,y_offset);
		drawTile(getGraphics(), mapa.getChar(j,i), r);
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		for(int k =0; k*GRID_OFFSET<x_offset;k++){
			g.drawLine(super.getX()+j*x_offset+k*GRID_OFFSET
					, super.getY()+i*y_offset
					, super.getX()+j*x_offset+k*GRID_OFFSET
					,super.getY()+ (i+1)*y_offset);
		}
		for(int k =0; k*GRID_OFFSET<y_offset;k++){
			g.drawLine(j*x_offset + super.getX()
					, i*y_offset+k*GRID_OFFSET + super.getY()
					, (j+1)*x_offset + super.getX()
					,i*y_offset+k*GRID_OFFSET+ super.getY());
		}
		g.setColor(c);
	}
	private void printBorders(ArrayList<Node> borders, Graphics g) {
		// TODO Auto-generated method stub
		if(borders==null){
			return;
		}
		for(Node n:borders){
			int rows = mapa.getRows();
			int collumns = mapa.getCollumns();
			int x_offset = super.getWidth()/rows;
			int y_offset = super.getHeight()/collumns;
			
			Rectangle r = new Rectangle(super.getX()+n.getX()*x_offset,
								super.getY()+n.getY()*y_offset,
								x_offset,y_offset);
			g.setColor(COLOR_SOLUTION);
			g.drawRect(r.x, r.y, r.width, r.height);
					
				
			
		}
		
	}
	public void printPathToNode(Node cBest, Graphics g) {
		// TODO Auto-generated method stub
		if(cBest == null){
			return;
		}
		Node parent = cBest.getParent();
		Node current = cBest;
		int rows = mapa.getRows();
		int collumns = mapa.getCollumns();
		int x_offset = super.getWidth()/rows;
		int y_offset = super.getHeight()/collumns;
		Color c = g.getColor();
		g.setColor(COLOR_SOLUTION);
		while(parent!=null){
			int x1 = current.getX()*x_offset+super.getX() + x_offset/2;
			int y1 = current.getY()*y_offset+super.getY() + y_offset/2;
			int x2 = parent.getX()*x_offset+super.getX() + x_offset/2;
			int y2 = parent.getY()*y_offset+super.getY() + y_offset/2;
			g.drawLine(x1, y1, x2, y2);
			current = parent;
			parent = parent.getParent();
		}
		g.setColor(c);
	}
	private void drawTile(Graphics g, char char1,Rectangle r) {
		// TODO Auto-generated method stub
		Color c = g.getColor();
		g.translate(r.x, r.y);
		printImage(IMAGE_PATH,r.width,r.height,g);
		switch(char1){
			case '.':
//				g.setColor(COLOR_PATH);
				
				break;
			case 'G':
//				g.setColor(COLOR_GALHO);
				printImage(IMAGE_GRASS,r.width,r.height,g);
				break;
			case 'D':
//				g.setColor(COLOR_DENSE);
				printImage(IMAGE_DENSE,r.width,r.height,g);
				break;
			case 'I':
//				g.setColor(COLOR_INICIO);
				printImage(IMAGE_INITIAL,r.width,r.height,g);
				break;
			case 'F':
//				g.setColor(COLOR_FINAL);
				printImage(IMAGE_FINAL,r.width,r.height,g);
				break;
			case 'C':
//				g.setColor(COLOR_CLAREIRA);
				printImage(IMAGE_CLEAR,r.width,r.height,g);
				break;
			case '@':
				
				g.setColor(COLOR_SOLUTION);
				g.drawLine(r.x+r.width/2, r.y, r.x+r.width/2 , r.y+r.height);
				g.drawLine(r.x, r.y+r.height/2, r.x+r.width , r.y+r.height/2);
				return;
				//break;
			default:
				g.setColor(c);
				break;
		}
//		g.fillRect(r.x, r.y, r.width, r.height);
//		g.setColor(Color.BLACK);
//		g.drawRect(r.x, r.y, r.width, r.height);
		
		
		g.translate(-r.x, -r.y);
		g.setColor(c);
		
		
	}
//	public void printSolution(Graphics g){
//		int rows = mapa.getRows();
//		int collumns = mapa.getCollumns();
//		int x_offset = super.getWidth()/rows;
//		int y_offset = super.getHeight()/collumns;
//		
//		for(int i = 0; i< rows; i++){
//			for(int j=0; j<collumns ; j++){
//				if(mapa.getChar(j, i)=='@'){
//					Rectangle r = new Rectangle(super.getX()+j*x_offset,
//							super.getY()+i*y_offset,
//							x_offset,y_offset);
//					g.setColor(COLOR_SOLUTION);
//					g.drawLine(r.x+r.width/2, r.y, r.x+r.width/2 , r.y+r.height);
//					g.drawLine(r.x, r.y+r.height/2, r.x+r.width , r.y+r.height/2);
//				}
//			}
//		}
//		this.printVisited(this.busca.getVisited(), g);
//	}
	public void setBusca(Busca busca2) {
		// TODO Auto-generated method stub
		this.busca=busca2;
	}
	
	public void printSolution(){
		Node end = this.busca.getCurrentBest();
		ArrayList<Node> solution = new ArrayList<>();
		solution.add(end);
		
		this.paintComponent(getGraphics());
		
		Node parent = end.getParent();
		while(parent!=null){
			solution.add(0, parent);
			parent = parent.getParent();
		}
		Node previous = null;
		for(Node n:solution){
			if(previous==null){
				printNode(n,'.');
			} else {
				printNode(previous);
			}
			printNode(n,'I');
			previous = n;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
//			this.paintComponent(getGraphics());
			this.printVisited(busca.getVisited(), getGraphics());
			this.printPathToNode(busca.getCurrentBest(), this.getGraphics());
//			System.out.println("repaint");
			try {
				Thread.sleep(1000/REFRESH_RATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * Prints image at that origin
	 * @param x
	 * @param y
	 * @param g
	 */
	private void printImage(BufferedImage image,int width,int height,Graphics g){
		
		g.drawImage(image, 0, 0,width,height,null);
		
		return;
	}
	private void printNode (Node n){
		int rows = mapa.getRows();
		int collumns = mapa.getCollumns();
		int x_offset = super.getWidth()/rows;
		int y_offset = super.getHeight()/collumns;
		
		Rectangle r = new Rectangle(super.getX()+n.getX()*x_offset,
				super.getY()+n.getY()*y_offset,
				x_offset,y_offset);
		
		drawTile(getGraphics(), mapa.getChar(n.getX(), n.getY()), r);
	}
	private void printNode (Node n,char forceChar){
		int rows = mapa.getRows();
		int collumns = mapa.getCollumns();
		int x_offset = super.getWidth()/rows;
		int y_offset = super.getHeight()/collumns;
		
		Rectangle r = new Rectangle(super.getX()+n.getX()*x_offset,
				super.getY()+n.getY()*y_offset,
				x_offset,y_offset);
		
		drawTile(getGraphics(), forceChar, r);
	}
	
}
