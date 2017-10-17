package is.timesheet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.platform.win32.WinUser.WNDCLASSEX;
import com.sun.jna.platform.win32.WinUser.WindowProc;
import com.sun.jna.platform.win32.Wtsapi32;

import is.timesheet.assistant.Assistant;
import is.timesheet.assistant.data.Period;

/**
 * Code got from a stackoverflow thread :
 * https://stackoverflow.com/questions/10228145/how-to-detect-workstation-system-screen-lock-unlock-in-windows-os-using-java
 *
 */
public class WorkstationLockListening implements WindowProc
{
	
	private Period currentPeriod = new Period();
	
	private Assistant assistant;

	private BufferedWriter timeSheetWriter;

    /**
     * Instantiates a new win32 window test.
     * @param assistant 
     * @param outPutFile 
     * @throws IOException 
     */
    public WorkstationLockListening(Assistant assistant, File outPutFile) throws IOException
    {
    	this.assistant = assistant;
    	this.timeSheetWriter = new BufferedWriter(new FileWriter(outPutFile));
    	
        // define new window class
        final WString windowClass = new WString("MyWindowClass");
        final HMODULE hInst = Kernel32.INSTANCE.GetModuleHandle("");

        WNDCLASSEX wClass = new WNDCLASSEX();
        wClass.hInstance = hInst;
        wClass.lpfnWndProc = WorkstationLockListening.this;
        wClass.lpszClassName = windowClass;

        // register window class
        User32.INSTANCE.RegisterClassEx(wClass);
        getLastError();

        // create new window
        final HWND hWnd = User32.INSTANCE.CreateWindowEx(User32.WS_EX_TOPMOST, windowClass, "'TimeTracker hidden helper window to catch Windows events", 0, 0, 0, 0, 0, null, // WM_DEVICECHANGE contradicts parent=WinUser.HWND_MESSAGE
                null, hInst, null);

        getLastError();
        System.out.println("window sucessfully created! window hwnd: " + hWnd.getPointer().toString());

        Wtsapi32.INSTANCE.WTSRegisterSessionNotification(hWnd, Wtsapi32.NOTIFY_FOR_THIS_SESSION);

        MSG msg = new MSG();
        while (User32.INSTANCE.GetMessage(msg, hWnd, 0, 0) != 0)
        {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessage(msg);
        }

            /// This code is to clean at the end. You can attach it to your custom application shutdown listener
            Wtsapi32.INSTANCE.WTSUnRegisterSessionNotification(hWnd);
            User32.INSTANCE.UnregisterClass(windowClass, hInst);
            User32.INSTANCE.DestroyWindow(hWnd);
            System.out.println("program exit!");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sun.jna.platform.win32.User32.WindowProc#callback(com.sun.jna.platform .win32.WinDef.HWND, int, com.sun.jna.platform.win32.WinDef.WPARAM, com.sun.jna.platform.win32.WinDef.LPARAM)
     */
    public LRESULT callback(HWND hwnd, int uMsg, WPARAM wParam, LPARAM lParam)
    {
        switch (uMsg)
        {
            case WinUser.WM_DESTROY:
            {
                User32.INSTANCE.PostQuitMessage(0);
                return new LRESULT(0);
            }
            case WinUser.WM_SESSION_CHANGE:
            {
                this.onSessionChange(wParam, lParam);
                return new LRESULT(0);
            }
            default:
                return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
        }
    }

    /**
     * Gets the last error.
     * 
     * @return the last error
     */
    public int getLastError()
    {
        int rc = Kernel32.INSTANCE.GetLastError();

        if (rc != 0)
            System.out.println("error: " + rc);

        return rc;
    }

    /**
     * On session change.
     * 
     * @param wParam
     *            the w param
     * @param lParam
     *            the l param
     */
    protected void onSessionChange(WPARAM wParam, LPARAM lParam)
    {
        switch (wParam.intValue())
        {
            case Wtsapi32.WTS_SESSION_LOCK:
            {
                this.onMachineLocked(lParam.intValue());
                break;
            }
            case Wtsapi32.WTS_SESSION_UNLOCK:
            {
                this.onMachineUnlocked(lParam.intValue());
                break;
            }
        }
    }

    /**
     * On machine locked.
     * 
     * @param sessionId
     *            the session id
     */
    protected void onMachineLocked(int sessionId)
    {
        System.out.println("Machine locked right now!");
        this.currentPeriod.initEnd();
        this.currentPeriod.initDuration();
        this.currentPeriod.setType(Period.PeriodType.WORK);
        this.currentPeriod.setCurrentTask(this.assistant.getCurrentTask());
        System.out.println(this.currentPeriod);
        this.assistant.log(this.currentPeriod);
        try {
			this.timeSheetWriter.write(this.currentPeriod.toString());
		} catch (IOException e) {
			this.assistant.log(e);
		}
        this.currentPeriod.reset();
    }

    /**
     * On machine unlocked.
     * 
     * @param sessionId
     *            the session id
     */
    protected void onMachineUnlocked(int sessionId)
    {
        System.out.println("Machine unlocked right now!");
        this.currentPeriod.initEnd();
        this.currentPeriod.initDuration();
        this.currentPeriod.setType(Period.PeriodType.PAUSE);
        this.currentPeriod.setCurrentTask(this.assistant.getCurrentTask());
        System.out.println(this.currentPeriod);
        try {
			this.timeSheetWriter.write(this.currentPeriod.toString());
		} catch (IOException e) {
			this.assistant.log(e);
		}
        this.assistant.log(this.currentPeriod);
        this.currentPeriod.reset();
    }
}