import java.util.*;
// Stack implementation to perform calculations
public class Calculator {
    // Server passes expr to calculator
    public static double calculate(String expression) {
        // To hold the numbers
        Stack<Double> numbers = new Stack<>();
        // To hold the operators
        Stack<Character> operators = new Stack<>();
        // To split the expr into numbers and operators stacks.
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length()
                        && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                double num = Double.parseDouble(sb.toString());
                numbers.push(num);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();
                    char op = operators.pop();
                    double result = performOperation(num1, num2, op);
                    numbers.push(result);
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();
                    char op = operators.pop();
                    double result = performOperation(num1, num2, op);
                    numbers.push(result);
                }
                operators.push(c);
            }
        }
        while (!operators.isEmpty()) {
            double num2 = numbers.pop();
            double num1 = numbers.pop();
            char op = operators.pop();
            double result = performOperation(num1, num2, op);
            numbers.push(result);
        }
        return numbers.pop();
    }
    // Returns the operator
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    // Returns the operator precedence
    public static int precedence(char c) {
        if (c == '*' || c == '/') {
            return 2;
        } else if (c == '+' || c == '-') {
            return 1;
        } else {
            return 0;
        }
    }
    // To perform the operation between to numbers
    public static double performOperation(double num1, double num2, char op) {
        if (op == '+') {
            return num1 + num2;
        } else if (op == '-') {
            return num1 - num2;
        } else if (op == '*') {
            return num1 * num2;
        } else if (op == '/') {
            return num1 / num2;
        } else {
            throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}