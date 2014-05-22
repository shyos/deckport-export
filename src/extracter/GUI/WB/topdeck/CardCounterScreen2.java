package extracter.GUI.WB.topdeck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import extracter.card.Deck;
import extracter.card.DeckItem;

public class CardCounterScreen2 extends JPanel {
	private List<DeckItem> deckItems;
	private int[] counts;
	private int i = 0;
	/**
	 * Create the panel.
	 */
	public CardCounterScreen2() {
		setLayout(null);
		

	}
	public CardCounterScreen2(Deck deck)
	{
		setLayout(null);

		setBackground(new Color(0, 0, 0));
		deckItems = deck.getCards();
		counts = new int[deckItems.size()];
		for(i=0;i<deckItems.size();i++)
		{
			DeckItem dI = deckItems.get(i);
			counts[i] = dI.getCount();
			final JLabel lblCount = new JLabel();
			lblCount.setBounds(167, 10+30*i, 44, 28);
			lblCount.setForeground(Color.ORANGE);
			lblCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
			final JLabel lblTd = new JLabel();
			lblTd.setBounds(221, 10+30*i, 23, 28);

			
			final JLabel lblImage = new JLabel();
			lblImage.setIcon(new ImageIcon(dI.getImage()));
			lblImage.setBounds(10, 10+30*i, 147, 28);
			lblImage.addMouseListener(new MouseAdapter() {
				private DeckItem dI = deckItems.get(i);
				private int index = i;
				@Override
				public void mouseReleased(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(counts[index] < deckItems.get(index).getCount())
						{
				    		lblImage.setIcon(new ImageIcon(dI.getImage()));
							counts[index]++;
							lblCount.setText(counts[index]+"");
							lblCount.setForeground(Color.ORANGE);
						}
					}
				    if(e.getButton() == MouseEvent.BUTTON1)
				    {
				    	if(counts[index] > 0)
				    	{
				    		if(counts[index] == 1)
				    		{
					    		BufferedImage blackNWhite = new BufferedImage(dI.getImage().getWidth(),dI.getImage().getHeight(),BufferedImage.TYPE_BYTE_BINARY);
								Graphics2D graphics = blackNWhite.createGraphics();
								graphics.drawImage(dI.getImage(), 0, 0, null);
								lblImage.setIcon(new ImageIcon(invertImage(blackNWhite)));
								lblCount.setForeground(Color.BLACK);
				    		}
				    		counts[index]--;
				    		lblCount.setText(counts[index]+"");
				    	}
				    }
				}
				private BufferedImage invertImage(BufferedImage blackNWhite) {
			        BufferedImage inputFile = blackNWhite;
			        for (int x = 0; x < inputFile.getWidth(); x++) {
			            for (int y = 0; y < inputFile.getHeight(); y++) {
			                int rgba = inputFile.getRGB(x, y);
			                Color col = new Color(rgba, true);
			                col = new Color(255 - col.getRed(),
			                                255 - col.getGreen(),
			                                255 - col.getBlue());
			                inputFile.setRGB(x, y, col.getRGB());
			            }
			        }
			        return inputFile;
				}

			/*	private void reCalculateTD() {
					for(int i=0;i<tableObject.length;i++)
					{
						tableObject[i][3] = String.format( "%.2f", (double)(Integer)tableObject[i][2]*100/numberOfCardsLeft );
					}
				}*/
			});
			lblCount.setText(dI.getCount()+"");
			add(lblImage);
			add(lblCount);
			add(lblTd);

		}
		JButton btnNewButton = new JButton();
		btnNewButton.setLayout(null);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setBounds(200, 0, 20, 20);
		btnNewButton.addMouseListener(new MouseAdapter() {
			private int side = 0;	
			@Override
			public void mouseReleased(MouseEvent e) {
				if(side == 0)
				{
					CardCounterMain.frame.dispose();
					CardCounterMain.frame.setUndecorated(false);
					CardCounterMain.frame.setVisible(true);
					side  = 1;
				}
				else{
					CardCounterMain.frame.dispose();
					CardCounterMain.frame.setUndecorated(true);
					CardCounterMain.frame.setVisible(true);
					side = 0;
				}
			}		
		});
		add(btnNewButton);
		
		setPreferredSize(new Dimension(200, deckItems.size()*31+10));
	}
}
