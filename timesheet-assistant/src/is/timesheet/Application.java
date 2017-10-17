package is.timesheet;

import java.io.File;
import java.io.IOException;

import is.timesheet.assistant.Assistant;

public class Application {

	public static void main(String[] args) {
		File outPutFile = new File("s:/timesheet-assistant.csv");
		Assistant assistant = new Assistant();
		try {
			new WorkstationLockListening(assistant, outPutFile);
		} catch (IOException e) {
			assistant.log(e);
		}
	}

}
