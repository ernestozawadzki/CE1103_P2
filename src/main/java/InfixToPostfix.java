package main.java;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Clase que maneja la conversion de expresiones
 * @author Ernesto Zawadzki Hernandez
 */
public class InfixToPostfix {

    /**
     * Metodo para convertir expresiones de infijas a posfijas
     * @param infixExpression expresion obtenida del campo asignado en la interfaz
     * @return expresion en notacion posfija
     */
    public static String toPostfix(String infixExpression) {

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(infixExpression, "()+-*/%^&|~", true);

        while (tokenizer.hasMoreTokens()) {

            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) { continue; }

            else if (isOperand(token)) { postfix.append(token).append(" "); }

            else if (token.equals("(")) { operatorStack.push(token.charAt(0)); }

            else if (token.equals(")")) {

                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {

                    postfix.append(operatorStack.pop()).append(" ");

                }

                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') { operatorStack.pop(); }

            }

            else if(isOperator(token)) {

                char currentOperator = token.charAt(0);

                while(!operatorStack.isEmpty() && getPrecedence(operatorStack.peek()) >= getPrecedence(currentOperator)) {

                    postfix.append(operatorStack.pop()).append(" ");

                }

                operatorStack.push(currentOperator);

            }
        }

        while(!operatorStack.isEmpty()) { postfix.append(operatorStack.pop()).append(" "); }

        return postfix.toString().trim();

    }

    /**
     * Metodo para verificar si se encuentra un operando
     * @param token caracteres de la expresion
     * @return true o false
     */
    private static boolean isOperand(String token) { return token.matches("[0-9]+|true|false"); }

    /**
     * Metodo para verificar si se encuentra un operador
     * @param token caracteres de la expresion
     * @return true o false
     */
    private static boolean isOperator(String token) { return token.matches("[-+*/%^&|~]"); }

    /**
     * Metodo que obtiene el orden de evaluacion para expresiones algebraicas y logicas
     * @param operator operador de la expresion
     * @return orden de prioridad
     */
    private static int getPrecedence(char operator) {

        switch(operator) {

            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '|':
                return 3;
            case '&':
                return 4;
            case '^':
                return 5;
            case '~':
                return 6;
        }

        return -1;

    }
}