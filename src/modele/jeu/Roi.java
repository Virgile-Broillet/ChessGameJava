package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;
import java.util.ArrayList;
import java.util.List;

public class Roi extends Piece {

    public Roi(Plateau _plateau, boolean _estBlanc) {
        super(_plateau, _estBlanc);
    }

    /***
     * @name getDéplacementPossible()
     * @brief Override de la fonction pour gérer les déplacement possible spécifique
     * @return ArrayList<Case>
     */
    @Override
    public ArrayList<Case> getDeplacementsPossibles() {
        ArrayList<Case> deplacements = new ArrayList<>();
        Case positionActuelle = this.getCase();

        if (positionActuelle == null) return deplacements;

        int x = positionActuelle.getX();
        int y = positionActuelle.getY();

        // 8 directions autour du roi
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Case caseCible = plateau.getCase(newX, newY);

                // Vérifie que la case est libre ou occupée par un adversaire
                if (caseCible.estLibre() || plateau.estCaseOccupeeParAdversaire(newX, newY, this)) {

                    // Vérifie que le roi ne va pas sur une case attaquée
                    if (!plateau.estCaseMenacee(caseCible, estBlanc)) {
                        deplacements.add(caseCible);
                    }
                }
            }
        }

        return deplacements;
    }



    /***
     * @name deplacementMetEnEchec()
     * @param caseCible
     * @brief Vérifie si le déplacement du roi sur la case cible le met en échec.
     * @return boolean
     */
    private boolean deplacementMetEnEchec(Case caseCible) {
        Case anciennePosition = this.getCase();
        Piece pieceCapturee = caseCible.getPiece();

        // Déplacer temporairement
        anciennePosition.setPiece(null);
        caseCible.setPiece(this);

        boolean enEchec = estEnEchec();

        // Restaurer la position initiale
        caseCible.setPiece(pieceCapturee);
        anciennePosition.setPiece(this);

        return enEchec;
    }

    /***
     * @name estEnEchec()
     * @brief Vérifie si le Roi est en échec.
     * @return boolean
     */
    public boolean estEnEchec() {
        Case positionRoi = this.getCase();
        if (positionRoi == null) return false; // Sécurité si le roi n'est pas bien placé

        // Vérifier si une pièce adverse menace la case du roi
        for (Piece piece : plateau.getPiecesAdverses(this)) {
            // Si la position du roi est dans les déplacements possibles de la pièce adverse
            List<Case> casesMenacees = piece.getDeplacementsPossibles();
            if (casesMenacees.contains(positionRoi)) {
                System.out.println("️Le Roi est en échec !");
                return true; // Le Roi est en échec
            }
        }

        return false; // Le Roi n'est pas en échec
    }


    @Override
    public String toString() {
        return estBlanc ? "♔" : "♚";
    }
}
