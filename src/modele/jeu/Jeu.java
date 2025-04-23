package modele.jeu;
import modele.plateau.Case;
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


    public boolean coupEstValide(Coup coup, boolean estBlanc) {
        Case depart = coup.dep;
        Case arrivee = coup.arr;

        Piece pieceADeplacer = depart.getPiece();
        Piece pieceCapturee = arrivee.getPiece(); // peut être null

        // 1. Appliquer temporairement le coup
        depart.quitterLaCase();
        if (pieceCapturee != null) {
            pieceCapturee.quitterLaCase();
        }
        pieceADeplacer.allerSurCase(arrivee);

        // 2. Vérifier si le roi est en échec
        boolean roiEnEchec = plateau.estEnEchec(estBlanc);

        // 3. Annuler le coup (restaurer l'état)
        pieceADeplacer.quitterLaCase();
        arrivee.setPiece(pieceCapturee); // Remettre la pièce capturée si elle existait
        depart.setPiece(pieceADeplacer);

        return !roiEnEchec;
    }



    public void run() {
        jouerPartie();
    }

    public void jouerPartie() {
        while (true) {
            Coup c;
            if (tourBlanc) {
                c = j1.getCoup(); // Le joueur blanc joue

                if (c.getDepart().equals(c.getArrivee())) {
                    System.out.println("Sélection annulée, choisissez une autre pièce.");
                    continue; // Ne pas changer de tour
                }

                if (c.getDepart().getPiece() == null) {
                    System.out.println("Aucune pièce sélectionnée !");
                    continue;
                }

                if (c.getDepart().getPiece().estBlanc) {
                    appliquerCoup(c);

                    if (c.getDepart().getPiece() instanceof Pion) {
                        ((Pion) c.getDepart().getPiece()).premierDeplacement = false;
                    }

                    tourBlanc = false;
                } else {
                    System.out.println("Ce n'est pas aux noirs de jouer !");
                }

            } else {
                c = j2.getCoup(); // Le joueur noir joue

                if (c.getDepart().equals(c.getArrivee())) {
                    System.out.println("Sélection annulée, choisissez une autre pièce.");
                    continue; // Ne pas changer de tour
                }

                if (c.getDepart().getPiece() == null) {
                    System.out.println("Aucune pièce sélectionnée !");
                    continue;
                }

                if (!c.getDepart().getPiece().estBlanc) {
                    appliquerCoup(c);

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
