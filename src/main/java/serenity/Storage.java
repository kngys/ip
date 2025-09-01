package serenity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import serenity.task.Deadline;
import serenity.task.Event;
import serenity.task.Task;
import serenity.task.TaskList;
import serenity.task.Todo;


/**
 * Handles the storing, loading and saving of tasks to data file.
 */
public class Storage {

    protected String filePath;

    /**
     * Constructs a Storage object.
     *
     * @param filePath The path of file where data is stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks stored in data file to chatbot.
     *
     * @return ArrayList of tasks loaded from data file.
     * @throws IOException If there are issues with creating directory or file
     * @throws SerenityException If there are issues with loading a task.
     */
    public ArrayList<Task> loadFile() throws IOException, SerenityException {
        File f = new File(filePath);
        File directory = new File(f.getParentFile().getAbsolutePath());
        directory.mkdirs();
        if (!f.exists()) {
            Files.createFile(Paths.get(filePath));
        }

        assert f.exists() : "File should exist.";

        Scanner sc = new Scanner(f);
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNext()) {
            tasks.add(loadTask(sc.nextLine()));
        }
        sc.close();
        return tasks;
    }

    /**
     * Converts string representation of task stored in data file to a Task.
     *
     * @param taskDescription String representation of task stored in data file.
     * @return Task created with the task description.
     * @throws SerenityException If task description is invalid and task cannot be created.
     */
    public Task loadTask(String taskDescription) throws SerenityException {
        String[] parts = taskDescription.split("\\|");
        Task t;

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].strip();
        }

        String taskType = parts[0];

        switch (taskType) {
        case "T":
            t = new Todo(parts[2]);
            break;
        case "D":
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            LocalDate date = LocalDate.parse(parts[3], formatter);
            String by = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            t = new Deadline(parts[2], by);
            break;
        case "E":
            t = new Event(parts[2], parts[3], parts[4]);
            break;
        default:
            throw new SerenityException("Error: Task cannot be loaded.");
        }

        if (parts[1].equals("1")) {
            t.markAsDone();
        }

        return t;
    }

    /**
     * Writes to data file by appending text to existing content.
     *
     * @param textToAppend Text to be appended.
     * @throws IOException If there are issues accessing file.
     */
    public void appendToFile(String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAppend + "\n");
        fw.close();
    }

    /**
     * Saves task to data file.
     *
     * @param t Task to be saved to file.
     * @throws IOException If there are issues appending text to file.
     */
    public void saveTask(Task t) throws IOException {
        String textToAppend = t.formatData();
        appendToFile(textToAppend);
    }

    /**
     * Writes to file by overwriting existing content.
     *
     * @param tasks Tasks to be saved to file.
     * @throws IOException If there are issues writing to file.
     */
    public void writeToFile(TaskList tasks) throws IOException {
        //overwrite
        FileWriter fw = new FileWriter(filePath);
        fw.write(tasks.formatData());
        fw.close();
    }

}
