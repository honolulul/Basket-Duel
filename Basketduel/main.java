package Main;
import javax.swing.SwingUtilities;
import Controlleur.ControleurMenu;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FenetrePrincipale fenetre = new FenetrePrincipale();
            new ControleurMenu(fenetre); 
            fenetre.setVisible(true);
        });
    }
}
