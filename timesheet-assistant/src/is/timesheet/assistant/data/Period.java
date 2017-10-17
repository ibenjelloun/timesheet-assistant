package is.timesheet.assistant.data;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Period {
	
	public enum PeriodType {
		PAUSE,
		WORK
	}
	
	private ZonedDateTime start;
	
	private ZonedDateTime end;
	
	private PeriodType type;
	
	private long durationSeconds;
	
	private long durationMinutes;
	
	private String currentTask;

	public Period() {
		super();
		this.initStart();
	}

	public ZonedDateTime getStart() {
		return start;
	}

	public void initStart() {
		this.start = ZonedDateTime.now();
	}
	
	public ZonedDateTime getEnd() {
		return end;
	}
	
	public void initEnd() {
		this.end = ZonedDateTime.now();
	}

	public void setType(PeriodType type) {
		this.type = type;
	}

	public PeriodType getType() {
		return type;
	}

	public long getDurationSeconds() {
		return durationSeconds;
	}
	
	public long getDurationMinutes() {
		return durationMinutes;
	}
	
	public void initDuration() {
		Duration duration = Duration.between(start, end);
		this.durationSeconds = duration.getSeconds();
		this.durationMinutes = duration.toMinutes();
	}
	
	public String getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}

	public void reset() {
		this.initStart();
		this.end = null;
		this.durationSeconds = 0;
		this.durationMinutes = 0;
		this.type = null;
		this.currentTask = null;
	}
	
	@Override
	public String toString() {
		return "" + durationMinutes + ";" + type + ";" + currentTask + ";" + durationSeconds + ";" + start + ";" + end;
	}
	
	
}