package extracter.GUI.WB;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import extracter.ExtractManager;
import extracter.ExtracterMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TrainDataMenu extends JPanel {
	private JComboBox comboBox;
	private JComboBox numberBox;
	/**
	 * Create the panel.
	 */
	public TrainDataMenu() {
		setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Initilize Environment
				ExtracterMain.buildEnvironment();
			
				if(ExtracterMain.checkResolution())
				{
					TrainingApp.card3.getLblCardorder().setText("1/" + (TrainingApp.card2.getNumberBox().getSelectedIndex()+1));
					ExtracterMain.getCardImage(TrainingApp.card3.getCurrentCardOrder());
					TrainingApp.card3.getLblCardimage().setIcon(new ImageIcon(ExtractManager.subImage));
					CardLayout cl = (CardLayout)(TrainingApp.panel.getLayout());
				    cl.show(TrainingApp.panel, "CARD");
				}
			}
		});
		btnStart.setBounds(177, 189, 105, 25);
		add(btnStart);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1024x768"}));
		comboBox.setBounds(177, 117, 105, 22);
		add(comboBox);
		
		JLabel lblResolution = new JLabel("Resolution:");
		lblResolution.setBounds(89, 120, 76, 16);
		add(lblResolution);
		
		JTextPane txtpnPleaseSelect = new JTextPane();
		txtpnPleaseSelect.setText("Please select :\n - Hearthstone.exe resolution \n - Set number of cards you will enter. (Max 20)");
		txtpnPleaseSelect.setBounds(49, 13, 294, 59);
		add(txtpnPleaseSelect);
		
		JLabel lblOfCards = new JLabel("# of Cards:");
		lblOfCards.setBounds(89, 155, 66, 16);
		add(lblOfCards);
		
		numberBox = new JComboBox();
		numberBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		numberBox.setBounds(177, 152, 105, 22);
		add(numberBox);

	}
	public JComboBox getComboBox() {
		return comboBox;
	}
	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}
	public JComboBox getNumberBox() {
		return numberBox;
	}
	public void setNumberBox(JComboBox numberBox) {
		this.numberBox = numberBox;
	}
}
