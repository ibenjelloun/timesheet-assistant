package is.timesheet;

import java.io.File;
import java.io.IOException;

import is.timesheet.assistant.Assistant;
import is.timesheet.assistant.data.TimesheetWriter;

public class Application {

	public static void main(String[] args) {
		File outputFile = new File("s:/timesheet-assistant.csv");
		TimesheetWriter writer = new TimesheetWriter(outputFile);
		Assistant assistant = new Assistant();
		try {
			new WorkstationLockListening(assistant, writer);
		} catch (IOException e) {
			assistant.log(e);
		}
	}

}
