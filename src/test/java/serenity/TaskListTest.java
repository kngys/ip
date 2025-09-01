package serenity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import serenity.task.*;

public class TaskListTest {

    @Test
    public void createTask_invalidTodo_exceptionThrown() {
        try {
            TaskList.createTask("todo ");
        } catch (SerenityException e) {
            assertEquals("Error: The description of a todo cannot be empty.",
                    e.getMessage());
        }
    }
    @Test
    public void createTask_validTodo_success() throws SerenityException {
        Task t = TaskList.createTask("todo read book");
        assertEquals(new Todo("read book").toString(), t.toString());
    }

    @Test
    public void createTask_invalidTaskType_exceptionThrown() {
        try {
            TaskList.createTask("read book");
        } catch (SerenityException e) {
            assertEquals("Error: Type of task is invalid.",
                    e.getMessage());
        }

    }

    @Test
    public void addTask_validDeadline_success() throws SerenityException{
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Deadline t = new Deadline("return book", "30/08/2024");
        String expected = "Got it. I've added this task:\n"
                + "[D][ ] return book (by: 30 Aug 2024)\n"
                + "Now you have 1 task in the list.";
        String actual = taskList.addTask(t);
        assertEquals(expected, actual);
    }

    @Test
    public void changeStatus_validIndex_success() throws SerenityException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Todo undoneTask = new Todo("read book");
        taskList.addTask(undoneTask);
        taskList.changeStatus("mark 1");
        Todo doneTask = new Todo("read book");
        doneTask.markAsDone();
        assertEquals(doneTask.toString(), tasks.get(0).toString());
    }

    @Test
    public void changeStatus_missingIndex_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Todo task = new Todo("read book");
        taskList.addTask(task);
        try {
            taskList.changeStatus("mark ");
        } catch (SerenityException e) {
            assertEquals("Error: Missing index.",
                    e.getMessage());
        }
    }

    @Test
    public void changeStatus_invalidIndex_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Todo task = new Todo("read book");
        taskList.addTask(task);
        try {
            taskList.changeStatus("mark 2");
        } catch (SerenityException e) {
            assertEquals("Error: Index is out of bounds.",
                    e.getMessage());
        }
    }

    @Test
    public void updateTask_validTodo_success() throws SerenityException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Todo task = new Todo("read book");
        taskList.addTask(task);
        Todo updatedTask = new Todo("read English Book");
        taskList.updateTask("update 1 /update todo read English Book");
        assertEquals(updatedTask.toString(), tasks.get(0).toString());
    }

    @Test
    public void updateTask_Deadline_success() throws SerenityException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Deadline task = new Deadline("write essay", "20/09/2024");
        taskList.addTask(task);
        Deadline updatedTask = new Deadline("write essay", "21/09/2024");
        taskList.updateTask("update 1 /update deadline write essay /by 21/09/2024");
        assertEquals(updatedTask.toString(), tasks.get(0).toString());
    }

    @Test
    public void updateTask_Event_success() throws SerenityException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Event task = new Event("project meeting", "2pm", "4pm");
        taskList.addTask(task);
        Event updatedTask = new Event("project meeting", "3pm", "5pm");
        taskList.updateTask("update 1 /update event project meeting /from 3pm /to 5pm");
        assertEquals(updatedTask.toString(), tasks.get(0).toString());
    }

    @Test
    public void updateTask_missingIndex_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Todo task = new Todo("read book");
        taskList.addTask(task);
        try {
            taskList.updateTask("update /update todo read English Book");
        } catch (SerenityException e) {
            assertEquals("Error: Missing index.",
                    e.getMessage());
        }
    }

    @Test
    public void updateTask_invalidIndex_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        Todo task = new Todo("read book");
        taskList.addTask(task);
        try {
            taskList.updateTask("update 2 /update todo read English Book");
        } catch (SerenityException e) {
            assertEquals("Error: Index is out of bounds.",
                    e.getMessage());
        }
    }

}
