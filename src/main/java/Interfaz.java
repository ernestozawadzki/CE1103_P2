package main.java;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 *
 */
public class Interfaz implements ActionListener {

    /**
     * Campo de texto para escribir expresiones
     */
    public JTextField fieldEcuacion;
    /**
     * Campo de texto donde se reciben respuestas
     */
    public JLabel labelRespuesta;
    /**
     * Tabla donde se recibe el registro
     */
    public JComboBox<String> listaHistorial;
    /**
     * Boton para evaluaciones algebraicas
     */
    private final JButton botonMath;
    /**
     * Boton para evaluaciones logicas
     */
    private final JButton botonLogic;
    /**
     * Boton para usar una imagen
     */
    private final JButton botonImagen;
    /**
     * Boton para pedir el registro
     */
    private final JButton botonRegistro;

    /**
     * Constructor de la clase
     */
    public Interfaz() {

        fieldEcuacion = new JTextField("");
        fieldEcuacion.setPreferredSize(new Dimension(150, 25));

        labelRespuesta = new JLabel("R: ");
        labelRespuesta.setPreferredSize(new Dimension(150, 25));

        listaHistorial = new JComboBox<String>();
        listaHistorial.setPreferredSize(new Dimension(300, 25));
        listaHistorial.addItem("Evaluaciones Pasadas");

        botonMath = new JButton("Algebra");
        botonMath.setPreferredSize(new Dimension(150, 25));
        botonMath.addActionListener(this);

        botonLogic = new JButton("Logica");
        botonLogic.setPreferredSize(new Dimension(150, 25));
        botonLogic.addActionListener(this);

        botonImagen = new JButton("Usar Imagen");
        botonImagen.setPreferredSize(new Dimension(150, 25));
        botonImagen.addActionListener(this);

        botonRegistro = new JButton("Obtener Registro");
        botonRegistro.setPreferredSize(new Dimension(150, 25));
        botonRegistro.addActionListener(this);

        JLabel blank = new JLabel("");
        blank.setPreferredSize(new Dimension(150, 25));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(fieldEcuacion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelRespuesta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(botonMath, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(botonLogic, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(botonImagen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(botonRegistro, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(blank, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(listaHistorial, gbc);

        JFrame frame = new JFrame();
        frame.setTitle("Calculadora");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Metodo principal de la clase
     */
    public static void main(String[] args) { new Interfaz(); }

    /**
     * Metodo que activa la accion de los botones
     * @Override
     * @param e evento a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == botonMath){

            String infixExpression = fieldEcuacion.getText();
            String postfixExpression = InfixToPostfix.toPostfix(infixExpression);

            String[] tokens = postfixExpression.split(" ");
            ExpressionTree tree = new ExpressionTree();
            TreeNode root = tree.constructExpressionTree(tokens);

            labelRespuesta.setText("R: " + tree.evaluateMath(root));

        }

        if(e.getSource() == botonLogic){

            String infixExpression = fieldEcuacion.getText();
            String postfixExpression = InfixToPostfix.toPostfix(infixExpression);

            String[] tokens = postfixExpression.split(" ");
            ExpressionTree tree = new ExpressionTree();
            TreeNode root = tree.constructExpressionTree(tokens);

            labelRespuesta.setText("R: " + tree.evaluateLogic(root));

        }

        if(e.getSource() == botonImagen){

            JFileChooser chosenFile = new JFileChooser();
            chosenFile.showOpenDialog(null);
            String filepath = chosenFile.getSelectedFile().getAbsolutePath();

            Tesseract tesseract = new Tesseract();

            try{

                String projectPath = System.getProperty("user.dir");
                tesseract.setDatapath(projectPath + "\\Tess4j\\tessdata");
                String extractedText = tesseract.doOCR(new File(filepath));
                fieldEcuacion.setText(extractedText);

            }

            catch(TesseractException te){ te.printStackTrace(); }

        }

        if(e.getSource() == botonRegistro){

            System.out.println("agregar esto");

        }
    }
}



/*

        IMPLEMENTAR CONECCION CLIENTE-SERVIDOR

        SIMPLIFICAR CLASE InfixToPostfix

        IMPLEMENTAR ESTADO DE ERROR ENTRE CLASES (?)

        IMPLEMENTAR REGISTRO CON ARCHVIOS .csv

        HACER JAVADOC Y UML

 */