package main.java;

public class ExpressionTree {

    private static StackNode top;

    public ExpressionTree(){ top = null; }

    public void clear(){ top = null; }      //borrar arbol

    public void push(TreeNode pointer){

        if(top == null){ top = new StackNode(pointer); }

        else{

            StackNode nextPointer = new StackNode(pointer);
            nextPointer.next = top;
            top = nextPointer;

        }
    }

    public TreeNode pop(){

        if(top == null){ throw new RuntimeException(); }

        else{

            TreeNode pointer = top.treeNode;
            top = top.next;
            return pointer;

        }
    }

    public TreeNode peek(){ return top.treeNode; }

    public void insert(char val){

        try{

            if(isDigit(val)){

                TreeNode nextpointer = new TreeNode(val);
                push(nextpointer);

            }
            else if(isOperator(val)){

                TreeNode nextpointer = new TreeNode(val);
                nextpointer.left = pop();
                nextpointer.right = pop();
                push(nextpointer);

            }

        } catch(Exception e){ System.out.println("Invalid Expression"); }

    }

    public boolean isDigit(char ch){ return ch >= '0' && ch <= '9'; }

    public boolean isOperator(char ch){

        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';

    }

    public int toDigit(char ch) { return ch - '0'; }

    public void buildTree(String expression){

        for(int i = expression.length() - 1; i >= 0; i--){ insert(expression.charAt(i)); }

    }

    public double evaluate(){ return evaluate(peek()); }

    public double evaluate(TreeNode pointer){

        if(pointer.left == null && pointer.right == null){ return toDigit(pointer.data); }

        else{

            double result = 0.0;
            double left = evaluate(pointer.left);
            double right = evaluate(pointer.right);
            char operator = pointer.data;

            switch(operator)
            {
            case '+': result = left + right; break;
            case '-': result = left - right; break;
            case '*': result = left * right; break;
            case '/': result = left / right; break;
            case '%': result = left % right; break;
            case '^': result = Math.pow(left, right); break;
            default: result = left + right; break;

            }

            return result;

        }
    }

    public void postfix(){ postOrder(peek()); };

    public void postOrder(TreeNode pointer) {

        if (pointer != null) {

            postOrder(pointer.left);
            postOrder(pointer.right);
            System.out.print(pointer.data);

        }
    }

    public void infix(){ inOrder(peek()); }

    public void inOrder(TreeNode pointer){

        if(pointer != null){

            inOrder(pointer.left);
            System.out.print(pointer.data);
            inOrder(pointer.right);

        }
    }

    public void prefix(){ preOrder(peek()); }

    public void preOrder(TreeNode pointer){

        if(pointer != null) {

            System.out.print(pointer.data);
            preOrder(pointer.left);
            preOrder(pointer.right);

        }
    }



    public static void main(String[] args){

        ExpressionTree tree = new ExpressionTree();
        tree.buildTree("+/*1357");

        tree.infix();

        System.out.println("\n" + tree.evaluate());

    }



}



//      +/*1357    =   1*3/5+7    =   13*5/7+

/*

        Proceso: escribe infix -> pasa a postfix -> evaluar arbol

        IMPLEMENTAR USO DE NUMEROS NO DIGITOS

 */