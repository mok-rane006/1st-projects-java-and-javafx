import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class calculatrice1 {
    // Variables de mémoire (en static pour être accessibles partout)
    static double num1 = 0, num2 = 0, resultat = 0;
    static char operateur = ' ';

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculatrice N MOKRANE YAAAAA");
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout(10, 10));

        // --- L'ÉCRAN ---
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 70));
        textField.setFont(new Font("Arial", Font.BOLD, 30));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false); // On ne tape pas au clavier
        frame.add(textField, BorderLayout.NORTH);

        // --- LE PAVÉ NUMÉRIQUE (CENTRE) ---
        JPanel panelChiffres = new JPanel(new GridLayout(4, 3, 5, 5));
        // Boucle pour créer les boutons 1 à 9
        for (int i = 1; i <= 9; i++) {
            String val = String.valueOf(i);
            JButton btn = new JButton(val);
            btn.setFont(new Font("Arial", Font.PLAIN, 20));
            btn.addActionListener(e -> textField.setText(textField.getText() + val));
            panelChiffres.add(btn);
        }
        
        // Bouton 0 et Clear (C)
        JButton btn0 = new JButton("0");
        btn0.setFont(new Font("Arial", Font.PLAIN, 20));
        btn0.addActionListener(e -> textField.setText(textField.getText() + "0"));
        
        JButton btnClear = new JButton("C");
        btnClear.setBackground(Color.RED);
        btnClear.addActionListener(e -> textField.setText(""));

        panelChiffres.add(btnClear);
        panelChiffres.add(btn0);
        
        frame.add(panelChiffres, BorderLayout.CENTER);

        // --- LES OPÉRATIONS (EST) ---
        JPanel panelOps = new JPanel(new GridLayout(5, 1, 5, 5));
        String[] ops = {"+", "-", "*", "/", "="};

        for (String s : ops) {
            JButton btn = new JButton(s);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setBackground(Color.LIGHT_GRAY);
            
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (s.equals("=")) {
                        // Action du bouton EGAL
                        if (!textField.getText().isEmpty()) {
                            num2 = Double.parseDouble(textField.getText());
                            switch (operateur) {
                                case '+': resultat = num1 + num2; break;
                                case '-': resultat = num1 - num2; break;
                                case '*': resultat = num1 * num2; break;
                                case '/': 
                                    if (num2 != 0) resultat = num1 / num2; 
                                    else { textField.setText("Error"); return; }
                                    break;
                            }
                            textField.setText(String.valueOf(resultat));
                        }
                    } else {
                        // Action des boutons +, -, *, /
                        if (!textField.getText().isEmpty()) {
                            num1 = Double.parseDouble(textField.getText());
                            operateur = s.charAt(0);
                            textField.setText("");
                        }
                    }
                }
            });
            panelOps.add(btn);
        }
        
        frame.add(panelOps, BorderLayout.EAST);

        // Lancement
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        frame.setVisible(true);
    }
}