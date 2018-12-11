package pl.drefosapps.model;

public class Calculator {

    public String calculate(String expression) {
        return "";
    }

    private enum Function {
        SIN("sin"), COS("cos"), TG("tg"), POW("pow"),
        SQRT("sqrt"), CBRT("cbrt"), LN("ln"), LOG("log"),
        ROUND("round"), CEIL("ceil"), FLOOR("floor");
        Function(String function){}
    }
}
