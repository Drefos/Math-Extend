package pl.drefosapps.model;

import java.util.Arrays;
import java.util.Map;

public class Calculator {
    private Map<String, Number> variables;

    public Calculator(Map<String, Number> variables) {
        this.variables = variables;
    }

    public String calculate(String expression) {

        return "";
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
