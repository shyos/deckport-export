package updater;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Thomas Otero H3R3T1C
 */
public class UpdateInfo extends JFrame{

    private JEditorPane infoPane;
    private JScrollPane scp;
    private JButton ok;
    private JButton cancel;
    private JPanel pan1;
    private JPanel pan2;

    public UpdateInfo(String info) {
        initComponents();
        infoPane.setText(info);
    }

    private void initComponents() {

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("New Update Found");
        pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());

        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        infoPane = new JEditorPane();
        infoPane.setContentType("text/html");

        scp = new JScrollPane();
        scp.setViewportView(infoPane);

        ok = new JButton("Update");
        ok.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        cancel = new JButton("Cancel");
        cancel.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                UpdateInfo.this.dispose();
            }
        });
        pan2.add(ok);
        pan2.add(cancel);
        pan1.add(pan2, BorderLayout.SOUTH);
        pan1.add(scp, BorderLayout.CENTER);
        this.add(pan1);
        pack();
        show();
        this.setSize(300, 200);
    }
    private void update()
    {
    	copyUpdateToOutside();
    	
    	File folder = new File("updater");
    	String[] run = {"java","-jar","updater/update.jar"};
        try {
            Runtime.getRuntime().exec(run);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        System.exit(0);
    }

	private void copyUpdateToOutside() {
		InputStream stream = Updater.class.getResourceAsStream("update.jar");//note that each / is a directory down in the "jar tree" been the jar the root of the tree"
	    if (stream == null) {
	       System.out.println("Bullshit");
	    }
	    OutputStream resStreamOut = null;
	    int readBytes;
	    byte[] buffer = new byte[4096];
	    File folder = new File("updater");
		if (!folder.exists()) {
			folder.mkdir();
		}
	    try {
	        resStreamOut = new FileOutputStream(new File("updater/update.jar"));
	        while ((readBytes = stream.read(buffer)) > 0) {
	            resStreamOut.write(buffer, 0, readBytes);
	        }
	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } finally {
			try {
				stream.close();
				resStreamOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	}

}
