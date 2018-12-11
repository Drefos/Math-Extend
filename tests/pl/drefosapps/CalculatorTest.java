package pl.drefosapps;

import org.junit.jupiter.api.Test;
import pl.drefosapps.model.Calculator;
import pl.drefosapps.model.Number;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calc;
    Map<String, Number> variables;

    public CalculatorTest() {
        calc = new Calculator();
        this.variables = new HashMap<>();
        variables.put("A", new Number("A", 13.4));
        variables.put("B", new Number("B", 5.5));
        variables.put("C", new Number("C", -3.25));
    }

    @Test
    void checkBasicOperationsTest() {
        String addition = "4.5+1.3";
        assertEquals("5.8", calc.calculate(addition));

        String subtraction = "54.82-27.12";
        assertEquals("27.7", calc.calculate(subtraction));

        String multiplication = "31*6.25";
        assertEquals("193.75", calc.calculate(multiplication));

        String division = "27/15";
        assertEquals("1.8", calc.calculate(division));
    }

    @Test
    void checkOrderOfOperatorsTest() {
        String l1 = "2+2*2";
        assertEquals("6.0", calc.calculate(l1));

        String l2 = "13.7-24.6/3+3.9*7.1";
        assertEquals("33.19", calc.calculate(l2));

        String l3 = "5/2*3-6+0.1";
        assertEquals("11.6", calc.calculate(l3));
    }

    @Test
    void checkFunctionsCalculationsTest() {
        String sin = "sin(pi/4)";
        assertEquals("0.5", calc.calculate(sin));

        String cos = "cos(pi)";
        assertEquals("-1.0", calc.calculate(cos));

        String tg = "tg(pi/4)";
        assertEquals("1.0", calc.calculate(tg));

        String pow = "pow(3,4)";
        assertEquals("81.0", calc.calculate(pow));

        String sqrt = "sqrt(6.25)";
        assertEquals("2.5", calc.calculate(sqrt));

        String cbrt = "cbrt(0.343)";
        assertEquals("0.7", calc.calculate(cbrt));

        String ln = "ln(1)";
        assertEquals("0.0", calc.calculate(ln));

        String log = "log(100)";
        assertEquals("2.0", calc.calculate(log));

        String round1 = "round(5.23)";
        assertEquals("5.0", calc.calculate(round1));

        String round2 = "round(5.23,1)";
        assertEquals("5.2", calc.calculate(round2));

        String ceil = "ceil(37.3)";
        assertEquals("38.0", calc.calculate(ceil));

        String floor = "floor(13.7)";
        assertEquals("13.0",calc.calculate(floor));
    }

    @Test
    void checkCalculationWithVariables() {
        String l1 = "sqrt(A-4.4)";
        assertEquals("3.0", calc.calculate(l1));

        String l2 = "B*C+A";
        assertEquals("4.475", calc.calculate(l2));

        String l3 = "floor(C)";
        assertEquals("-4.0", calc.calculate(l3));
    }
}
