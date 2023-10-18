package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que maneja el servidor y las expresiones de los clientes
 * @author Ernesto Zawadzki Hernandez
 */
public class Server {

    /**
     * Metodo que agrega datos al archivo csv
     * @param line linea de datos para agregar
     */
    public static void writeCSV(String[] line) {

        String projectPath = System.getProperty("user.dir");

        try(BufferedWriter writeCSV = new BufferedWriter(new FileWriter(projectPath + "\\Registro\\Registro.csv", true))) {

            for(int i = 0; i < line.length; i++) {

                writeCSV.write(line[i]);

                if(i < line.length - 1) { writeCSV.write(","); }

            }

            writeCSV.write(System.lineSeparator());

        }

        catch(IOException ioe) { ioe.printStackTrace(); }

    }

    /**
     * Metodo que enciende el servidor y los pedidos de los clientes
     */
    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(7000)) {

            System.out.println("Server Online");

            while(true){

                try(Socket clientSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String getButton = input.readLine();
                    String infixExpression = input.readLine();
                    String name = input.readLine();

                    System.out.println(getButton);
                    System.out.println(infixExpression);
                    System.out.println("Name: " + name);
                    //^^borrar esto despues

                    if(getButton.equals("Algebra")) {

                        String postfixExpression = InfixToPostfix.toPostfix(infixExpression);
                        String[] tokens = postfixExpression.split(" ");
                        ExpressionTree tree = new ExpressionTree();
                        TreeNode root = tree.constructExpressionTree(tokens);

                        String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
                        String answer = String.valueOf(tree.evaluateMath(root));

                        String[] line = {name, infixExpression, answer, time};
                        writeCSV(line);

                        output.println(answer);

                    }

                    if(getButton.equals("Logica")) {

                        String postfixExpression = InfixToPostfix.toPostfix(infixExpression);
                        System.out.println(postfixExpression);
                        String[] tokens = postfixExpression.split(" ");
                        ExpressionTree tree = new ExpressionTree();
                        TreeNode root = tree.constructExpressionTree(tokens);

                        String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
                        String answer = String.valueOf(tree.evaluateLogic(root));

                        String[] line = {name, infixExpression, answer, time};
                        writeCSV(line);

                        output.println(answer);

                    }
                }
            }
        }

        catch(IOException ioe) { ioe.printStackTrace(); }

    }
}