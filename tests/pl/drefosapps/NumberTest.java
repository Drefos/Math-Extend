package pl.drefosapps;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NumberTest {
    Number number = new Number("A", 12);

    @Test
    public void shouldReturnLetterATest() {
        assertEquals("A", number.getLabel());
    }

    @Test
    public void shouldReturn12Test() {
        assertEquals(12.0, number.getValue());
    }

    @Test
    public void shouldReturnAeq12Test() {
        assertEquals("A = 12.0", number.toString());
    }
}
