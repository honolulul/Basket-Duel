package Modele;

//Détection de collision : utilise la distance euclidienne entre le centre du ballon et le centre du panier.

public class Panier {

    // Rayon de la hitbox
    public static final int RAYON = 30;

    // Position
    private double x;
    private double y;

    // Limites de déplacement
    private double limiteGauche;

    private double limiteDroite;

    private double limiteHaute;

    private double limiteBasse;

    // Constructeur
    public Panier(double x, double y, double limiteGauche, double limiteDroite, double limiteHaute,
            double limiteBasse) {
        this.x = x;
        this.y = y;
        this.limiteGauche = limiteGauche;
        this.limiteDroite = limiteDroite;
        this.limiteHaute = limiteHaute;
        this.limiteBasse = limiteBasse;
    }

    // Déplacement du panier (appelé par le Contrôleur / IA)
    public void deplacerHaut(double pas) {
        y = Math.max(limiteHaute + RAYON, y - pas);
    }

    public void deplacerBas(double pas) {
        y = Math.min(limiteBasse - RAYON, y + pas);
    }

    public void deplacerGauche(double pas) {
        x = Math.max(limiteGauche + RAYON, x - pas);
    }

    public void deplacerDroite(double pas) {
        x = Math.min(limiteDroite - RAYON, x + pas);
    }

    // Détection de collision
    /**
     * Vérifie si un ballon (décrit par son centre et son rayon) entre en collision
     * avec le panier.
     * Formule : d = sqrt((xB-xP)² + (yB-yP)²)
     * Collision si d ≤ rayonBallon + RAYON
    */
    public boolean estTouche(double xBallon, double yBallon, int rayonBallon) {
        double dx = xBallon - this.x;
        double dy = yBallon - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= (rayonBallon + RAYON);
    }

    // Getters / Setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getLimiteGauche() {
        return limiteGauche;
    }

    public double getLimiteDroite() {
        return limiteDroite;
    }

    public double getLimiteHaute() {
        return limiteHaute;
    }

    public double getLimiteBasse() {
        return limiteBasse;
    }

    @Override
    public String toString() {
        return String.format("Panier[x=%.1f, y=%.1f, rayon=%d]", x, y, RAYON);
    }
}
