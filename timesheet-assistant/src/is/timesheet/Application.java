package is.timesheet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import is.timesheet.assistant.AssistantGUI;
import is.timesheet.assistant.data.TimesheetWriter;


/**
 * The Class Application.
 */
public class Application {

	/**
	 * The main method.
	 *
	 * @param args the arguments (not used)
	 */
	public static void main(String[] args) {
		String path = Application.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File outputFile;
		
		try {
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			outputFile = new File(decodedPath + "/timesheet-assistant.csv");
		} catch (UnsupportedEncodingException e1) {
			outputFile = new File("c:/timesheet-assistant.csv");
		}
		
		// Initialise the writer to the destination csv file.
		TimesheetWriter writer = new TimesheetWriter(outputFile);
		
		// Start the Graphical Interface.
		AssistantGUI assistant = new AssistantGUI();
		
		assistant.log("Output file : " + outputFile.getAbsolutePath());
		
		try {
			// Starts the workstation monitoring.
			new WorkstationLockListening(assistant, writer);
		} catch (IOException e) {
			assistant.log(e);
		}
	}

}
