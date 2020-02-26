package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

@SuppressWarnings("deprecation")
public class GameGrid extends Observable{
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	
	public static final int INROW = 5;
	private int[][] gameGrid;
	private int size;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		gameGrid = new int[size][size];
		this.size = size;
	}
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return gameGrid[x][y];
	}
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if (gameGrid[x][y] != EMPTY) {
			return false;
		} else {
			gameGrid[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		}
	}
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				gameGrid[x][y] = EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		for (int k=0; k<size; k++) {
			int count = 0;
			int x = 0;
			int y = k;
			while (y>=0) {
				if (gameGrid[x][y] == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == INROW) {
					return true;
				}
				x++;
				y--;
			}
		}
		for (int k=1; k<size; k++) {
			int count = 0;
			int x = k;
			int y = size-1;
			while (x<size) {
				if (gameGrid[x][y] == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == INROW) {
					return true;
				}
				x++;
				y--;
			}
		}
		for (int k=size-1; k>=0; k--) {
			int count = 0;
			int x = 0;
			int y = k;
			while (y<size) {
				if (gameGrid[x][y] == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == INROW) {
					return true;
				}
				x++;
				y++;
			}
		}
		for (int k=1; k<size; k++) {
			int count = 0;
			int x = k;
			int y = 0;
			while (x<size) {
				if (gameGrid[x][y] == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == INROW) {
					return true;
				}
				x++;
				y++;
			}
		}
		for (int x=0; x<size; x++) {
			int count = 0;
			for (int y=0; y<size; y++) {
				if (gameGrid[x][y] == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == INROW) {
					return true;
				}
			}	
		}
		for (int y=0; y<size; y++) {
			int count = 0;
			for (int x=0; x<size; x++) {
				if (gameGrid[x][y] == player) {
					count++;
				} else {
					count = 0;
				}
				if (count == INROW) {
					return true;
				}
			}	
		}
		return false;		
	}
}
