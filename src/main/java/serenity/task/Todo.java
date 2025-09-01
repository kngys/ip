package serenity.task;

/**
 * Represents a type of Task with only a basic description.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo.
     *
     * @param description Description of Task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns string representation of Todo.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns string representation of Todo to save as data.
     *
     * @return String representation to save as data.
     */
    @Override
    public String formatData() {
        return "T | " + super.formatData();
    }
}
