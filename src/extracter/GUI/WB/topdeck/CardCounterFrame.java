package extracter.GUI.WB.topdeck;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import extracter.ExtracterMain;
import extracter.GUI.WB.TrainingApp;
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
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
				if(ExtracterMain.checkResolution())
				{
					Deck deck = ExtracterMain.exportDeck("TopDeck");
					if(deck == null)
						TrainingApp.showMessageDialog(null, "Deckpage is invalid. Possible Reasons:\n - You are not on deck page\n - Deck is not complete.\n");
					else
					{
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
				}
			}
		});
	}
}
