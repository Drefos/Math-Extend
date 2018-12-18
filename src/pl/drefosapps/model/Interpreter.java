package pl.drefosapps.model;

import java.util.Map;

public class Interpreter {
    private Calculator calculator;

    public Interpreter(Map<String, Number> variables) {
        this.calculator = new Calculator(variables);
    }

    public String interpret(String expression) {
        return "";
    }
}
