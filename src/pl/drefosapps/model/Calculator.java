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
        calculateBasicOperations(expression);
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

    private void calculateBasicOperations(String expression) {
        int multiplicationIndex = findMultiplication(expression);
        int divisionIndex = findDivision(expression);
        int operatorIndex;
        double firstNumber, secondNumber;

        while (multiplicationIndex != divisionIndex) {
            operatorIndex = getEarlierIndex(multiplicationIndex, divisionIndex);

            firstNumber = getNumberBeforeIndex(expression, operatorIndex);
            secondNumber = getNumberAfterIndex(expression, operatorIndex);

            expression = replaceOperationWithResult(expression, firstNumber, secondNumber, expression.charAt(operatorIndex));

            multiplicationIndex = findMultiplication(expression);
            divisionIndex = findDivision(expression);
        }
        while ((operatorIndex = getFirstOperatorIndex(expression)) != -1) {
            firstNumber = getNumberBeforeIndex(expression, operatorIndex);
            secondNumber = getNumberAfterIndex(expression, operatorIndex);

            expression = replaceOperationWithResult(expression, firstNumber, secondNumber, expression.charAt(operatorIndex));

        }
    }

    private int getEarlierIndex(int multiplicationIndex, int divisionIndex) {
        if(multiplicationIndex == -1) return divisionIndex;
        if(divisionIndex == -1) return multiplicationIndex;
        return multiplicationIndex < divisionIndex ? multiplicationIndex : divisionIndex;
    }

    private int findMultiplication(String expression) {
        return findChar(expression, '*');
    }

    private int findDivision(String expression) {
        return findChar(expression, '/');
    }

    private int findChar(String expression, char c) {
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == c)
                return i;
        }
        return -1;
    }

    private double getNumberBeforeIndex(String expression, int operatorIndex) {
        int i = operatorIndex - 1;
        while (i >= 0 && !isOperator(expression.charAt(i)) && expression.charAt(i) != ',') i--;
        return Double.valueOf(expression.substring(i+1, operatorIndex));
    }

    private double getNumberAfterIndex(String expression, int operatorIndex) {
        int i = operatorIndex + 1;
        while (i < expression.length() && !isOperator(expression.charAt(i)) && expression.charAt(i) != ',') i++;
        return Double.valueOf(expression.substring(operatorIndex + 1, i));
    }

    private boolean isOperator(char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;

        }
    }

    private String replaceOperationWithResult(String expression, double firstNumber, double secondNumber, char operation) {
        double result = 0;
        switch (operation) {
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                result = firstNumber / secondNumber;
                break;
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
        }
        return expression.replaceAll("" + firstNumber + operation + secondNumber, String.valueOf(result));
    }

    private int getFirstOperatorIndex(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            if (isOperator(expression.charAt(i)))
                return i;
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
