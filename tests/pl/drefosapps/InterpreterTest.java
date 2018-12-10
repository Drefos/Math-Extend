package pl.drefosapps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    Interpreter interpreter = new Interpreter();

    @Test
    void checkBasicOperationsTest() {
        String addition = "4.5 + 1.3";
        assertEquals("5.8", interpreter.interprete(addition));

        String subtraction = "54.82 - 27.12";
        assertEquals("27.7", interpreter.interprate(subtraction));

        String multiplication = "31* 6.25";
        assertEquals("193.75", interpreter.interprate(multiplication));

        String division = "27/15";
        assertEquals("1.8", interpreter.interprate(division));
    }

    @Test
    void checkOrderOfOperatorsTest() {
        String l1 = "2+2*2";
        assertEquals("6.0", interpreter.interprate(l1));

        String l2 = "13.7-24.6/3+3.9*7.1";
        assertEquals("33.19", interpreter.interprate(l2));

        String l3 = "5/2*3-6+0.1";
        assertEquals("11.6", interpreter.interprate(l3));
    }

    @Test
    void checkOrderIncludingBracketsTest() {
        String l1 = "(2+2)*(2+2)";
        assertEquals("16.0", interpreter.interprate(l1));

        String l2 = "3.2-15.8/(2.2+ 3.2)";
        assertEquals("0.2(740)", interpreter.interprate(l2));
    }

    @Test
    void checkCalculatingFunctionsTest() {
        String l1 = "3* pow(2,3)";
        assertEquals("24.0", interpreter.interprate(l1));

        String l2 = "2 +sqrt(2.25)";
        assertEquals("3.5", interpreter.interprate(l2));

        String l3 = "17.53 - cbrt(1000)";
        assertEquals("7.53", interpreter.interprate(l3));
    }

    @Test
    void checkAssignmentTest() {
        String l1 = "A=13.5 + 8/2";
        assertEquals("A=17.5", interpreter.interprate(l1));

        String l2 = "B =1 1 *4. 3";
        assertEquals("B=47.3", interpreter.interprate(l2));
    }
}
