package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/*
 * @Author Marcus Asplund
 * 
 */


public class GomokuMain {

	
	static GomokuClient client;
	
	public static void main(String[] args) {
		/*
		int portStandard = 4000;
		
		if (args.length == 1) {
			int port = Integer.parseInt(args[0]);
			GomokuClient client = new GomokuClient(port);
		}
		else {
			GomokuClient client = new GomokuClient(portStandard);
		}
		*/
		GomokuClient client = new GomokuClient(4069);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);
		
	}

	

}
