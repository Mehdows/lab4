package lab4.gui;


import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

/*
 * @Author Marcus Asplund
 */

@SuppressWarnings("deprecation")
public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	JButton connectButton;
	JButton newGameButton;
	JButton disconnectButton;
	JLabel messageLabel;
	JFrame frame;
	/* The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 * @return 
	 */
	
	
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		
		this.client = c;
		this.gamestate = g;
		
		client.addObserver(this);
		gamestate.addObserver(this);
		
		frame = new JFrame();
		messageLabel = new JLabel();
		connectButton = new JButton();
		newGameButton = new JButton();
		disconnectButton = new JButton();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(g.DEFAULT_SIZE*GamePanel.UNIT_SIZE, gamestate.DEFAULT_SIZE*GamePanel.UNIT_SIZE);
		frame.setTitle("Gomoku");
		frame.setVisible(true);
		frame.setResizable(false);
		
		GamePanel gameGridPanel = new GamePanel(GomokuGameState.getGameGrid());
		MouseAdapter mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				gamestate.move(e.getX(), e.getY());
			}
		};
		gameGridPanel.addMouseListener(mouseListener);
		
		if(( gamestate.currentState == gamestate.NOT_STARTED)) {
			newGameButton.setEnabled(false);
			disconnectButton.setEnabled(false);
		}
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = e.getActionCommand();
				if(input.equals("Disconnect")) {
					gamestate.disconnect();
					messageLabel.setText("You've ben disconnected");
				}
			}
		});
		
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String buttonInput = e.getActionCommand();
				if(buttonInput.equals("New Game")) {
					gamestate.newGame();
					messageLabel.setText("A new game has started");
				}
			}
		});
		
		Box b = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		
		b.add(connectButton);
		b.add(newGameButton);
		b.add(disconnectButton);
		
		
		
		
	}
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}	
	}
}