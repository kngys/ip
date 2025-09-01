package serenity;

import java.io.IOException;

import serenity.task.Task;
import serenity.task.TaskList;


/**
 * Represents a parser that parses user inputs and executes the actions corresponding to the command.
 */
public class Parser {

    /**
     * Parses user input, carries out corresponding actions and
     * displays corresponding message.
     *
     * @param input User's input.
     * @param tasks List of tasks.
     * @param ui User interface.
     * @param storage Data storage.
     * @throws SerenityException If user input is invalid and task cannot be created.
     * @throws IOException If there is an issue writing to file that stores data.
     */
    public static void parse(String input, TaskList tasks, Ui ui, Storage storage)
            throws SerenityException, IOException {
        String[] parts = input.split(" ", 2);
        String command = parts[0].strip();
        String message;
        Task t;

        switch (command) {
        case "bye":
            ui.showGoodbye();
            break;
        case "list":
            ui.showTaskList(tasks);
            break;
        case "todo", "deadline", "event":
            t = TaskList.createTask(input);
            message = tasks.addTask(t);
            ui.showMessage(message);
            storage.saveTask(t);
            break;
        case "mark", "unmark":
            message = tasks.changeStatus(input);
            ui.showMessage(message);
            storage.writeToFile(tasks);
            break;
        case "delete":
            message = tasks.deleteTask(input);
            ui.showMessage(message);
            storage.writeToFile(tasks);
            break;
        case "find":
            message = tasks.findTask(input);
            ui.showMessage(message);
            break;
        case "update":
            message = tasks.updateTask(input);
            ui.showMessage(message);
            storage.writeToFile(tasks);
            break;
        default:
            ui.showMessage("Error: Invalid task.");
            break;
        }

    }

    /**
     * Parses user input, carries out corresponding actions and
     * returns message to be displayed.
     *
     * @param input User's input
     * @param tasks List of tasks.
     * @param storage Data storage
     * @return Message to be displayed.
     */
    public static String parseToString(String input, TaskList tasks, Storage storage) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].strip();
        String message;
        Task t;

        try {
            switch (command) {
            case "bye":
                message = "Goodbye. Hope to see you again soon!";
                break;
            case "list":
                message = tasks.toString();
                break;
            case "todo", "deadline", "event":
                t = TaskList.createTask(input);
                message = tasks.addTask(t);
                storage.saveTask(t);
                break;
            case "mark", "unmark":
                message = tasks.changeStatus(input);
                storage.writeToFile(tasks);
                break;
            case "delete":
                message = tasks.deleteTask(input);
                storage.writeToFile(tasks);
                break;
            case "find":
                message = tasks.findTask(input);
                break;
            case "update":
                message = tasks.updateTask(input);
                storage.writeToFile(tasks);
                break;
            default:
                message = "Error: Invalid task.";
                break;
            }
        } catch (SerenityException | IOException e) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * Checks if command is to exit chatbot.
     *
     * @param input User's input.
     * @return True if the command is bye.
     */
    public static boolean isExit(String input) {
        return input.startsWith("bye");
    }

}
