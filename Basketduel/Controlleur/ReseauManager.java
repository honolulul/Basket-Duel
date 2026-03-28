package Controlleur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReseauManager {
    private ServerSocket serveur;
    private Socket client;
    private DataOutputStream sortie;
    private DataInputStream entree;
    private boolean isServer;

    // Pour créer une partie (ordinateur 1)
    public boolean hebergerActif(int port) {
        try {
            serveur = new ServerSocket(port);
            System.out.println("Attente de connexion du joueur adverse sur le port " + port);

            client = serveur.accept(); // on attend que le joueur 2 se connecte (le jeu est bloqué ici)
            System.out.println("Joueur 2 est connecté !");

            sortie = new DataOutputStream(client.getOutputStream());
            entree = new DataInputStream(client.getInputStream());
            isServer = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Pour rejoindre une partie déjà créée (ordinateur 2)
    public boolean rejoindrePartie(String ip, int port) {
        try {
            client = new Socket(ip, port);
            System.out.println("Connecté au joueur 1 !");

            sortie = new DataOutputStream(client.getOutputStream());
            entree = new DataInputStream(client.getInputStream());
            isServer = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Le controleur jeu appelle ça quand la boule part
    public void envoyerTir(double a, double f) {
        try {
            sortie.writeDouble(a);
            sortie.writeDouble(f);
            sortie.flush();
            System.out.println("On vient d'envoyer le tir : angle=" + a + " force=" + f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Réception des infos du tir adverse (bloquant aussi)
    public double[] recevoirTir() {
        try {
            System.out.println("Attente du tir de l'autre joueur...");
            double angle = entree.readDouble();
            double force = entree.readDouble();

            double[] tir = new double[2];
            tir[0] = angle;
            tir[1] = force;
            System.out.println("Tir ennemi reçu : angle=" + angle + " force=" + force);
            return tir;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Envoie l'état du jeu (scores) pour synchronisation
    public void synchroniserPartie(int scoreLocal, int scoreAdverse) throws IOException {
        if (sortie != null) {
            sortie.writeInt(scoreLocal);
            sortie.writeInt(scoreAdverse);
            sortie.flush();
        }
    }

    // Fermeture propre pour éviter les problèmes de ports déjà utilisés
    public void fermerTout() {
        try {
            if (entree != null) entree.close();
            if (sortie != null) sortie.close();
            if (client != null) client.close();
            if (serveur != null) serveur.close();
        } catch (Exception e) {
            // on ignore l'erreur
        }
    }

    public boolean isServer() {
        return isServer;
    }

    public boolean isConnected() {
        return client != null && !client.isClosed();
    }
}
