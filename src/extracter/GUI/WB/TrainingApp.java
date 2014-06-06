package extracter.GUI.WB;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import extracter.Constants;
import updater.Update;
import updater.UpdateInfo;
import updater.Updater;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.UIManager.LookAndFeelInfo;

public class TrainingApp {
	private JFrame frame;
	public static JPanel panel = new HomeScreen();
	public static HomeScreen card1 = new HomeScreen();
	public static TrainDataMenu card2 = new TrainDataMenu();
	public static TrainDataCardScreen card3 = new TrainDataCardScreen();
	public static ExportScreen card4 = new ExportScreen();
	public static ImportScreen card5 = new ImportScreen();
	public static WidgetScreen card6 = new WidgetScreen();
	public static AboutScreen card7 = new AboutScreen();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 if (!Updater.getLatestVersion().equals(Constants.version)) {
			                new UpdateInfo(Updater.getWhatsNew());
			            }
					 else{
						 File folder = new File("updater");
						 if (folder.exists()) {
								Update.remove(folder);
								folder.delete();
						}
						 folder = new File("dpupdate.zip");
					TrainingApp window = new TrainingApp();
					window.frame.setVisible(true);
					 }
				} catch (Exception e) {
					TrainingApp.showMessageDialog(null, e.toString());
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
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Windows Classic".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		frame = new JFrame("DeckPort | " + Constants.version);
		frame.setBounds(100, 100, 408, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final Button trainButton = new Button("Train Data");
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.setResizable(false);
	
		panel = new JPanel();
		panel.setBounds(0, 0, 390, 377);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		panel.add(card1,"HOME");
		panel.add(card2,"TRAINMENU");
		panel.add(card3,"CARD");
		panel.add(card4,"EXPORT");
		panel.add(card5,"IMPORT");
		panel.add(card6,"WIDGET");
		panel.add(card7,"ABOUT");
		Button homeButton = new Button("Home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "HOME");
			}
		});
		menuBar.add(homeButton);
		
		
		trainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "TRAINMENU");
			}
		});
		trainButton.setEnabled(false);
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
		
		Button widgetButton = new Button("Widgets");
		widgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "WIDGET");
			}
		});
		menuBar.add(widgetButton);
		
		Button aboutButton = new Button("About");
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(panel.getLayout());
			    cl.show(panel, "ABOUT");
			}
		});
		menuBar.add(aboutButton);
		frame.getContentPane().setLayout(null);
		

	}
	public static void showMessageDialog(Component parentComponent, String message) {
		JOptionPane op = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
		TextField myText = new TextField(message);
		if(message.contains("http") && message.contains("deck"))
		{	
			op = new JOptionPane("Deck Published.", JOptionPane.INFORMATION_MESSAGE);
			op.add(myText);
		}
		JDialog dialog = op.createDialog(parentComponent, "DeckPort.com");
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setFocusableWindowState(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}
