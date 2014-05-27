package extracter;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import jna.extra.User32Extra;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LONG;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import extracter.card.Deck;
import extracter.card.DeckItem;

public class RobotManager {
	public static int x;
	public static int y;
	public static boolean scrollDownFlag = true;
	public static void importDeck(Deck deck) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		User32 user32 = User32.INSTANCE;
		HWND hWnd = user32.FindWindow(null, "Hearthstone");
		user32.ShowWindow(hWnd, User32.SW_SHOW);
		user32.SetForegroundWindow(hWnd);
		RECT bounds = new RECT();
		User32Extra.INSTANCE.GetWindowRect(hWnd, bounds);
		Thread.sleep(1000);
		PixelManager.setPixelManager();
		x = bounds.toRectangle().x;
		y = bounds.toRectangle().y;
		List<DeckItem> cards = deck.getCards();
		for(DeckItem deckItem : cards)
		{
			addCardToDeck(x,y,deckItem);
		}
	}

	public static void addCardToDeck(int x, int y, DeckItem deckItem)
			throws InterruptedException {
		searchCard(x, y, deckItem);
	}

	public static void searchCard(int x, int y, DeckItem deckItem)
			throws InterruptedException {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(100);
		// Move mouse to search area
		robot.mouseMove(x + PixelManager.getX_Search(),
				y + PixelManager.getY_Search());
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		// Type card name to search area
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(deckItem.getCard().getName());
		System.out.println(deckItem.getCard().getName());
		clipboard.setContents(stringSelection, new ClipboardOwner() {
			@Override
			public void lostOwnership(Clipboard clipboard, Transferable contents) {

			}
		});
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		// Search card
		Thread.sleep(100);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		// Add card
		for(int i=0;i<deckItem.getCount();i++)
		{
			Thread.sleep(100);
			robot.mouseMove(x + PixelManager.getX_SearchedCard(),
					y + PixelManager.getY_SearchedCard());
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		// Clear Search
		Thread.sleep(100);
		robot.mouseMove(x + PixelManager.getX_Search(),
				y + PixelManager.getY_Search());
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(100);
		robot.keyPress(KeyEvent.VK_DELETE);
		robot.keyRelease(KeyEvent.VK_DELETE);
	}

	public static void scrollDown() {
		try {
			User32 user32 = User32.INSTANCE;
			HWND hWnd = user32.FindWindow(null, "Hearthstone");
			user32.ShowWindow(hWnd, User32.SW_SHOW);
			user32.SetForegroundWindow(hWnd);
			Thread.sleep(1000);
	
			RECT bounds = new RECT();
			User32Extra.INSTANCE.GetWindowRect(hWnd, bounds);
			x = bounds.toRectangle().x;
			y = bounds.toRectangle().y;
	
			PixelManager.setPixelManager();
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robot.mouseMove(x + PixelManager.getX_Scroll(),y + PixelManager.getY_Scroll());
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseWheel(5);
			Thread.sleep(30);
			robot.mouseWheel(5);
			Thread.sleep(30);
			robot.mouseWheel(5);
			Thread.sleep(30);
			robot.mouseWheel(5);
			Thread.sleep(30);
			robot.mouseWheel(5);
			Thread.sleep(1000);
			scrollDownFlag = false;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
	}
	public static Rectangle getWindowSize()
	{
		User32 user32 = User32.INSTANCE;
		HWND hWnd = user32.FindWindow(null, "Hearthstone");
		user32.ShowWindow(hWnd, User32.SW_SHOW);
		user32.SetForegroundWindow(hWnd);
		RECT bounds = new RECT();
		User32Extra.INSTANCE.GetWindowRect(hWnd, bounds);
		return bounds.toRectangle();
	}
}
