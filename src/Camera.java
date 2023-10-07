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

public class Camera {

    private JLabel cameraOutput;
    private JButton takeImage;
    private VideoCapture videoCapture;
    private Mat image;

    public Camera(){

        cameraOutput = new JLabel();
        takeImage = new JButton("Tomar Foto");
        takeImage.setPreferredSize(new Dimension(120, 30));

        takeImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fileName = new String();
                fileName = new SimpleDateFormat("hh-mm-ss").format(new Date());

                Imgcodecs.imwrite("images/" + fileName + ".jpg", image);

            }
        });

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
        cameraFrame.setSize(new Dimension(800, 800));
        cameraFrame.pack();
        cameraFrame.setVisible(true);

    }

    public void camON(){

        videoCapture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;

        while(true){

            videoCapture.read(image);
            MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraOutput.setIcon(icon);

        }
    }

    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                Camera camera = new Camera();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        camera.camON();
                    }
                }).start();
            }
        });
    }
}
