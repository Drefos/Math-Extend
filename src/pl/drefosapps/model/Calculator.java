package pl.drefosapps.model;

import java.util.Arrays;

public class Calculator {

    public String calculate(String expression) {

        return "";
    }

    private boolean isFunction(String expression) {
        int begin = 0, end = 0;
        while('a' <= expression.charAt(end) && expression.charAt(end) <= 'z') end++;
        String fun = expression.substring(begin, end).toUpperCase();
        return Arrays.asList(Function.values()).contains(Function.valueOf(fun));
    }

    private enum Function {
        SIN("sin"), COS("cos"), TG("tg"), POW("pow"),
        SQRT("sqrt"), CBRT("cbrt"), LN("ln"), LOG("log"),
        ROUND("round"), CEIL("ceil"), FLOOR("floor");
        Function(String function){}
    }
}
