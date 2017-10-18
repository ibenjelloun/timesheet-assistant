package is.timesheet.assistant.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Class TimesheetWriter.
 */
public class TimesheetWriter {
	
	/** The output file. */
	private File outputFile;

	/**
	 * Instantiates a new timesheet writer.
	 *
	 * @param outputFile the output file
	 */
	public TimesheetWriter(File outputFile) {
		super();
		this.outputFile = outputFile;
	}
	
	/**
	 * Log.
	 *
	 * @param line the line
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void log(Object line) throws IOException {
		PrintWriter output = new PrintWriter(new FileWriter(outputFile, true));
		output.println(line.toString());
		output.close();
	}
}
