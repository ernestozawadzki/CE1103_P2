package main.java;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Interfaz implements ActionListener {

    public JTextField fieldEcuacion;
    public JLabel labelRespuesta;
    public JComboBox<String> listaHistorial;
    private JButton botonCalcular;
    private JButton botonImagen;

    public Interfaz() {

        fieldEcuacion = new JTextField("");
        fieldEcuacion.setPreferredSize(new Dimension(150, 25));

        labelRespuesta = new JLabel("");
        labelRespuesta.setPreferredSize(new Dimension(150, 25));

        listaHistorial = new JComboBox<String>();
        listaHistorial.setPreferredSize(new Dimension(300, 25));
        listaHistorial.addItem("Evaluaciones Pasadas");

        botonCalcular = new JButton("Calcular");
        botonCalcular.setPreferredSize(new Dimension(150, 25));
        botonCalcular.addActionListener(this);

        botonImagen = new JButton("Usar Imagen");
        botonImagen.setPreferredSize(new Dimension(150, 25));
        botonImagen.addActionListener(this);

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
        panel.add(botonCalcular, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(botonImagen, gbc);

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

    public static void main(String[] args) { new Interfaz(); }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == botonCalcular){

            //llamar arbol

        }

        if(e.getSource() == botonImagen){

            JFileChooser chosenFile = new JFileChooser();
            chosenFile.showOpenDialog(null);
            String filepath = chosenFile.getSelectedFile().getAbsolutePath();

            System.out.println(filepath);

            Tesseract tesseract = new Tesseract();

            try{

                String projectPath = System.getProperty("user.dir");
                tesseract.setDatapath(projectPath + "\\Tess4j\\tessdata");
                String text = tesseract.doOCR(new File(filepath));
                fieldEcuacion.setText(text);

            }
            catch(TesseractException te){ te.printStackTrace(); }

        }

    }

}