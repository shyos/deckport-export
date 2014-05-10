package extracter.GUI.WB;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import main.TesseractMain;
import extracter.Constants;
import extracter.ExtractManager;
import extracter.ExtracterMain;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class TrainDataCardScreen extends JPanel {
	private JTextField textField;
	private JLabel lblCardorder;
	private JLabel lblCardimage;
	private int currentCardOrder = 0;
	/**
	 * Create the panel.
	 */
	public TrainDataCardScreen() {
		setLayout(null);
		
		JLabel lblCardOrder = new JLabel("Card Order :");
		lblCardOrder.setBounds(65, 43, 73, 16);
		add(lblCardOrder);
		
		lblCardorder = new JLabel("1");
		lblCardorder.setBounds(158, 43, 56, 16);
		add(lblCardorder);
		
		JLabel lblCardImage = new JLabel("Card Image:");
		lblCardImage.setBounds(65, 80, 73, 16);
		add(lblCardImage);
		
		lblCardimage = new JLabel("");
		lblCardimage.setBounds(158, 80, 116, 16);
		add(lblCardimage);
		
		JLabel lblEnterCardName = new JLabel("Card Name:");
		lblEnterCardName.setBounds(65, 147, 109, 16);
		add(lblEnterCardName);
		
		textField = new JTextField();
		textField.setBounds(158, 144, 116, 22);
		add(textField);
		textField.setColumns(10);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int maxCardNumber = TrainingApp.card2.getNumberBox().getSelectedIndex()+1;
				int k = currentCardOrder;
				k = ExtracterMain.saveSingleCard(currentCardOrder, textField.getText());
				
				if(k == maxCardNumber)
				{
					//Save cards
					ExtracterMain.writeToFile(ExtracterMain.GUICards, "guicards.txt");
					
					//GUI stuff
					TrainingApp.showMessageDialog(null, " Data Training completed. Total amount of cards processed: " + maxCardNumber);
					TrainingApp.card3.getLblCardorder().setText(1+ "/" + maxCardNumber);
					textField.setText("");
					
					//set null everything
					currentCardOrder = 0;
					
					//Return to HOME Page
					CardLayout cl = (CardLayout)(TrainingApp.panel.getLayout());
					cl.show(TrainingApp.panel, "HOME");
				}
				
				else if(k > currentCardOrder)
				{
					currentCardOrder = k;
					
					//Gui stuff
					TrainingApp.card3.getLblCardorder().setText((currentCardOrder+1)+ "/" + maxCardNumber);
					textField.setText("");
					
					//Get image of next card
					ExtracterMain.getCardImage(currentCardOrder);
					
					//Set image of next card
					TrainingApp.card3.getLblCardimage().setIcon(new ImageIcon(ExtractManager.subImage));
					
					//Navigate to next card
					CardLayout cl = (CardLayout)(TrainingApp.panel.getLayout());
					cl.show(TrainingApp.panel, "CARD");
				}

			}
		});
		btnConfirm.setBounds(158, 176, 116, 22);
		add(btnConfirm);
		
		JTextPane txtpnAboveImageIs = new JTextPane();
		txtpnAboveImageIs.setText("Above image is just for comparison, if it is not a part of your current card, please restart application and check if hearthstone.exe is visible.");
		txtpnAboveImageIs.setBounds(12, 249, 426, 38);
		add(txtpnAboveImageIs);

	}
	/**
	 * @return the textField
	 */
	public JTextField getTextField() {
		return textField;
	}
	/**
	 * @param textField the textField to set
	 */
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	/**
	 * @return the lblCardimage
	 */
	public JLabel getLblCardimage() {
		return lblCardimage;
	}
	/**
	 * @param lblCardimage the lblCardimage to set
	 */
	public void setLblCardimage(JLabel lblCardimage) {
		this.lblCardimage = lblCardimage;
	}
	/**
	 * @return the lblCardorder
	 */
	public JLabel getLblCardorder() {
		return lblCardorder;
	}
	/**
	 * @param lblCardorder the lblCardorder to set
	 */
	public void setLblCardorder(JLabel lblCardorder) {
		this.lblCardorder = lblCardorder;
	}
	public int getCurrentCardOrder() {
		return currentCardOrder;
	}
	public void setCurrentCardOrder(int currentCardOrder) {
		this.currentCardOrder = currentCardOrder;
	}
}
