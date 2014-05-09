package extracter.GUI;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;



public class MainScreen extends JPanel{

	private JButton startButton;
	private JButton riverButton;
	private JButton cleanRiverButton;
	private JButton homeButton;
	private JButton periodicButton;
	
	public JButton getHomeButton() {
		return homeButton;
	}

	public void setHomeButton(JButton homeButton) {
		this.homeButton = homeButton;
	}

	public JButton getCleanRiverButton() {
		return cleanRiverButton;
	}

	public void setCleanRiverButton(JButton cleanRiverButton) {
		this.cleanRiverButton = cleanRiverButton;
	}

	public MainScreen() throws IOException
	{
		setLayout(null);
		Font font = new Font("Times New Roman", Font.BOLD, 16);
		Rectangle r = new Rectangle(100,100,120,30);
		startButton = new JButton("Start");
		startButton.setBounds(r);
        add(startButton);
        
	}

	public void addActionListeners(ActionListener e) {
		startButton.addActionListener(e);

	}

	/**
	 * @return the startButton
	 */
	public JButton getStartButton() {
		return startButton;
	}

	/**
	 * @param startButton the startButton to set
	 */
	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}


}
