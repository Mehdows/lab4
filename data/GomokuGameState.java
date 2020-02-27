package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;


/*
 * @author Arthur Andersson
 */
/**
 * Represents the state of a game
 */

@SuppressWarnings("deprecation")
public class GomokuGameState extends Observable implements Observer{

   // Game variables
	public final int DEFAULT_SIZE = 5;
	private static GameGrid gameGrid;
	
    //Possible game states
	public final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINNISHED = 3;
	public int currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}


	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */

	public String getMessageString(){
		return message;
	}
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */

	public static GameGrid getGameGrid(){
		return gameGrid;
	}
	/**
	 * This player makes a move at a specified location
	 * 
	 * If the move is possible the game grid is updated
	 * and the move is passed to the opponent.
	 * If the move results in a win the game state is updated accordingly.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * 
	 */
	public void move(int x, int y){
		if (currentState == MY_TURN) {
			if (gameGrid.move(x, y, gameGrid.ME) == true) {
				currentState = OTHER_TURN;
				client.sendMoveMessage(x, y);
				message = "Waiting for the opponent...";
				if (gameGrid.isWinner(gameGrid.ME) == true) {
					currentState = FINNISHED;
					message = "Game over, you won!";
				}
			} else {
				message = "That square is occupied.";
			}
		} else {
			message = "It's not your turn.";
		}
		setChanged();
		notifyObservers();
	}
	/**
	 * Starts a new game with the current client
	 * 
	 * Clears the board and passes the turn to the opponent.
	 */
	public void newGame(){
		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "A new game has started, your the opponent goes first.";
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();
	}
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 * 
	 * Clears the board and starts the first turn.
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "A new game has started, its your turn.";
		setChanged();
		notifyObservers();	
	}	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 * 
	 * Clears the board since the opponent is no longer playing.
	 */
	public void otherGuyLeft(){
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "The opponent disconnected.";
		setChanged();
		notifyObservers();
	}
	/**
	 * The player disconnects from the client
	 * 
	 * Clears the board and notifies the opponent.
	 */
	public void disconnect(){
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "Disconnected.";
		client.disconnect();
		setChanged();
		notifyObservers();
	}
	/**
	 * The player receives a move from the other player
	 * Registers the move made by the opponent on this players board,
	 * then checks if the opponent has won.
	 * If not, start a turn.
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		gameGrid.move(x, y, gameGrid.OTHER);
		if (gameGrid.isWinner(gameGrid.OTHER) == true) {
			currentState = FINNISHED;
			message = "Game over, the opponent won.";
		} else {
			currentState = MY_TURN;
			message = "Your turn.";
		}
		setChanged();
		notifyObservers();
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();	
	}	
}