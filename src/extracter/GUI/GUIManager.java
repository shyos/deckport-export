package extracter.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import main.TesseractMain;
import extracter.Constants;
import extracter.ExtracterMain;


public class GUIManager implements ActionListener{

	private JFrame frame;
	private MainScreen mainScreen;
	private DataGrindScreen grindScreen;
	public GUIManager() {
		try {
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initialize() throws IOException {


		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		frame = new JFrame("Shyug's Data Grind Helper");
		frame.setBounds(640, 300, 700, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainScreen = new MainScreen();
		mainScreen.setBounds(0, 0, 700, 480);
		mainScreen.setVisible(true);
		mainScreen.addActionListeners(this);
		
		grindScreen = new DataGrindScreen();
		grindScreen.setBounds(0, 0, 700, 480);
		grindScreen.setVisible(false);
		grindScreen.addActionListeners(this);
		
		frame.getContentPane().add(mainScreen);
		frame.getContentPane().add(grindScreen);
	}
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == mainScreen.getStartButton())
		{
			mainScreen.setVisible(false);
			grindScreen.setVisible(true);
			ExtracterMain.buildEnvironment();
	
		}
		if(e.getSource() == grindScreen.getConfirmButton())
		{
			int k = Constants.GUICardOrder;
			k = ExtracterMain.saveSingleCard(Constants.GUICardOrder, grindScreen.getCardName().getText());
			
			if(k > Constants.GUICardOrder)
			{
				Constants.GUICardOrder = k;
				grindScreen.getMyLabel().setText((k+1) + ". Card");
				grindScreen.getCardName().setText("");
			}
			
			if(k == ExtracterMain.numberOfCardInDeck)
			{
				ExtracterMain.writeToFile(ExtracterMain.GUICards, "guicards.txt");
				TesseractMain.showMessageDialog(null, " Deckinizden kayit yapabilecegimiz " + ExtracterMain.numberOfCardInDeck + " kart tamamlanmistir. ");
				mainScreen.setVisible(true);
				grindScreen.setVisible(false);
				Constants.GUICardOrder = 0;
				
				grindScreen.getMyLabel().setText("1. Card");
				grindScreen.getCardName().setText("");
			}
		}
	}

}

