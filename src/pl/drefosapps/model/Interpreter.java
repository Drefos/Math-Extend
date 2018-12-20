package pl.drefosapps.model;

import java.util.Map;
import java.util.Stack;

public class Interpreter {
    private Calculator calculator;
    private boolean assignment;
    private Map<String, Number> variables;
    private String label;

    public Interpreter(Map<String, Number> variables) {
        this.variables = variables;
        this.calculator = new Calculator(variables);
    }

    public String interpret(String expression) {
        expression = expression.replaceAll(" ", "");
        expression = checkAssignment(expression);
        String result = execute(expression);
        if(assignment)
            result = saveAndReturnNumber(result);
        return result;
    }

    private String checkAssignment(String expression) {
        assignment = expression.contains("=");
        label = String.valueOf(expression.charAt(0));
        return assignment ? expression.substring(2) : expression;
    }

    private String execute(String expression) {
        Stack<Integer> brackets = new Stack<>();
        int start, end;
        boolean isFunction = false;
        String subExpression;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                brackets.push(i);
            } else if (expression.charAt(i) == ')') {
                start = brackets.pop();
                end = i;
                while (start - 1 >= 0 && 'a' <= expression.charAt(start - 1) && expression.charAt(start - 1) <= 'z')
                    start--;
                if (expression.charAt(start) != '(') isFunction = true;
                if (isFunction) {
                    subExpression = expression.substring(start, end + 1);
                } else {
                    subExpression = expression.substring(start + 1, end);
                }
                subExpression = calculator.calculate(subExpression);
                StringBuilder builder = new StringBuilder();
                builder.append(expression.substring(0, start)).append(subExpression).append(expression.substring(end + 1));
                expression = builder.toString();
                i = start + subExpression.length();
            }
        }
        return calculator.calculate(expression);
    }

    private String saveAndReturnNumber(String expression) {
        if(variables.keySet().contains(label))
            variables.remove(label);
        Number number = new Number(label, Double.valueOf(expression));
        variables.put(label, number);
        return number.toString();
    }
}
