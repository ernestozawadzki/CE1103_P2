package main.java;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Clase que crea y maneja la funcionalidad de la camara
 * @author Ernesto Zawadzki Hernandez
 */
public class Camera implements ActionListener {

    /**
     * Propiedad que representa la senal de video de la camara
     */
    private final JLabel cameraOutput;
    /**
     * Propiedad que representa la imagen creada
     */
    private Mat image;

    /**
     * Constructor de la camara
     */
    public Camera() {

        cameraOutput = new JLabel();

        JButton takeImage = new JButton("Tomar Foto");
        takeImage.setPreferredSize(new Dimension(120, 30));
        takeImage.addActionListener(this);

        JPanel cameraPanel = new JPanel();
        cameraPanel.setLayout(new GridBagLayout());

        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.fill = GridBagConstraints.VERTICAL;
        cgbc.insets = new Insets(3, 3, 3, 3);

        cgbc.gridx = 0;
        cgbc.gridy = 0;
        cameraPanel.add(cameraOutput, cgbc);

        cgbc.gridx = 0;
        cgbc.gridy = 1;
        cameraPanel.add(takeImage, cgbc);

        JFrame cameraFrame = new JFrame();
        cameraFrame.setTitle("Camara");
        cameraFrame.add(cameraPanel);
        cameraFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cameraFrame.setPreferredSize(new Dimension(800, 800));
        cameraFrame.setResizable(false);
        cameraFrame.pack();
        cameraFrame.setVisible(true);

    }

    /**
     * Metodo que activa la camara
     */
    public void camON() {

        VideoCapture videoCapture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;
        ImageIcon icon;

        while (true) {

            videoCapture.read(image);
            MatOfByte buffer = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buffer);
            imageData = buffer.toArray();
            icon = new ImageIcon(imageData);
            cameraOutput.setIcon(icon);

        }
    }

    /**
     * Metodo que activa la libreria OpenCV
     */
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {

            /**
             * Metodo que construye la camara
             * @Override
             */
            @Override
            public void run() {

                Camera camera = new Camera();

                new Thread(new Runnable() {

                    /**
                     * Metodo que activa la camara
                     * @Override
                     */
                    @Override
                    public void run() {
                        camera.camON();
                    }

                }).start();

            }
        });
    }

    /**
     * Metodo que toma una foto y la agrega al folder de imagenes del proyecto
     * @param e evento a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String fileName = new SimpleDateFormat("dd-hh-mm-ss").format(new Date());
        Imgcodecs.imwrite("images/" + fileName + ".jpg", image);

    }
}