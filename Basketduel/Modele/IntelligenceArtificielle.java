package Modele;

import java.util.Random;

public class IntelligenceArtificielle {

    public static double[] calculerTir(double xJoueur, double yJoueur, double xPanier, double yPanier, double difficulte) {
        double dx = xPanier - xJoueur;
        double dy = yPanier - yJoueur;
                                       
        double g = Ballon.GRAVITE;
        
        Random rand = new Random();
        // Choix d'un angle aléatoire en cloche entre 45 et 70 degrés
        double angleDeg = 45 + rand.nextDouble() * 25; 
        double angleRad = Math.toRadians(angleDeg);
        
        double denominateur = dy + dx * Math.tan(angleRad);
        
        // Si denominateur est <= 0, l'angle choisi ne permet pas d'atteindre la cible
        // On ajuste l'angle jusqu'à trouver une solution
        int tentatives = 0;
        while (denominateur <= 0 && tentatives < 20) {
            angleDeg += 2; // on augmente l'angle pour avoir une cloche plus haute
            angleRad = Math.toRadians(angleDeg);
            denominateur = dy + dx * Math.tan(angleRad);
            tentatives++;
        }
        
        // Sécurité si aucune trajectoire trouvée (improbable si le panier est à portée)
        if (denominateur <= 0) {
            denominateur = 1.0; 
        }
        
        double numerateur = 0.5 * g * dx * dx;
        double cosA = Math.cos(angleRad);
        
        double v0_carre = numerateur / (cosA * cosA * denominateur);
        double puissance = Math.sqrt(v0_carre);
        
        // Ajout d'une marge d'erreur basée sur la difficulté
        if (difficulte > 0) {
            double erreurAngle = (rand.nextDouble() * 10 - 5) * difficulte; // +/- 5 degrés max
            double erreurPuissance = (rand.nextDouble() * 50 - 25) * difficulte; // +/- 25 pixels/s max
            
            angleDeg += erreurAngle;
            puissance += erreurPuissance;
        }
        
        return new double[]{angleDeg, puissance};
    }
}
