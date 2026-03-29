package Controlleur;

import Modele.Joueur;
import Modele.Partie;
import Modele.JoueurIA;
import Vue.FenetrePrincipale;

public class ControleurMenu {

    private static final int PORT_DEFAUT   = 5000;
    private static final int TOURS_DEFAUT  = 10; 
    private static final int NIVEAU_IA_DEF = 1;   // 1=Facile 2=Moyen 3=Difficile

    private final FenetrePrincipale fenetre;

    private String  pseudoJ1       = "Joueur 1";
    private String  pseudoJ2       = "Joueur 2";
    private int     pointsVictoire = 5;
    private String  modeAdversaire = "LOCAL"; // "LOCAL" | "IA" | "RESEAU"
    private int     niveauIA       = NIVEAU_IA_DEF;
    private String  ipServeur      = "localhost";
    private int     portReseau     = PORT_DEFAUT;

    private Partie        partie;
    private ControleurJeu controleurJeu;
    private ReseauManager reseauManager;

    public ControleurMenu(FenetrePrincipale fenetre) {
        this.fenetre = fenetre;
    }

    public void allerMenu() {
        fenetre.afficherEcran(FenetrePrincipale.ECRAN_MENU);
    }

    public void allerCreerPartie() {
        fenetre.afficherEcran(FenetrePrincipale.ECRAN_CREER);
    }

    public void allerRejoindrePartie() {
        fenetre.afficherEcran(FenetrePrincipale.ECRAN_REJOINDRE);
    }

    public void quitter() {
        if (reseauManager != null) reseauManager.fermerTout();
        System.exit(0);
    }

    
    public void validerParametres(String pseudo1, String pseudo2,
                                  int pointsVict, String mode, int niveauIA) {

        this.pseudoJ1       = (pseudo1 == null || pseudo1.isBlank()) ? "Joueur 1" : pseudo1.trim();
        this.pseudoJ2       = (pseudo2 == null || pseudo2.isBlank()) ? "Joueur 2" : pseudo2.trim();
        this.pointsVictoire = Math.max(1, pointsVict);
        this.modeAdversaire = mode;
        this.niveauIA       = Math.max(1, Math.min(3, niveauIA));

        fenetre.setPseudoJ1(this.pseudoJ1);
        fenetre.setPseudoJ2(this.pseudoJ2);
        fenetre.setPointsVictoire(this.pointsVictoire);
        fenetre.setModeAdversaire(this.modeAdversaire);
    }

    public void lancerPartieLocale() {
        creerPartieEtControleur();
        fenetre.lancerPartie();
    }

    public boolean lancerPartieServeur() {
        reseauManager = new ReseauManager();
        boolean ok = reseauManager.hebergerActif(portReseau);

        if (ok) {
            fenetre.setModeAdversaire("RESEAU");
            creerPartieEtControleur();
            fenetre.lancerPartie();
        }
        return ok;
    }

    public boolean rejoindrePartieReseau(String pseudo, String ip, int port) {
        this.pseudoJ2   = (pseudo == null || pseudo.isBlank()) ? "Joueur 2" : pseudo.trim();
        this.ipServeur  = ip;
        this.portReseau = port;

        fenetre.setPseudoJ2(this.pseudoJ2);
        fenetre.setModeAdversaire("RESEAU");

        reseauManager = new ReseauManager();
        boolean ok = reseauManager.rejoindrePartie(ip, port);

        if (ok) {
            creerPartieEtControleur();
            fenetre.lancerPartie();
        }
        return ok;
    }

    public void relancerPartie() {
        if (reseauManager != null) {
            reseauManager.fermerTout();
            reseauManager = null;
        }
        creerPartieEtControleur();
        fenetre.relancerPartie();
    }

    private void creerPartieEtControleur() {

        // Création des joueurs
        Joueur j1 = new Joueur();
        j1.setNom(pseudoJ1);

        Joueur j2;
        if ("IA".equals(modeAdversaire)) {
            j2 = new JoueurIA(pseudoJ2, niveauIA);
        } else {
            j2 = new Joueur();
            j2.setNom(pseudoJ2);
        }

        // Création de la partie
        this.partie = new Partie(j1, j2);
        this.partie.setpseudoJoueur(pseudoJ1); // synchronise le nom dans Partie

        int largeur = fenetre.getWidth()  > 0 ? fenetre.getWidth()  : 800;
        int hauteur = fenetre.getHeight() > 0 ? fenetre.getHeight() : 600;

        this.controleurJeu = new ControleurJeu(largeur, hauteur, niveauIA, TOURS_DEFAUT);

        fenetre.setPartie(this.partie);
        fenetre.setControleurJeu(this.controleurJeu);

        if (reseauManager != null) {
            fenetre.setReseauManager(this.reseauManager);
        }
    }

    public String  getPseudoJ1()       { return pseudoJ1; }
    public String  getPseudoJ2()       { return pseudoJ2; }
    public int     getPointsVictoire() { return pointsVictoire; }
    public String  getModeAdversaire() { return modeAdversaire; }
    public int     getNiveauIA()       { return niveauIA; }
    public String  getIpServeur()      { return ipServeur; }
    public int     getPortReseau()     { return portReseau; }

    public void setPseudoJ1(String p)       { this.pseudoJ1 = p; }
    public void setPseudoJ2(String p)       { this.pseudoJ2 = p; }
    public void setPointsVictoire(int n)    { this.pointsVictoire = n; }
    public void setModeAdversaire(String m) { this.modeAdversaire = m; }
    public void setNiveauIA(int n)          { this.niveauIA = n; }
    public void setIpServeur(String ip)     { this.ipServeur = ip; }
    public void setPortReseau(int p)        { this.portReseau = p; }

    public Partie        getPartie()        { return partie; }
    public ControleurJeu getControleurJeu() { return controleurJeu; }
    public ReseauManager getReseauManager() { return reseauManager; }
}
