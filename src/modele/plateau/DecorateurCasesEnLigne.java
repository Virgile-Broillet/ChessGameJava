package modele.plateau;

import modele.jeu.Piece;

import java.util.ArrayList;

public class DecorateurCasesEnLigne extends DecorateurCasesAccessibles {

    /***
     * @name DecorateurCasesEnLigne()
     * @brief constructeur avec le decorateur de base et la piece
     * @param baseDecorateur
     * @param piece
     */
    public DecorateurCasesEnLigne(DecorateurCasesAccessibles baseDecorateur, Piece piece) {
        super(baseDecorateur, piece);
    }

    /***
     * @name getMesCasesPossibles()
     * @brief Override de la fonction pour retourner les cases possible en fonction des déplacement de ce décorateur (ligne)
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getMesCasesPossibles() {
        ArrayList<String> casesPossibles = new ArrayList<>();
        Case position = piece.getCase();

        if (position == null) return casesPossibles;

        int x = position.getX();
        int y = position.getY();

        // Ligne verticale (haut)
        for (int i = y - 1; i >= 0; i--) {
            if (!ajouterSiPossible(casesPossibles, x, i)) break;
        }

        // Ligne verticale (bas)
        for (int i = y + 1; i < Plateau.SIZE_Y; i++) {
            if (!ajouterSiPossible(casesPossibles, x, i)) break;
        }

        // Ligne horizontale (gauche)
        for (int i = x - 1; i >= 0; i--) {
            if (!ajouterSiPossible(casesPossibles, i, y)) break;
        }

        // Ligne horizontale (droite)
        for (int i = x + 1; i < Plateau.SIZE_X; i++) {
            if (!ajouterSiPossible(casesPossibles, i, y)) break;
        }

        return casesPossibles;
    }

    /***
     * @name AjouterSiPossible()
     * @brief Ajoute la case si elle est libre ou occupée par une piece ennemis
     * @param liste
     * @param x
     * @param y
     * @return boolean
     */
    private boolean ajouterSiPossible(ArrayList<String> liste, int x, int y) {
        if (!piece.getPlateau().estDansLesLimites(x, y)) return false;

        if (piece.getPlateau().estCaseLibre(x, y)) {
            liste.add(Case.coordVersString(x, y));
            return true;
        }

        if (piece.getPlateau().estCaseOccupeeParAdversaire(x, y, piece)) {
            liste.add(Case.coordVersString(x, y));
        }

        return false;
    }
}
