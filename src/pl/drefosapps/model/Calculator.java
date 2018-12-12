package pl.drefosapps.model;

import java.util.Arrays;
import java.util.Map;

public class Calculator {
    private Map<String, Number> variables;

    public Calculator(Map<String, Number> variables) {
        this.variables = variables;
    }

    public String calculate(String expression) {
        replaceVariables(expression);

        return "";
    }

    private void replaceVariables(String expression) {
        for (int i = getIndexOfVariable(expression); i != -1; i = getIndexOfVariable(expression)) {
            expression = expression.replaceAll(
                    String.valueOf(expression.charAt(i)),
                    String.valueOf(variables.get(String.valueOf(expression.charAt(i))).getValue()));
        }
    }

    private int getIndexOfVariable(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) >= 'A' && expression.charAt(i) <= 'Z') return i;
        }
        return -1;
    }

    private enum Function {
        SIN("sin"), COS("cos"), TG("tg"), POW("pow"),
        SQRT("sqrt"), CBRT("cbrt"), LN("ln"), LOG("log"),
        ROUND("round"), CEIL("ceil"), FLOOR("floor");
        Function(String function){}

        public boolean isFunction(String expression) {
            int begin = 0, end = 0;
            while('a' <= expression.charAt(end) && expression.charAt(end) <= 'z') end++;
            String fun = expression.substring(begin, end).toUpperCase();
            return Arrays.asList(Function.values()).contains(Function.valueOf(fun));
        }
    }
}
