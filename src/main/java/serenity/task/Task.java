package serenity.task;

/**
 * Represents a general task with a description that can be marked done or undone.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task.
     *
     * @param description Description of task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }


    /**
     * Returns string representation of status of task.
     *
     * @return String representation of status of task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks task as undone.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns string representation of a Task.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns string representation of Task to save as data.
     *
     * @return String representation to save as data.
     */
    public String formatData() {
        int i = this.isDone ? 1 : 0;
        return i + " | " + this.description;
    }

}
