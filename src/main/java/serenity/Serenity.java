package serenity;

import java.io.IOException;

import serenity.task.TaskList;

/**
 * Represents a chatbot used for task management.
 */
@SuppressWarnings("checkstyle:Regexp")
public class Serenity {

    private static final String DEFAULT_FILE_PATH = "./data/serenity.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Serenity object.
     *
     * @param filePath The path of file where data is stored.
     */
    public Serenity(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadFile());
        } catch (SerenityException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        } catch (IOException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * Constructs a Serenity object with default file path.
     */
    public Serenity() {
        ui = new Ui();
        storage = new Storage(DEFAULT_FILE_PATH);
        try {
            tasks = new TaskList(storage.loadFile());
        } catch (SerenityException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        } catch (IOException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * Generates response to user's input in chat.
     *
     * @param input User's input
     */
    public String getResponse(String input) {
        return Parser.parseToString(input, tasks, storage);
    }

    /**
     * Generates welcome message.
     *
     * @return Welcome message.
     */
    public String welcome() {
        return "Hi, I'm Serenity!\n" + "What can I do for you?";
    }


    /**
     * Runs the Serenity chatbot
     */
    public void run() {

        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                Parser.parse(input, tasks, ui, storage);
                isExit = Parser.isExit(input);
            } catch (SerenityException | IOException e) {
                ui.showMessage(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        new Serenity("./data/serenity.txt").run();
    }

}
