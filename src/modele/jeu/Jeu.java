package modele.jeu;
import modele.plateau.Plateau;


public class Jeu extends Thread {
    private Plateau plateau;
    private Joueur j1;
    private Joueur j2;
    protected Coup coupRecu;
    private boolean tourBlanc = true;

    public Jeu() {
        plateau = new Plateau();
        plateau.placerPieces();

        // Initialisation des joueurs
        j1 = new Joueur(this, true);  // Joueur 1 est blanc
        j2 = new Joueur(this, false); // Joueur 2 est noir

        start();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void placerPieces() {
        plateau.placerPieces();
    }

    public void envoyerCoup(Coup c) {
        coupRecu = c;

        synchronized (this) {
            notify();
        }
    }

    public void appliquerCoup(Coup coup) {
        plateau.deplacerPiece(coup.dep, coup.arr);
    }

    public void run() {
        jouerPartie();
    }

    public void jouerPartie() {
        while (true) {
            Coup c;
            if (tourBlanc) {
                c = j1.getCoup();
                if (c.getDepart().getPiece().estBlanc) {
                    appliquerCoup(c);
                    tourBlanc = false;
                } else {
                    System.out.println("Ce n'est pas aux noirs de jouer !");
                }
            } else {
                c = j2.getCoup();
                if (!c.getDepart().getPiece().estBlanc) {
                    appliquerCoup(c);
                    tourBlanc = true;
                } else {
                    System.out.println("Ce n'est pas aux blancs de jouer !");
                }
            }
        }
    }


}
