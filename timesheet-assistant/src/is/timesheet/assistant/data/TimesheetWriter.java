package is.timesheet.assistant.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TimesheetWriter {
	
	private File outputFile;

	public TimesheetWriter(File outputFile) {
		super();
		this.outputFile = outputFile;
	}
	
	public void log(Object line) throws IOException {
		PrintWriter output = new PrintWriter(new FileWriter(outputFile, true));
		output.println(line.toString());
		output.close();
	}
}
