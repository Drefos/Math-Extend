package pl.drefosapps.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calculator {
    private Map<String, Number> variables;

    public Calculator(Map<String, Number> variables) {
        this.variables = variables;
    }

    public String calculate(String expression) {
        expression = replaceVariables(expression);
        expression = calculateBasicOperations(expression);
        if(Functions.isFunction(expression))
            expression = calculateFunction(expression);
        return String.valueOf(Math.round(Double.valueOf(expression)*Math.pow(10, 10))/Math.pow(10, 10));
    }

    private String replaceVariables(String expression) {
        for (int i = getIndexOfVariable(expression); i != -1; i = getIndexOfVariable(expression)) {
            expression = expression.replaceAll(
                    String.valueOf(expression.charAt(i)),
                    String.valueOf(variables.get(String.valueOf(expression.charAt(i))).getValue()));
        }
        return expression.replaceAll("pi", String.valueOf(Math.PI));
    }

    private int getIndexOfVariable(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) >= 'A' && expression.charAt(i) <= 'Z') return i;
        }
        return -1;
    }

    private String calculateBasicOperations(String expression) {
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
        return expression;
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
        while (i >= 0 && isNumber(expression, i)) i--;
        return Double.valueOf(expression.substring(i+1, operatorIndex));
    }

    private double getNumberAfterIndex(String expression, int operatorIndex) {
        int i = operatorIndex + 1;
        if(expression.charAt(i) == '-') i++;
        while (i < expression.length() && isNumber(expression, i)) i++;
        return Double.valueOf(expression.substring(operatorIndex + 1, i));
    }

    private boolean isNumber(String expression, int i) {
        char c = expression.charAt(i);
        if(c=='-' && i == 0) return true;
        if(c=='-' && isOperator(expression.charAt(i-1))) return true;
        return (!isOperator(c) && c != ',' && c != '(' && c != ')');
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
        return expression.replace("" + firstNumber + operation + secondNumber, String.valueOf(result));
    }

    private int getFirstOperatorIndex(String expression) {
        for (int i = 1; i < expression.length(); i++) {
            if (isOperator(expression.charAt(i)) && isNumber(expression, i-1))
                return i;
        }
        return -1;
    }

    private String calculateFunction(String expression) {
        int begin = 0, end = 0;
        while (end < expression.length() && 'a' <= expression.charAt(end) && expression.charAt(end) <= 'z') end++;
        String function = expression.substring(begin, end).toUpperCase();
        String innerExpression = expression.substring(end+1, expression.length()-1);
        switch (Functions.valueOf(function)) {
            case SIN: {
                expression = String.valueOf(Math.sin(Double.valueOf(innerExpression)));
                break;
            }
            case COS: {
                expression = String.valueOf(Math.cos(Double.valueOf(innerExpression)));
                break;
            }
            case TG: {
                expression = String.valueOf(Math.tan(Double.valueOf(innerExpression)));
                break;
            }
            case POW: {
                expression = String.valueOf(Math.pow(Double.valueOf(innerExpression.split(",")[0]), Double.valueOf(innerExpression.split(",")[1])));
                break;
            }
            case SQRT: {
                expression = String.valueOf(Math.sqrt(Double.valueOf(innerExpression)));
                break;
            }
            case CBRT: {
                expression = String.valueOf(Math.cbrt(Double.valueOf(innerExpression)));
                break;
            }
            case LN: {
                expression = String.valueOf(Math.log(Double.valueOf(innerExpression)));
                break;
            }
            case LOG: {
                expression = String.valueOf(Math.log10(Double.valueOf(innerExpression)));
                break;
            }
            case ROUND: {
                if(expression.contains(",")) {
                    double accuracy = Math.pow(10, Double.valueOf(innerExpression.split(",")[1]));
                    expression = String.valueOf(Math.round(Double.valueOf(innerExpression.split(",")[0])*accuracy)/accuracy);
                }

                expression = String.valueOf(Math.round(Double.valueOf(innerExpression)));
                break;
            }
            case CEIL: {
                if(expression.contains(",")) {
                    double accuracy = Math.pow(10, Double.valueOf(innerExpression.split(",")[1]));
                    expression = String.valueOf(Math.ceil(Double.valueOf(innerExpression.split(",")[0])*accuracy)/accuracy);
                } else
                    expression = String.valueOf(Math.ceil(Double.valueOf(innerExpression)));
                break;
            }
            case FLOOR: {
                if(expression.contains(",")) {
                    double accuracy = Math.pow(10, Double.valueOf(innerExpression.split(",")[1]));
                    expression = String.valueOf(Math.floor(Double.valueOf(innerExpression.split(",")[0])*accuracy)/accuracy);
                } else
                    expression = String.valueOf(Math.floor(Double.valueOf(innerExpression)));
                break;
            }
        }
        return expression;
    }

    private enum Functions {
        SIN, COS, TG, POW, SQRT, CBRT, LN, LOG, ROUND, CEIL, FLOOR;

        static List<String> functions;

        static {
            functions = new ArrayList<>();
            for (Field f : Functions.class.getFields())
                functions.add(f.getName());
        }

        public static boolean isFunction(String expression) {
            int begin = 0, end = 0;
            while (end < expression.length() && 'a' <= expression.charAt(end) && expression.charAt(end) <= 'z') end++;
            String function = expression.substring(begin, end).toUpperCase();
            return functions.contains(function);
        }
    }
}
