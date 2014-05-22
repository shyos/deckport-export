package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import jna.extra.GDI32Extra;
import jna.extra.User32Extra;
import jna.extra.WinGDIExtra;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HBITMAP;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinGDI.BITMAPINFO;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class WindowCapture extends JFrame {

	private static final int ITERATIONS_FOR_MINIMISE = 8;
	private HWND _windowHandle = null;
	private String _windowHandleId = null;
	private boolean isFullscreen = false;
	private boolean isMinimised = false;
    private int minimisedCount = 0;
	private boolean _isFullScreen(Rectangle rect) {
		// check to make sure Hearthstone's not in full screen
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		return (rect.width >= width && rect.height >= height);
	}
    public BufferedImage capture(HWND hWnd) {

    	HDC hdcWindow = User32.INSTANCE.GetDC(hWnd);
		HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);

		RECT bounds = new RECT();
		User32Extra.INSTANCE.GetClientRect(hWnd, bounds);

		// check to make sure the window's not minimized
		if(bounds.toRectangle().width >= 1024) {
			if(isMinimised) {
				System.out.println("Hearthstone window restored");
				isMinimised = false;
			}

			if(_isFullScreen(bounds.toRectangle())) {
				if(!isFullscreen) {
					System.out.println("Hearthstone running in fullscreen");
					isFullscreen = true;
				}
				return null;
			} else {
				int width = bounds.right - bounds.left;
				int height = bounds.bottom - bounds.top;

				HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height);

				HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
				GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0, 0, WinGDIExtra.SRCCOPY);

				GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
				GDI32.INSTANCE.DeleteDC(hdcMemDC);

				BITMAPINFO bmi = new BITMAPINFO();
				bmi.bmiHeader.biWidth = width;
				bmi.bmiHeader.biHeight = -height;
				bmi.bmiHeader.biPlanes = 1;
				bmi.bmiHeader.biBitCount = 32;
				bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

				Memory buffer = new Memory(width * height * 4);
				GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);

				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);

				GDI32.INSTANCE.DeleteObject(hBitmap);
				User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);
				return image;
			}
		}
		if (!isMinimised) {
            // Hearthstone has brief periods where its window is not displayed, such as during startup and when changing
            // scree size. We don't want to show a warning for these, so we wait a couple of iterations before assuming
            // that the window has been minimised.
            if (minimisedCount < ITERATIONS_FOR_MINIMISE) {
                minimisedCount++;
            } else {
            	System.out.println("Warning! Hearthstone minimized. No detection possible.");
                isMinimised = true;
                minimisedCount = 0;
            }
		}
		return null;

    }

    public static void main(String[] args) throws IOException {
        new WindowCapture();
    }

    BufferedImage image;
    private int x = 820;
    private int y = 84;
    private int w = 50;
    private int h = 19;
    private HWND hWnd;
    public WindowCapture() throws IOException {
        this.hWnd = User32.INSTANCE.FindWindow(null, "Hearthstone");
        captureHwnd();
       
    /*  setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();dene sonra bana
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);*/
    }
    public void captureHwnd(){
    	this.image = capture(hWnd);
    }
    public void showFrame(BufferedImage image)
    {
    	this.image = image;
    	// setDefaultCloseOperation(EXIT_ON_CLOSE);
         pack();
         setExtendedState(MAXIMIZED_BOTH);
         setVisible(true);
    }
    public static BufferedImage invertImage(BufferedImage tempImage) {
        BufferedImage inputFile = tempImage;
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
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 20, 40, null);
    }
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public HWND gethWnd() {
		return hWnd;
	}
	public void sethWnd(HWND hWnd) {
		this.hWnd = hWnd;
	}



}