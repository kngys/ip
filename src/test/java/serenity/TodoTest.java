package serenity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import serenity.task.Todo;

public class TodoTest {

    @Test
    public void testToString() {
        assertEquals("[T][ ] read book", new Todo("read book").toString());
    }

    @Test
    public void testFormatData() {
        assertEquals("T | 0 | read book", new Todo("read book").formatData());
    }

}
