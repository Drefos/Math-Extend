package pl.drefosapps.model;

import java.util.Map;

public class Validator {
    private Map<String, Number> variables;
    private boolean valid;
    private String message;

    public Validator(Map<String, Number> variables) {
        this.variables = variables;
    }

    public boolean isValid(String expression) {
        valid = true;
        checkAssignment(expression);
        checkBrackets(expression);
        checkVariables(expression);
        checkOperators(expression);
        checkFunctions(expression);
        return valid;
    }

    public String getMessage() {
        return message;
    }
}
