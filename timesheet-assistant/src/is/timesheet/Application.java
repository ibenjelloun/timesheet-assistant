package is.timesheet;

import java.io.File;
import java.io.IOException;

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
		// TODO find a better way to specify the destination
		File outputFile = new File("s:/timesheet-assistant.csv");
		
		// Initialise the writer to the destination csv file.
		TimesheetWriter writer = new TimesheetWriter(outputFile);
		
		// Start the Graphical Interface.
		AssistantGUI assistant = new AssistantGUI();
		
		try {
			// Starts the workstation monitoring.
			new WorkstationLockListening(assistant, writer);
		} catch (IOException e) {
			assistant.log(e);
		}
	}

}
