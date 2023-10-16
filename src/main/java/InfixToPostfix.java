package main.java;

import java.util.Stack;
import java.util.StringTokenizer;


public class InfixToPostfix {

    public static String toPostfix(String infixExpression) {
        Stack<Character> operatorStack = new Stack<>();
        StringBuilder postfixExpression = new StringBuilder();

        StringTokenizer tokenizer = new StringTokenizer(infixExpression, "()+-*/%&|~^", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) {
                continue; // Skip whitespace
            } else if (Character.isDigit(token.charAt(0))) {
                postfixExpression.append(token).append(" ");
            } else if (token.equals("(")) {
                operatorStack.push('(');
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfixExpression.append(operatorStack.pop()).append(" ");
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop(); // Pop '('
                }
            } else {
                char operator = token.charAt(0);
                while (!operatorStack.isEmpty() && Precedence(operator) <= Precedence(operatorStack.peek())) {
                    postfixExpression.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(operator);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfixExpression.append(operatorStack.pop()).append(" ");
        }

        return postfixExpression.toString().trim();
    }

    public static int Precedence(char operator){

        switch(operator){

            case '+':
            case '-':
            case '|':
                return 1;

            case '*':
            case '/':
            case '%':
            case '&':
                return 2;

            case '^': return 3;
            case '~': return 4;
            default: return 0;

        }
    }
}