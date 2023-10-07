import java.util.Stack;

public class ExpressionTree {

    private static boolean isOperator(char element){

        if(element == '+' || element == '-' || element == '*' || element == '/' || element == '?'){
            return true;
        }
        else{ return false; }

    }

    public static Node mathTree(String postfix){

        Stack<Node> stack = new Stack<Node>();
        Node op1;
        Node op2;
        Node operator;

        for(int i = 0; i < postfix.length(); i++){

            if(!isOperator(postfix.charAt(i))){

                operator = new Node(postfix.charAt(i));
                stack.push(operator);

            }
            else{

                operator = new Node(postfix.charAt(i));
                op1 = stack.pop();
                op2 = stack.pop();
                operator.left = op2;
                operator.right = op1;
                stack.push(operator);

            }
        }

        operator = stack.pop();
        return operator;

    }

    public static void inorder(Node root){

        if(root == null) return;

        inorder(root.left);
        System.out.print(root.element);
        inorder(root.right);

    }

    public static void main(String[] args){

        String postfix = "135+*7/";

        Node r = mathTree(postfix);
        inorder(r);

    }

}