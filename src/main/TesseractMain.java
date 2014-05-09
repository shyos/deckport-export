package main;


import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractMain {
	private static String ocrLanguage = "eng";
	public static void main(String[] args) throws IOException {
		File imageFile = new File("deneme3.png");

		
         
		Tesseract instance = Tesseract.getInstance(); //

		try {
			setupTesseract();
            loadJarDll("liblept168");
            loadJarDll("libtesseract302");
    		WindowCapture WC = new WindowCapture();
    		BufferedImage image = WC.getImage();
    		
			String result = instance.doOCR(image);
    		//String result = instance.doOCR(imageFile);
			System.out.println(result);

		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsatisfiedLinkError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getExtractionFolder() {
            String path = "tmp";
            (new File(path)).mkdirs();
            return path;
	}
	
	public static void showErrorDialog(String message, Throwable e) {
        JFrame frame = new JFrame();
        frame.setFocusableWindowState(true);
        TesseractMain.showMessageDialog(null, message + "\n" + e.getMessage() + "\n\nSee log.txt for details");
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
	public static void setupTesseract() {
        System.out.println("Extracting Tesseract data");
        Tesseract instance = Tesseract.getInstance();
        instance.setDatapath("tessdata");
        instance.setLanguage(ocrLanguage);
	}
	
	public static void copyFileFromJarTo(String jarPath, String outPath) {
		InputStream stream = TesseractMain.class.getResourceAsStream(jarPath);
	    if (stream == null) {
            System.out.println("Exception: Unable to load file from JAR: " + jarPath);
            TesseractMain.showMessageDialog(null, "Exception: Unable to find " + jarPath + " in .jar file\n\nSee log.txt for details");
	    	System.exit(1);
	    } else {
		    OutputStream resStreamOut = null;
		    int readBytes;
		    byte[] buffer = new byte[4096];
		    try {
		        resStreamOut = new FileOutputStream(new File(outPath));
		        while ((readBytes = stream.read(buffer)) > 0) {
		            resStreamOut.write(buffer, 0, readBytes);
		        }
		    } catch (IOException e) {
		    	TesseractMain.showErrorDialog("Error writing file " + outPath, e);
		    } finally {
		        try {
					stream.close();
					resStreamOut.close();
				} catch (IOException e) {
					TesseractMain.showErrorDialog("Error closing stream for " + jarPath, e);
				}
		    }
	    }
	}

	private static void loadJarDll(String name) throws FileNotFoundException, UnsatisfiedLinkError {
 
		String resourcePath = "/lib/" + name + "_" + System.getProperty("sun.arch.data.model") + ".dll";
	    InputStream in = TesseractMain.class.getResourceAsStream(resourcePath);
	    if (in != null) {
		    byte[] buffer = new byte[1024];
		    int read = -1;

		    File outDir = new File(TesseractMain.getExtractionFolder());
		    outDir.mkdirs();
		    String outPath = outDir.getPath() + "/";

		    String outFileName = name.replace("_32", "").replace("_64",  "") + ".dll";

		    FileOutputStream fos = null;
			fos = new FileOutputStream(outPath + outFileName);

		    try {
				while((read = in.read(buffer)) != -1) {
				    fos.write(buffer, 0, read);
				}
				fos.close();
				in.close();

			} catch (IOException e) {
               System.out.println("Error copying DLL " + name + e);
			}
		    try {
                System.loadLibrary(outPath + name);
            } catch (UnsatisfiedLinkError e) {
                System.out.println("UnsatisfiedLinkError loading DLL " + name + e);
                throw e;
		    } catch (Exception e) {
                System.out.println("Error loading DLL " + name + e);
		    }
	    } else {
	    	TesseractMain.showErrorDialog("Error loading " + name, new Exception("Unable to load library from " + resourcePath));
	    }
	}
}