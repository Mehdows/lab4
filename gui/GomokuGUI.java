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
		connectButton = new JButton("Connect");
		newGameButton = new JButton("New Game");
		disconnectButton = new JButton("Disconnect");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(g.DEFAULT_SIZE*GamePanel.UNIT_SIZE+100, gamestate.DEFAULT_SIZE*GamePanel.UNIT_SIZE+100);
		frame.setTitle("Gomoku");
		frame.setVisible(true);
		frame.setResizable(true);
		
		GamePanel gameGridPanel = new GamePanel(GomokuGameState.getGameGrid());
		MouseAdapter mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int[] xy = gameGridPanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(xy[0], xy[1]);
			}
		};
		
		gameGridPanel.addMouseListener(mouseListener);
		
		if(( gamestate.currentState == gamestate.NOT_STARTED)) {
			newGameButton.setEnabled(false);
			disconnectButton.setEnabled(false);
		}
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = e.getActionCommand();
				if(input.equals("Disconnect")) {
					gamestate.disconnect();
				}
			}
		});
		
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String input = e.getActionCommand();
				if(input.equals("Connect")) {
					ConnectionWindow c = new ConnectionWindow(client);
				}
			}
		});
		
		newGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String input = e.getActionCommand();
				if(input.equals("New Game")) {
					gamestate.newGame();
				}
			}
		});
		Box b = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createVerticalBox();
		
		b.add(connectButton);
		b.add(newGameButton);
		b.add(disconnectButton);
		
		b2.add(messageLabel);
		
		b3.add(gameGridPanel);
		b3.add(b);
		b3.add(b2);
		
		frame.add(b3);
		
		
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