package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/*
 * @Author Marcus Asplund
 * 
 */


public class GomokuMain {

	
	
	public static void main(String[] args) {
		
		int port = 6999;
		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
		}
		
		
		GomokuClient client = new GomokuClient(port);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);
		/*
		GomokuClient client2 = new GomokuClient(7000);
		GomokuGameState gameState2 = new GomokuGameState(client2);
		GomokuGUI gui2 = new GomokuGUI(gameState2, client2);
		*/
		
	}

	

}
