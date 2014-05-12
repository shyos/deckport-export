package extracter.GUI.WB;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.Box;

import extracter.ExtracterMain;

import java.awt.Choice;
import java.awt.CardLayout;
import javax.swing.JButton;

public class TrainingApp {

	private JFrame frame;
	public static JPanel panel;
	public static TrainDataMenu card2 = new TrainDataMenu();
	public static TrainDataCardScreen card3 = new TrainDataCardScreen();
	public static ExportScreen card4 = new ExportScreen();
	public static ImportScreen card5 = new ImportScreen();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainingApp window = new TrainingApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrainingApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 408, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JPanel card1 = new JPanel();
		card2 = new TrainDataMenu();
		card3 = new TrainDataCardScreen();
		card4 = new ExportScreen();
		
		panel = new JPanel();
		panel.setBounds(0, 0, 390, 377);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		panel.add(card1,"HOME");
		panel.add(card2,"TRAINMENU");
		panel.add(card3,"CARD");
		panel.add(card4,"EXPORT");
		panel.add(card5,"IMPORT");
		
		Button homeButton = new Button("Home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "HOME");
			}
		});
		menuBar.add(homeButton);
		
		Button trainButton = new Button("Train Data");
		trainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "TRAINMENU");
			}
		});
		menuBar.add(trainButton);
		
		Button exportButton = new Button("Export");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "EXPORT");
			}
		});
		menuBar.add(exportButton);
		
		Button importButton = new Button("Import");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "IMPORT");
			}
		});
		menuBar.add(importButton);
		
		Button aboutButton = new Button("About");
		menuBar.add(aboutButton);
		frame.getContentPane().setLayout(null);
		

	}
	public static void showMessageDialog(Component parentComponent, String message) {
		JOptionPane op = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = op.createDialog(parentComponent, "HearthStats.net");
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setFocusableWindowState(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}
