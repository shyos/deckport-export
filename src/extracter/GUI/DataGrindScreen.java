package extracter.GUI;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class DataGrindScreen extends JPanel{

	private JButton confirmButton;
	private JTextField cardName;
	private JLabel myLabel;

	public DataGrindScreen() throws IOException
	{
		setLayout(null);
		Font font = new Font("Times New Roman", Font.BOLD, 16);
		
		Rectangle r3 = new Rectangle(100,60,120,30);
		myLabel = new JLabel("1. Kart");
		myLabel.setBounds(r3);
        add(myLabel);
        
        
		Rectangle r = new Rectangle(100,100,120,30);
		cardName = new JTextField();
		cardName.setBounds(r);
        add(cardName);
        
        Rectangle r2 = new Rectangle(100,140,120,30);
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(r2);
        add(confirmButton);
        
	}

	public void addActionListeners(ActionListener e) {
		confirmButton.addActionListener(e);

	}

	/**
	 * @return the confirmButton
	 */
	public JButton getConfirmButton() {
		return confirmButton;
	}

	/**
	 * @param confirmButton the confirmButton to set
	 */
	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	/**
	 * @return the cardName
	 */
	public JTextField getCardName() {
		return cardName;
	}

	/**
	 * @param cardName the cardName to set
	 */
	public void setCardName(JTextField cardName) {
		this.cardName = cardName;
	}

	/**
	 * @return the myLabel
	 */
	public JLabel getMyLabel() {
		return myLabel;
	}

	/**
	 * @param myLabel the myLabel to set
	 */
	public void setMyLabel(JLabel myLabel) {
		this.myLabel = myLabel;
	}



}
