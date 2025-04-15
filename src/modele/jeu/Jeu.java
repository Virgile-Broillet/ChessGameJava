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
                c = j1.getCoup(); // Le joueur blanc joue
                if (c.getDepart().getPiece().estBlanc) { // Vérifie que la pièce du joueur blanc est bien sélectionnée
                    appliquerCoup(c);  // Applique le coup (déplacement)
                    // Si le coup a été effectué, on met à jour le premier déplacement du pion
                    if (c.getDepart().getPiece() instanceof Pion) {
                        ((Pion) c.getDepart().getPiece()).premierDeplacement = false;
                    }
                    tourBlanc = false;
                } else {
                    System.out.println("Ce n'est pas aux noirs de jouer !");
                }
            } else {
                c = j2.getCoup(); // Le joueur noir joue
                if (!c.getDepart().getPiece().estBlanc) { // Vérifie que la pièce du joueur noir est bien sélectionnée
                    appliquerCoup(c);  // Applique le coup (déplacement)
                    // Si le coup a été effectué, on met à jour le premier déplacement du pion
                    if (c.getDepart().getPiece() instanceof Pion) {
                        ((Pion) c.getDepart().getPiece()).premierDeplacement = false;
                    }
                    tourBlanc = true;
                } else {
                    System.out.println("Ce n'est pas aux blancs de jouer !");
                }
            }
        }
    }

}
