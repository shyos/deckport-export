package extracter.GUI.WB.winlose;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingConstants;

import extracter.GUI.WB.topdeck.CardCounterMain;

public class ScoreTrackerFrame extends JFrame {
	static Point mouseDownCompCoords;
	private JPanel contentPane;
	public static int winCount = 0;
	public static int loseCount = 0;
	public static JLabel lblWin;
	public static JLabel lblLose;
	/**
	 * Create the frame.
	 */
	public ScoreTrackerFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 335, 161);
		
		contentPane = new JPanel();

		contentPane.setBackground(new Color(1,1,1));
		contentPane.setBounds(0, 0, 335, 161);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblWin = new JLabel("0");
		lblWin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWin.setFont(new Font("Century Schoolbook", Font.BOLD, 99));
		lblWin.setBounds(12, 0, 123, 101);
		lblWin.setForeground(Color.ORANGE);
		contentPane.add(lblWin);
		
		lblLose = new JLabel("0");
		lblLose.setHorizontalAlignment(SwingConstants.LEFT);
		lblLose.setFont(new Font("Century Schoolbook", Font.BOLD, 99));
		lblLose.setBounds(166, 0, 125, 101);
		lblLose.setForeground(Color.red);
		contentPane.add(lblLose);
		
		JLabel label = new JLabel("/");
		label.setFont(new Font("Century Schoolbook", Font.BOLD, 99));
		label.setBounds(135, 0, 33, 101);
		label.setForeground(Color.WHITE);
		contentPane.add(label);
		
		// Make window pretty
		JButton btnNewButton = new JButton();
		btnNewButton.setLayout(null);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setBounds(295, 0, 20, 20);
		btnNewButton.setToolTipText("Windowless");
		btnNewButton.addMouseListener(new MouseAdapter() {
			private int side = 1;	
			@Override
			public void mouseReleased(MouseEvent e) {
				if(side == 0)
				{
					setBounds(getX(), getY(), 335, 161);
					dispose();
					setUndecorated(false);
					setVisible(true);
					side  = 1;
				}
				else{
					setBounds(getX(), getY(), 315, 110);
					dispose();
					setUndecorated(true);
					setVisible(true);
					side = 0;
				}
			}		
		});
		getContentPane().add(btnNewButton);
		
		// Reset Deck to initial
		JButton btnNewButton2 = new JButton();
		btnNewButton2.setLayout(null);
		btnNewButton2.setBackground(Color.GREEN);
		btnNewButton2.setBounds(295, 80, 20, 20);
		btnNewButton2.setToolTipText("Help");
		btnNewButton2.addMouseListener(new MouseAdapter() {
		
			public void mouseReleased(MouseEvent e) {
				JOptionPane op = new JOptionPane("Press:\n -W1 to add win\n -W2 to delete win\n -L1 to add lose\n -L2 to delete lose.", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = op.createDialog(null, "DeckPort.com");
				dialog.setAlwaysOnTop(true);
				dialog.setModal(true);
				dialog.setFocusableWindowState(true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}		
		});
		getContentPane().add(btnNewButton2);
		
		
		// Reset Deck to initial
		JButton btnNewButton3 = new JButton();
		btnNewButton3.setLayout(null);
		btnNewButton3.setBackground(Color.RED);
		btnNewButton3.setBounds(295, 40, 20, 20);
		btnNewButton3.setToolTipText("Reset");
		btnNewButton3.addMouseListener(new MouseAdapter() {
		
			public void mouseReleased(MouseEvent e) {
				winCount = 0;
				loseCount = 0;
				lblWin.setText(winCount+"");
				lblLose.setText(loseCount+"");
			}		
		});
		getContentPane().add(btnNewButton3);
		
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
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
		// End
	}

}
