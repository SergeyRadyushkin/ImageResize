/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageresize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.imgscalr.Scalr;


/**
 *
 * @author Sergey Radyushkin
 */

public class GUI extends JDialog {

    public GUI() throws ParseException {
        setTitle("resizeImage");
        setSize(350, 80);
        setLocation(500, 500);
        this.setResizable(false);
        JPanel panel = new JPanel();
        JLabel labelSource = new JLabel("source:");
        JLabel labelHeight = new JLabel("height:");
        JLabel labelWidth = new JLabel("width:");
        JButton openChooserButton = new JButton("...");
        JButton resizeImageButton = new JButton("Resize");
        add(panel, BorderLayout.CENTER);
        panel.add(labelSource);
        panel.add(openChooserButton);
        panel.add(labelWidth);
        panel.add(heightField);
        panel.add(labelHeight);
        panel.add(widthField);
        panel.add(resizeImageButton);
        openChooserButton.addActionListener(buttonOpenChooserListener);
        resizeImageButton.addActionListener(buttonResizeListener);
    }

    
    
    private String getCorrectFilePath(String path) {
        String separator = "\\";
        int sep = path.lastIndexOf(separator);
        path = path.substring(0, sep + 1);
        return path;
    }

    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (flagNotFileChoosen == true) {
            g.setColor(Color.red);
            g.drawString("please, choose file!", 105, 70);
        }
        if (flagNotSizeSet == true) {
            g.setColor(Color.red);
            g.drawString("please, set size!", 100, 70);
        }
        if (flagIncorrectFormat == true) {
            g.setColor(Color.red);
            g.drawString("Only integers!", 100, 70);
        }
    }
    ;
  
       
    
    ActionListener buttonOpenChooserListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent event) {
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*jpg only", "jpg");
            fileChooser.setFileFilter(fileFilter);
            int ret = fileChooser.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                fileChoosed = file;
                pathToFile = fileChooser.getSelectedFile().getAbsolutePath();
            }
        }
    };
    
    
    ActionListener buttonResizeListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (fileChoosed == null) {
                flagNotFileChoosen = true;
                repaint();
            } else if (heightField.getText().equals("") || widthField.getText().equals("")) {
                flagNotFileChoosen = false;
                flagNotSizeSet = true;
                repaint();
            } else {
                flagNotFileChoosen = false;
                flagNotSizeSet = false;
                repaint();
                Integer HEIGHT;
                Integer WIDTH;
                try {
                    HEIGHT = new Integer(heightField.getText());
                    WIDTH = new Integer(widthField.getText());
                    pathToFile = getCorrectFilePath(pathToFile);
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(fileChoosed);
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    BufferedImage scaledImg = Scalr.resize(img, Scalr.Mode.AUTOMATIC, HEIGHT, WIDTH);
                    File destFile = new File(pathToFile + "resized.jpg");
                    try {
                        ImageIO.write(scaledImg, "jpg", destFile);
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    flagIncorrectFormat = false;
                } catch (NumberFormatException ex) {
                    flagIncorrectFormat = true;
                }
            }
        }
    };
    
    
    private JTextField heightField = new JTextField(3);
    private JTextField widthField = new JTextField(3);
    private boolean flagNotFileChoosen = false;
    private boolean flagNotSizeSet = false;
    private boolean flagIncorrectFormat = false;
    private String pathToFile = null;
    private File fileChoosed = null;
    private JFileChooser fileChooser = new JFileChooser();
}
