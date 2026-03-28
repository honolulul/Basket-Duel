package vue;

import javax.swing.*;
import java.awt.*;
import controleur.Controleur;

public class MenuPrincipal extends JFrame {

    private JButton boutonLocal;
    private JButton boutonReseau;
    private JButton boutonIA;
    private JButton boutonQuitter;

    private Controleur controleur;

    public MenuPrincipal(Controleur controleur) {
        this.controleur = controleur;

        setTitle("Basket Duel - Menu Principal");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initialiserComposants();
        ajouterListeners();

        setVisible(true);
    }

    private void initialiserComposants() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 25));

        JLabel titre = new JLabel("Basket Duel");
        titre.setFont(new Font("Arial", Font.BOLD, 42));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        boutonLocal = new JButton("Jouer en Local");
        boutonReseau = new JButton("Jouer en Réseau");
        boutonIA = new JButton("Jouer contre IA");
        boutonQuitter = new JButton("Quitter");

        Dimension tailleBouton = new Dimension(260, 55);
        boutonLocal.setMaximumSize(tailleBouton);
        boutonReseau.setMaximumSize(tailleBouton);
        boutonIA.setMaximumSize(tailleBouton);
        boutonQuitter.setMaximumSize(tailleBouton);

        styliserBouton(boutonLocal);
        styliserBouton(boutonReseau);
        styliserBouton(boutonIA);
        styliserBouton(boutonQuitter);

        panel.add(Box.createVerticalStrut(80));
        panel.add(titre);
        panel.add(Box.createVerticalStrut(70));
        panel.add(boutonLocal);
        panel.add(Box.createVerticalStrut(25));
        panel.add(boutonReseau);
        panel.add(Box.createVerticalStrut(25));
        panel.add(boutonIA);
        panel.add(Box.createVerticalStrut(25));
        panel.add(boutonQuitter);

        add(panel);
    }

    private void styliserBouton(JButton bouton) {
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("Arial", Font.BOLD, 18));
        bouton.setBackground(new Color(70, 130, 180));
        bouton.setForeground(Color.WHITE);
    }

    private void ajouterListeners() {

        boutonLocal.addActionListener(e -> {
            controleur.demarrerPartieLocale();
            this.dispose();
        });

        boutonReseau.addActionListener(e -> {
            controleur.demarrerPartieReseau();
            this.dispose();
        });

        boutonIA.addActionListener(e -> {
            controleur.demarrerPartieIA();
            this.dispose();
        });

        boutonQuitter.addActionListener(e -> {
            System.exit(0);
        });
    }
}
