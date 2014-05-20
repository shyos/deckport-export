package extracter.GUI.WB.topdeck;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.google.gson.Gson;

import extracter.ExtracterMain;
import extracter.card.Deck;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CardCounterFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private CardCounterScreen card1;
	private CardCounterScreen card2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardCounterFrame frame = new CardCounterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CardCounterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 396, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		panel = new JPanel();
		card1 = new CardCounterScreen();
		panel.setLayout(new CardLayout(0, 0));
		panel.add(card1,"CARD");
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(291, 44, 89, 23);
		contentPane.add(btnReset);
		
		panel.setBounds(0, 1, 291, 428);
		contentPane.add(panel);
		
		JButton btnBuild = new JButton("Build");
		btnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Deck deck = ExtracterMain.exportDeck("TopDeck");
				System.out.println(new Gson().toJson(deck));
				card2 = new CardCounterScreen(deck);
				panel.add(card2,"CARD2");
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "CARD2");
			}
		});
		btnBuild.setBounds(291, 11, 89, 23);
		contentPane.add(btnBuild);
		
		getContentPane().add(contentPane);
	}
}
