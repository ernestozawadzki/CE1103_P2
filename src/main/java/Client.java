package main.java;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que crea un cliente y su interfaz grafica
 * @author Ernesto Zawadzki Hernandez
 */
public class Client {

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
    public static JComboBox<String> listaHistorial;
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
     * String que representa una etiqueta para el cliente
     */
    private static String name;

    /**
     * Constructor de la clase
     */
    public Client() {

        double tag = Math.floor(Math.random() * (100000 - 1 + 1) + 1);
        name = String.valueOf(tag);

        fieldEcuacion = new JTextField("");
        fieldEcuacion.setPreferredSize(new Dimension(150, 25));

        labelRespuesta = new JLabel("R: ");
        labelRespuesta.setPreferredSize(new Dimension(150, 25));

        listaHistorial = new JComboBox<>();
        listaHistorial.setPreferredSize(new Dimension(300, 25));
        listaHistorial.addItem("Respuestas Pasadas");

        botonMath = new JButton("Algebra");
        botonMath.setPreferredSize(new Dimension(150, 25));
        botonMath.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sendData("Algebra", fieldEcuacion.getText(), name);

            }
        });

        botonLogic = new JButton("Logica");
        botonLogic.setPreferredSize(new Dimension(150, 25));
        botonLogic.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sendData("Logica", fieldEcuacion.getText(), name);

            }
        });

        botonImagen = new JButton("Usar Imagen");
        botonImagen.setPreferredSize(new Dimension(150, 25));
        botonImagen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chosenFile = new JFileChooser();
                chosenFile.showOpenDialog(null);
                String filepath = chosenFile.getSelectedFile().getAbsolutePath();

                Tesseract tesseract = new Tesseract();

                try {

                    String projectPath = System.getProperty("user.dir");
                    tesseract.setDatapath(projectPath + "\\Tess4j\\tessdata");
                    String extractedText = tesseract.doOCR(new File(filepath));
                    fieldEcuacion.setText(extractedText);

                }

                catch(TesseractException te) { te.printStackTrace(); }

            }
        });

        botonRegistro = new JButton("Obtener Registro");
        botonRegistro.setPreferredSize(new Dimension(150, 25));
        botonRegistro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> items = readCSV();
                for(String item : items) { listaHistorial.addItem(item); }

            }
        });

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
     * Metodo para enviar y recibir datos del servidor
     * @param getButton nombre del boton seleccionado
     * @param infixExpression expresion dentro del campo asignado en la interfaz
     */
    private void sendData(String getButton, String infixExpression, String name) {

        try(Socket socket = new Socket("localhost", 7000);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            output.println(getButton);
            output.println(infixExpression);
            output.println(name);
            String answer = input.readLine();

            if(answer != null) { labelRespuesta.setText("R: " + answer); }

        }

        catch(IOException e) { e.printStackTrace(); }

    }

    /**
     * Metodo que lee el archivo csv y busca los datos del cliente
     * @return Lista que corresponde a los datos del cliente
     */
    public static List<String> readCSV() {

        listaHistorial.removeAllItems();
        List<String> items = new ArrayList<>();
        String projectPath = System.getProperty("user.dir");

        try(BufferedReader readCSV = new BufferedReader(new FileReader(projectPath + "\\Registro\\Registro.csv"))) {

            String line;

            while((line = readCSV.readLine()) != null) {

                String[] column = line.split(",");

                if(column.length >= 4) {

                    if(column[0].equals(name)) {

                        StringBuilder comboItem = new StringBuilder();

                        for(int i = 1; i < column.length; i++) {

                            comboItem.append(column[i]);

                            if(i < column.length - 1) { comboItem.append(", "); }

                        }

                        items.add(comboItem.toString());

                    }
                }
            }
        }

        catch(IOException ioe) { ioe.printStackTrace(); }

        return items;

    }

    /**
     * Metodo principal de la clase
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() { new Client(); }

        });
    }
}