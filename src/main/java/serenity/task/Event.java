package serenity.task;

/**
 * Represents a type of Task that has start and end timings.
 */
public class Event extends Task {

    protected String start;
    protected String end;


    /**
     * Constructs an Event.
     *
     * @param description Description of task.
     * @param start Start time of task.
     * @param end End time of task.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns string representation of Event.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    /**
     * Returns string representation of Event to save as data.
     *
     * @return String representation to save as data.
     */
    @Override
    public String formatData() {
        return "E | " + super.formatData() + " | " + this.start + " | " + this.end;
    }
}
