package extracter.GUI.WB.topdeck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import extracter.card.Deck;
import extracter.card.DeckItem;

public class CardCounterScreen2 extends JPanel {
	static Point mouseDownCompCoords;
	private List<DeckItem> deckItems;
	private JLabel[] lblCount;
	private JLabel[] lblImage;
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
		lblCount = new JLabel[deckItems.size()];
		lblImage = new JLabel[deckItems.size()];
		counts = new int[deckItems.size()];
		for(i=0;i<deckItems.size();i++)
		{
			DeckItem dI = deckItems.get(i);
			counts[i] = dI.getCount();
			lblCount[i] = new JLabel();
			lblCount[i].setBounds(167, 10+30*i, 44, 28);
			lblCount[i].setForeground(Color.ORANGE);
			lblCount[i].setFont(new Font("Tahoma", Font.PLAIN, 16));
			final JLabel lblTd = new JLabel();
			lblTd.setBounds(221, 10+30*i, 23, 28);

			// Image changes
			lblImage[i] = new JLabel();
			lblImage[i].setIcon(new ImageIcon(dI.getImage()));
			lblImage[i].setBounds(10, 10+30*i, 147, 28);
			lblImage[i].addMouseListener(new MouseAdapter() {
				private DeckItem dI = deckItems.get(i);
				private int index = i;
				@Override
				public void mouseReleased(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(counts[index] < deckItems.get(index).getCount())
						{
							lblImage[index].setIcon(new ImageIcon(dI.getImage()));
							counts[index]++;
							lblCount[index].setText(counts[index]+"");
							lblCount[index].setForeground(Color.ORANGE);
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
								lblImage[index].setIcon(new ImageIcon(invertImage(blackNWhite)));
								lblCount[index].setForeground(Color.BLACK);
				    		}
				    		counts[index]--;
				    		lblCount[index].setText(counts[index]+"");
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

			});
			lblCount[i].setText(dI.getCount()+"");
			add(lblImage[i]);
			add(lblCount[i]);
			add(lblTd);

		}
		
		// Make window pretty
		JButton btnNewButton = new JButton();
		btnNewButton.setLayout(null);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setBounds(200, 0, 20, 20);
		btnNewButton.setToolTipText("Windowless");
		btnNewButton.addMouseListener(new MouseAdapter() {
			private int side = 1;	
			@Override
			public void mouseReleased(MouseEvent e) {
				if(side == 0)
				{
					CardCounterMain.frame.setBounds(CardCounterMain.frame.getX(), CardCounterMain.frame.getY(), 396, 50+deckItems.size()*31);
					CardCounterMain.frame.dispose();
					CardCounterMain.frame.setUndecorated(false);
					CardCounterMain.frame.setVisible(true);
					side  = 1;
				}
				else{
					CardCounterMain.frame.setBounds(CardCounterMain.frame.getX(), CardCounterMain.frame.getY(), 220, 10+deckItems.size()*31);
					CardCounterMain.frame.dispose();
					CardCounterMain.frame.setUndecorated(true);
					CardCounterMain.frame.setVisible(true);
					side = 0;
				}
			}		
		});
		add(btnNewButton);
		
		// Build new deck
		JButton btnNewButton2 = new JButton();
		btnNewButton2.setLayout(null);
		btnNewButton2.setBackground(Color.GREEN);
		btnNewButton2.setBounds(200, 40, 20, 20);
		btnNewButton2.setToolTipText("Build");
		btnNewButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}		
		});
		add(btnNewButton2);
		
		// Reset Deck to initial
		JButton btnNewButton3 = new JButton();
		btnNewButton3.setLayout(null);
		btnNewButton3.setBackground(Color.RED);
		btnNewButton3.setBounds(200, 60, 20, 20);
		btnNewButton3.setToolTipText("Reset");
		btnNewButton3.addMouseListener(new MouseAdapter() {
		
			public void mouseReleased(MouseEvent e) {
				for(int j=0;j<deckItems.size();j++)
				{
					lblImage[j].setIcon(new ImageIcon(deckItems.get(j).getImage()));
					counts[j] = deckItems.get(j).getCount();
					lblCount[j].setText(counts[j]+"");
					lblCount[j].setForeground(Color.ORANGE);
				}
			}		
		});
		add(btnNewButton3);
		
		// Helps to move frame when its pretty
		// Start
		addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        });
		addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                CardCounterMain.frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
		// End
		
		setPreferredSize(new Dimension(200, deckItems.size()*31+10));
	}
}
