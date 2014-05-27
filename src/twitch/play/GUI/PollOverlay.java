package twitch.play.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import extracter.PixelManager;
import extracter.RobotManager;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import main.WindowCapture;
import java.awt.Font;

public class PollOverlay extends JFrame {

	public static JPanel contentPane;
	public static JLabel lblCount1;
	public static JLabel lblCount2;
	public static JLabel lblCount3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PollOverlay frame = new PollOverlay();
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
	public PollOverlay() {
		WindowCapture WC = new WindowCapture();
		PixelManager.setPixelManager();
		Color countColor = new Color(255, 204, 0);
		Color orderColor = Color.RED;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
        setBounds(RobotManager.getWindowSize());
        setAlwaysOnTop(true);
        setFocusable(false);
        
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0,0,0,0));
		
		lblCount1 = new JLabel("0");
		lblCount1.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblCount1.setForeground(countColor);
		lblCount1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount1.setBounds(scale(110, 420, 200, 100));
		contentPane.add(lblCount1);
		
		lblCount2 = new JLabel("0");
		lblCount2.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblCount2.setForeground(countColor);
		lblCount2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount2.setBounds(scale(310, 420, 200, 100));
		contentPane.add(lblCount2);
		
		lblCount3 = new JLabel("0");
		lblCount3.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblCount3.setForeground(countColor);
		lblCount3.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount3.setBounds(scale(510, 420, 200, 100));
		contentPane.add(lblCount3);
		
		JLabel lblPick = new JLabel("!1");
		lblPick.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblPick.setHorizontalAlignment(SwingConstants.CENTER);
		lblPick.setForeground(orderColor);
		lblPick.setBounds(scale(110, 150, 200, 100));
		contentPane.add(lblPick);
		
		JLabel lblPick2 = new JLabel("!2");
		lblPick2.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblPick2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPick2.setForeground(orderColor);
		lblPick2.setBounds(scale(310, 150, 200, 100));
		contentPane.add(lblPick2);
		
		JLabel lblPick3 = new JLabel("!3");
		lblPick3.setFont(new Font("Tahoma", Font.PLAIN, 99));
		lblPick3.setHorizontalAlignment(SwingConstants.CENTER);
		lblPick3.setForeground(orderColor);
		lblPick3.setBounds(scale(510, 150, 200, 100));
		contentPane.add(lblPick3);
	}
	private Rectangle scale(int x, int y, int w, int h) {
		int c = PixelManager.sideCrop;
		int ww = 20; // Window label
		double r = PixelManager.ratio;
		return new Rectangle((int)(c+x*r), (int)(ww + y*r), (int)(c+w*r), (int)(h*r));
	}

}
