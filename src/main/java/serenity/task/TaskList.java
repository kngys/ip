package serenity.task;

import java.util.ArrayList;

import serenity.SerenityException;

/**
 * Represents a list of tasks, where tasks can be added, deleted
 * and marked as done or undone.
 */
public class TaskList {

    protected ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with no existing tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with existing tasks.
     *
     * @param tasks ArrayList of existing tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates task of specified type with given task description.
     *
     * @param input Task description specifying type of task.
     * @return Task of specified type with given task description.
     * @throws SerenityException If type of task is not specified.
     */
    public static Task createTask(String input) throws SerenityException {
        Task t;

        if (input.startsWith("todo")) {
            t = createTodo(input);
        } else if (input.startsWith("deadline")) {
            t = createDeadline(input);
        } else if (input.startsWith("event")) {
            t = createEvent(input);
        } else {
            throw new SerenityException("Error: Type of task is invalid.");
        }

        return t;
    }

    /**
     * Creates Event with given task description.
     *
     * @param input Task description.
     * @return Event with given task description.
     * @throws SerenityException If task description is missing.
     */
    private static Task createEvent(String input) throws SerenityException {
        String[] components = input.split(" ");
        if (components.length == 1) {
            throw new SerenityException("Error: The description of an event cannot be empty.");
        }
        String taskDescription = input.split(" ", 2)[1];
        if (!taskDescription.contains("/from") || !taskDescription.contains("/to")) {
            throw new SerenityException("Error: Missing event info.");
        }
        String[] parts = taskDescription.split("/from");
        if (parts.length == 1) {
            throw new SerenityException("Error: Missing event info.");
        }
        String[] timings = parts[1].split("/to");
        if (timings.length == 1 || parts[0].isBlank() || timings[0].isBlank()
                || timings[1].isBlank()) {
            throw new SerenityException("Error: Missing event info.");
        }
        return new Event(parts[0].strip(), timings[0].strip(), timings[1].strip());
    }

    /**
     * Creates Deadline with given task description.
     *
     * @param input Task description.
     * @return Deadline with given task description.
     * @throws SerenityException If task description is missing.
     */
    private static Task createDeadline(String input) throws SerenityException {
        String[] components = input.split(" ");
        if (components.length == 1) {
            throw new SerenityException("Error: The description of a deadline cannot be empty.");
        }
        String taskDescription = input.split(" ", 2)[1];
        if (!taskDescription.contains("/by")) {
            throw new SerenityException("Error: Missing deadline info.");
        }
        String[] parts = taskDescription.split("/by");
        if (parts[0].isBlank() || parts[1].isBlank()) {
            throw new SerenityException("Error: Missing deadline info.");
        }
        return new Deadline(parts[0].strip(), parts[1].strip());
    }

    /**
     * Creates Todo with given task description.
     *
     * @param input Task description.
     * @return Deadline with given task description.
     * @throws SerenityException If task description is missing.
     */
    private static Todo createTodo(String input) throws SerenityException {
        String[] components = input.split(" ");
        if (components.length == 1) {
            throw new SerenityException("Error: The description of a todo cannot be empty.");
        }
        String taskDescription = input.split(" ", 2)[1];
        return new Todo(taskDescription.strip());
    }

    /**
     * Adds task to task list.
     *
     * @param t Task to be added.
     * @return Message to be displayed when task is added.
     */
    public String addTask(Task t) {
        tasks.add(t);
        assert tasks.contains(t) : "Task failed to be added to task list";

        String numOfTasks = tasks.size() == 1 ? "task" : "tasks";
        return ("Got it. I've added this task:\n" + t
                + "\nNow you have " + tasks.size() + " " + numOfTasks + " in the list.");

    }

    /**
     * Deletes task from task list.
     *
     * @param input Command containing index of task to delete.
     * @return Message to be displayed when task is deleted.
     * @throws SerenityException If task index is not specified.
     */
    public String deleteTask(String input) throws SerenityException {

        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new SerenityException("Error: Missing index.");
        }

        int index = Integer.parseInt(input.substring(7)) - 1;
        if (index < 0 || index > tasks.size() - 1) {
            throw new SerenityException("Error: Index is out of bounds.");
        }
        Task t = tasks.get(index);
        tasks.remove(index);

        String numOfTasks = tasks.size() == 1 ? "task" : "tasks";
        return ("Noted. I've removed this task:\n" + t
                + "\nNow you have " + tasks.size() + " " + numOfTasks + " in the list.");
    }

    /**
     * Changes status of task.
     *
     * @param input Command to containing index of task to change status.
     * @return Message to be displayed to user when task status is changed.
     * @throws SerenityException If task index is not specified.
     */
    public String changeStatus(String input) throws SerenityException {

        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new SerenityException("Error: Missing index.");
        }

        String message;

        if (input.startsWith("mark")) {
            int index = Integer.parseInt(input.substring(5)) - 1;
            if (index < 0 || index > tasks.size() - 1) {
                throw new SerenityException("Error: Index is out of bounds.");
            }
            Task t = tasks.get(index);
            t.markAsDone();
            message = "Nice! I've marked this task as done:\n" + t;
        } else if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            if (index < 0 || index > tasks.size() - 1) {
                throw new SerenityException("Error: Index is out of bounds.");
            }
            Task t = tasks.get(index);
            t.markAsNotDone();
            message = "OK, I've marked this task as not done yet:\n" + t;
        } else {
            throw new SerenityException("Error: Type of task is not specified.");
        }
        return message;
    }

    /**
     * Returns String representation of TaskList of matching tasks.
     * Matching tasks are identified by the given keyword.
     *
     * @param input keyword to search tasks for
     * @return String representation of TaskList of matching tasks
     * @throws SerenityException If keyword is missing.
     */
    public String findTask(String input) throws SerenityException {

        String[] parts = input.split(" ");
        if (parts.length == 1) {
            throw new SerenityException("Error: Missing keyword.");
        }

        String keyWord = parts[1].strip();
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().contains(keyWord)) {
                foundTasks.add(tasks.get(i));
            }
        }

        String message = "Here are the matching tasks in your list:";
        for (int j = 0; j < foundTasks.size(); j++) {
            int index = j + 1;
            message += "\n" + index + ". " + foundTasks.get(j);
        }
        return message;
    }

    /**
     * Updates task at specified index.
     *
     * @param input User's input.
     * @return Message to be displayed to user when task is updated.
     * @throws SerenityException If update info or task index is missing.
     */
    public String updateTask(String input) throws SerenityException {
        String[] parts = input.split("/update");
        if (parts.length == 1) {
            throw new SerenityException("Error: Missing info to update task with.");
        }
        String[] command = parts[0].split(" ");
        if (command.length == 1) {
            throw new SerenityException("Error: Missing index.");
        }
        int index = Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > tasks.size() - 1) {
            throw new SerenityException("Error: Index is out of bounds.");
        }
        String taskDescription = parts[1].strip();
        Task newTask = TaskList.createTask(taskDescription);
        Task t = tasks.set(index, newTask);

        return "Task has been updated! Updated task: \n " + newTask;
    }

    /**
     * Returns string representation of TaskList.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        String message = "Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            int index = i + 1;
            message += "\n" + index + ". " + tasks.get(i);
        }
        return message;
    }

    /**
     * Returns string representation of TaskList to save as data.
     *
     * @return String representation to save as data.
     */
    public String formatData() {
        String data = "";
        for (int i = 0; i < tasks.size(); i++) {
            data += tasks.get(i).formatData() + "\n";
        }
        return data;
    }
}
