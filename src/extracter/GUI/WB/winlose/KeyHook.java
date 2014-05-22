package extracter.GUI.WB.winlose;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

/** Sample implementation of a low-level keyboard hook on W32. */
public class KeyHook {
    private static volatile boolean quit;
    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
	private static int prevKey = 0;
	private static int currKey = 0;
    public static void main(String[] args) {
        final User32 lib = User32.INSTANCE;
        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        keyboardHook = new LowLevelKeyboardProc() {
            public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
                if (nCode >= 0) {
                    switch(wParam.intValue()) {
                    case WinUser.WM_KEYDOWN:
	                    {
	                    	currKey = info.vkCode;
	        				if(prevKey == 87 && currKey == 49)
	        				{
	        					System.out.println(ScoreTrackerFrame.winCount);
	        					ScoreTrackerFrame.lblWin.setText(++ScoreTrackerFrame.winCount+"");
	        				}
	        				else if(prevKey == 87 && currKey == 50)
	        				{
	        					ScoreTrackerFrame.lblWin.setText(--ScoreTrackerFrame.winCount+"");
	        				}
	        				else if(prevKey == 76 && currKey == 49)
	        				{
	        					ScoreTrackerFrame.lblLose.setText(++ScoreTrackerFrame.loseCount+"");
	        				}
	        				else if(prevKey == 76 && currKey == 50)
	        				{
	        					ScoreTrackerFrame.lblLose.setText(--ScoreTrackerFrame.loseCount+"");
	        				}
	                        prevKey = info.vkCode;
	                        if (info.vkCode == 81) {
	                            quit = true;
	                        }
	                    }
                    }
                }
                return lib.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
            }
        };
        hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
        System.out.println("Keyboard hook installed, type anywhere, 'q' to quit");
        new Thread() {
            public void run() {
                while (!quit) {
                    try { Thread.sleep(10); } catch(Exception e) { }
                }
                System.err.println("unhook and exit");
                lib.UnhookWindowsHookEx(hhk);        
            }
        }.start();

        // This bit never returns from GetMessage
        int result;
        MSG msg = new MSG();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            }
            else {
                System.err.println("got message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }
        lib.UnhookWindowsHookEx(hhk);
    }
	public static void close() {
		quit = true;
	}
}