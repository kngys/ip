package serenity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import serenity.task.Deadline;
import serenity.task.Event;
import serenity.task.Todo;

public class StorageTest {

    @Test
    public void loadTask_validTodo_success() throws SerenityException {
        Storage storage = new Storage("");
        String taskDescription = "T | 0 | read book";
        assertEquals(new Todo("read book").toString(),
                storage.loadTask(taskDescription).toString());
    }

    @Test
    public void loadTask_validDeadline_success() throws SerenityException {
        Storage storage = new Storage("");
        String taskDescription = "D | 1 | return book | 30 Aug 2024";
        Deadline d = new Deadline("return book", "30/08/2024");
        d.markAsDone();
        assertEquals(d.toString(),
                storage.loadTask(taskDescription).toString());
    }

    @Test
    public void loadTask_validEvent_success() throws SerenityException {
        Storage storage = new Storage("");
        String taskDescription = "E | 0 | attend meeting | 3pm | 5pm";
        Event e = new Event("attend meeting", "3pm", "5pm");
        assertEquals(e.toString(),
                storage.loadTask(taskDescription).toString());
    }

    @Test
    public void loadTask_invalidInput_exceptionThrown() {
        Storage storage = new Storage("");
        String taskDescription = "X | 0 | read book";
        try {
            storage.loadTask("X | 0 | read book");
        } catch (SerenityException e) {
            assertEquals("Error: Task cannot be loaded.",
                    e.getMessage());
        }
    }



}
