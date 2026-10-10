import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class editimage {
    public static BufferedImage image;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mon Éditeur d'Image");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JButton gray = new JButton("Gris");
        JButton invert = new JButton("Inverser");
        JButton luminositeplus = new JButton("Luminosité +");
        JButton luminositeminus = new JButton("Luminosité -");

        frame.add(new JScrollPane(imgLabel), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(gray);
        panel.add(invert);
        panel.add(luminositeplus);
        panel.add(luminositeminus);
        frame.add(panel, BorderLayout.SOUTH);

        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu("Fichier");
        JMenuItem open = new JMenuItem("Ouvrir");
        JMenuItem save = new JMenuItem("Sauvegarder");
        menu1.add(open);
        menu1.add(save);
        menubar.add(menu1);
        frame.setJMenuBar(menubar);

        // Action : Ouvrir
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    image = ImageIO.read(fileChooser.getSelectedFile());
                    imgLabel.setIcon(new ImageIcon(image));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Action : Gris
        gray.addActionListener(e -> {
            if (image == null) {
                JOptionPane.showMessageDialog(frame, "Pas d'image chargée");
                return;
            }
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color c = new Color(image.getRGB(x, y));
                    int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                    image.setRGB(x, y, new Color(grey, grey, grey).getRGB());
                }
            }
            imgLabel.repaint();
        });

        // Action : Inverser
        invert.addActionListener(e -> {
            if (image == null) {
                JOptionPane.showMessageDialog(frame, "Pas d'image chargée");
                return;
            }
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color c = new Color(image.getRGB(x, y));
                    int r = 255 - c.getRed();
                    int g = 255 - c.getGreen();
                    int b = 255 - c.getBlue();
                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
            imgLabel.repaint();
        });

        // Action : Luminosité +
        luminositeplus.addActionListener(e -> {
            if (image == null) {
                JOptionPane.showMessageDialog(frame, "Pas de photo choisie");
                return;
            }
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color c = new Color(image.getRGB(x, y));
                    int r = Math.min(255, c.getRed() + 30);
                    int g = Math.min(255, c.getGreen() + 30);
                    int b = Math.min(255, c.getBlue() + 30);
                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
            imgLabel.repaint();
        });

        // Action : Luminosité -
        luminositeminus.addActionListener(e -> {
            if (image == null) {
                JOptionPane.showMessageDialog(frame, "Pas de photo choisie");
                return;
            }
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color c = new Color(image.getRGB(x, y));
                    int r = Math.max(0, c.getRed() - 30);
                    int g = Math.max(0, c.getGreen() - 30);
                    int b = Math.max(0, c.getBlue() - 30);
                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
            imgLabel.repaint();
        });

        // Action : Sauvegarder
        save.addActionListener(e -> {
            if (image == null) {
                JOptionPane.showMessageDialog(frame, "Pas d'image à sauvegarder");
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    File outputFile = fileChooser.getSelectedFile();
                    // On force l'extension si absente
                    if (!outputFile.getName().toLowerCase().endsWith(".jpg")) {
                        outputFile = new File(outputFile.getAbsolutePath() + ".jpg");
                    }
                    ImageIO.write(image, "jpg", outputFile);
                    JOptionPane.showMessageDialog(frame, "Enregistré avec succès");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}