package is.timesheet.assistant.data;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * The Class Period.
 */
public class Period {
	
	/**
	 * The Enum PeriodType.
	 */
	public enum PeriodType {
		
		/** The pause. */
		PAUSE,
		
		/** The work. */
		WORK,
		
		/** The very long pause. */
		VERY_LONG_PAUSE,
		
		/** The long pause. */
		LONG_PAUSE,
		
		/** The short pause. */
		SHORT_PAUSE
	}
	
	/** The start. */
	private ZonedDateTime start;
	
	/** The end. */
	private ZonedDateTime end;
	
	/** The type. */
	private PeriodType type;
	
	/** The duration seconds. */
	private long durationSeconds;
	
	/** The duration minutes. */
	private long durationMinutes;
	
	/** The current task. */
	private String currentTask;

	/**
	 * Instantiates a new period.
	 */
	public Period() {
		super();
		this.initStart();
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public ZonedDateTime getStart() {
		return start;
	}

	/**
	 * Inits the start.
	 */
	public void initStart() {
		this.start = ZonedDateTime.now();
	}
	
	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public ZonedDateTime getEnd() {
		return end;
	}
	
	/**
	 * Inits the end.
	 */
	public void initEnd() {
		this.end = ZonedDateTime.now();
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(PeriodType type) {
		this.type = type;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public PeriodType getType() {
		return type;
	}

	/**
	 * Gets the duration seconds.
	 *
	 * @return the duration seconds
	 */
	public long getDurationSeconds() {
		return durationSeconds;
	}
	
	/**
	 * Gets the duration minutes.
	 *
	 * @return the duration minutes
	 */
	public long getDurationMinutes() {
		return durationMinutes;
	}
	
	/**
	 * Inits the duration.
	 */
	public void initDuration() {
		Duration duration = Duration.between(start, end);
		this.durationSeconds = duration.getSeconds();
		this.durationMinutes = duration.toMinutes();
		initDetailedPause();
	}

	/**
	 * Inits the detailed pause.
	 */
	private void initDetailedPause() {
		if (this.type == PeriodType.PAUSE) {
			if(this.durationMinutes > 180) {
				this.type = PeriodType.VERY_LONG_PAUSE;
			} else if (this.durationMinutes > 30) {
				this.type = PeriodType.LONG_PAUSE;
			} else if (this.durationMinutes <= 15){
				this.type = PeriodType.SHORT_PAUSE;
			}
		}
	}
	
	/**
	 * Gets the current task.
	 *
	 * @return the current task
	 */
	public String getCurrentTask() {
		return currentTask;
	}

	/**
	 * Sets the current task.
	 *
	 * @param currentTask the new current task
	 */
	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		this.initStart();
		this.end = null;
		this.durationSeconds = 0;
		this.durationMinutes = 0;
		this.type = null;
		this.currentTask = null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + durationMinutes + ";" + type + ";" + currentTask + ";" + durationSeconds + ";" + start + ";" + end;
	}
	
	
}