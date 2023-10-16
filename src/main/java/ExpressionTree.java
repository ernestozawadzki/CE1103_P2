package main.java;

import java.util.Stack;


/**
 * Clase que crea y evalua el arbol de expresion
 * @author Ernesto Zawadzki Hernandez
 */
public class ExpressionTree {

    /**
     * Metodo que construye el arbol de expresion
     * @param postfix expresion dada en la notacion posfija
     * @return arbol construido
     */
    public TreeNode constructExpressionTree(String[] postfix) {

        Stack<TreeNode> stack = new Stack<>();

        for (String token : postfix) {

            if (isOperator(token)) {

                TreeNode rightOperand = stack.pop();
                TreeNode leftOperand = stack.pop();
                TreeNode operatorNode = new TreeNode(token);
                operatorNode.left = leftOperand;
                operatorNode.right = rightOperand;
                stack.push(operatorNode);

            } else { stack.push(new TreeNode(token)); }

        }

        return stack.pop();

    }

    /**
     * Metodo que evalua expresiones algebraicas
     * @param root raiz del arbol
     * @return operacion algebracia
     */
    public double evaluateMath(TreeNode root) {

        if (root == null) { return 0; }
        if (!isOperator(root.value)) { return Double.valueOf(root.value); }

        double left = evaluateMath(root.left);
        double right = evaluateMath(root.right);

        switch (root.value) {

            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/":

                if(right == 0){ throw new ArithmeticException("Arithmetic Error"); }
                return left / right;

            case "%":

                if(right == 0){ throw new ArithmeticException("Arithmetic Error"); }
                return left % right;

            case "^": return Math.pow(left, right);

            default: throw new IllegalArgumentException("Invalid operator: " + root.value);

        }
    }

    /**
     * Metodo que evalua expresiones logicas
     * @param root raiz del arbol
     * @return operacion logica
     */
    public boolean evaluateLogic(TreeNode root) {

        if (root == null) { return false; }
        if (!isOperator(root.value)) { return Boolean.parseBoolean(root.value); }

        boolean left = evaluateLogic(root.left);
        boolean right = evaluateLogic(root.right);

        switch (root.value) {

            case "&": return left && right;
            case "|": return left || right;
            case "^": return left ^ right;
            case "~": return !(left && right);

            default: throw new IllegalArgumentException("Invalid operator: " + root.value);

        }
    }

    /**
     * Metodo que indica si un nodo es o no es operador
     * @param token texto del nodo
     * @return true o false
     */
    public boolean isOperator(String token) {

        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")
                || token.equals("%") || token.equals("&") || token.equals("|") || token.equals("~")
                || token.equals("^");

    }
}