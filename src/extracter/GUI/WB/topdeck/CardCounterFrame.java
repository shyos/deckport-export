package extracter.GUI.WB.topdeck;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import extracter.ExtracterMain;
import extracter.card.Deck;
import java.awt.Font;
import javax.swing.SwingConstants;

public  class CardCounterFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private CardCounterScreen card1;
	private CardCounterScreen card2;
	private CardCounterScreen2 card3;


	/**
	 * Create the frame.
	 */
	public CardCounterFrame() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 396, 405);
		setBackground(Color.BLACK);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 220, 364);
		panel.setLayout(new CardLayout(0, 0));
		contentPane.add(panel);
		card3 = new CardCounterScreen2();
		panel.add(card3,"CARD2");
		

		
		getContentPane().add(contentPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(219, 0, 161, 366);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(40, 58, 74, 23);
		panel_1.add(btnReset);
		
		JButton btnBuild = new JButton("Build");
		btnBuild.setBounds(40, 24, 74, 23);
		panel_1.add(btnBuild);
		btnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Deck deck = ExtracterMain.exportDeck("TopDeck");
				card3 = new CardCounterScreen2(deck);
				JScrollPane myScroll = new JScrollPane(card3);
				myScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				myScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.setBounds(0, 0, 220, 50+deck.getCards().size()*31);
				myScroll.setPreferredSize(new Dimension(220,50+deck.getCards().size()*31));
				setBounds(100, 100, 396, 50+deck.getCards().size()*31);
				panel.add(myScroll,"CARD2");
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "CARD2");
			}
		});
	/*	card1 = new CardCounterScreen(null);
		card1.setBounds(0, 1, 291, 504);
		contentPane.add(card1);*/
	}
}