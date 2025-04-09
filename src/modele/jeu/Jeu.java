package modele.jeu;
import modele.plateau.Plateau;


public class Jeu extends Thread {
    private Plateau plateau;
    private Joueur j1;
    private Joueur j2;
    protected Coup coupRecu;

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
        // Le jeu s'exécute en boucle
        while (true) {
            // Vérifie si c'est le tour du joueur blanc ou noir et applique son coup
            if (j1.estBlanc()) {

                System.out.println("Tour du joueur blanc");

                Coup c = j1.getCoup();
                appliquerCoup(c);

                synchronized (this) {
                    notify();  // Notifie le joueur noir qu'il peut jouer
                }

                // Attente que le joueur noir fasse son coup
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {

                System.out.println("Tour du joueur noir");

                Coup c = j2.getCoup();
                appliquerCoup(c);

                synchronized (this) {
                    notify();  // Notifie le joueur blanc qu'il peut jouer
                }

                // Attente que le joueur blanc fasse son coup
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
