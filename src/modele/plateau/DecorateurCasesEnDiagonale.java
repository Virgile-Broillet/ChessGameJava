package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;

public class DecorateurCasesEnDiagonale extends DecorateurCasesAccessibles {

    /***
     * @name DecorateurCasesEnDiagonale()
     * @brief constructeur avec le decorateur de base et la piece
     * @param _baseDecorateur
     * @param _piece
     */
    public DecorateurCasesEnDiagonale(DecorateurCasesAccessibles _baseDecorateur, Piece _piece) {
        super(_baseDecorateur, _piece);
    }

    /***
     * @name getMesCasesPossibles()
     * @brief Override de la fonction pour retourner les cases possible en fonction des déplacement de ce décorateur (diagonale)
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getMesCasesPossibles() {
        ArrayList<String> casesPossibles = new ArrayList<>();
        Case position = piece.getCase();

        if (position == null) return casesPossibles;  // Si la pièce n'a pas de position, pas de cases possibles

        int x = position.getX();
        int y = position.getY();

        // Diagonale haut-gauche (x--, y--)
        for (int i = 1; i < 8; i++) {
            if (!ajouterSiPossible(casesPossibles, x - i, y - i)) break;
        }

        // Diagonale haut-droite (x++, y--)
        for (int i = 1; i < 8; i++) {
            if (!ajouterSiPossible(casesPossibles, x + i, y - i)) break;
        }

        // Diagonale bas-gauche (x--, y++)
        for (int i = 1; i < 8; i++) {
            if (!ajouterSiPossible(casesPossibles, x - i, y + i)) break;
        }

        // Diagonale bas-droite (x++, y++)
        for (int i = 1; i < 8; i++) {
            if (!ajouterSiPossible(casesPossibles, x + i, y + i)) break;
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
        if (!plateau.estDansLesLimites(x, y)) return false;  // Vérifie si la case est dans les limites du plateau

        if (plateau.estCaseLibre(x, y)) {
            liste.add(Case.coordVersString(x, y));  // Ajoute la case si elle est libre
            return true;
        }

        // Si la case est occupée par une pièce adverse, on ajoute la case comme possibilité
        if (plateau.estCaseOccupeeParAdversaire(x, y, piece)) {
            liste.add(Case.coordVersString(x, y));
        }

        return false;  // Arrêter si une pièce alliée est rencontrée
    }
}
