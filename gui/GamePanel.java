package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import lab4.data.GameGrid;

/*
 * @Author Marcus Asplund
 */



/*
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	public final static int UNIT_SIZE = 20;
	private lab4.data.GameGrid grid;
	private Color colorGrid = Color.pink;
	private Color colorCircle = Color.green;
	private Color colorCross = Color.red;
	
	/*
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	
	public int[] getGridPosition(int x, int y){
		int[] coords = {x/UNIT_SIZE,y/UNIT_SIZE};
		return coords;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(int x = grid.getSize()-1; x >= 0; x--) {
			for (int y = grid.getSize()-1; y >= 0; y--) {
				paintRectangle(g, x, y, colorGrid);
				
				if(grid.getLocation(x, y) == GameGrid.ME) {
					paintCross(g, x, y, colorCross);
				}
				if(grid.getLocation(x, y) == GameGrid.OTHER) {
					paintCircle(g, x, y, colorCross);
				}
		
			}
		}
	}
	
	private void paintRectangle(Graphics g, int x, int y, Color c) {
		
		g.setColor(c);
		g.drawRect(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
		
	}
	private void paintCircle(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.drawOval(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
	}
	private void paintCross(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.drawLine(x*UNIT_SIZE, y*UNIT_SIZE, x + UNIT_SIZE, y + UNIT_SIZE);
		g.drawLine(x*UNIT_SIZE, y*UNIT_SIZE, x + UNIT_SIZE, y + UNIT_SIZE);
	}
}