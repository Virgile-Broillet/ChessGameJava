package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;

public abstract class DecorateurCasesAccessibles {

    protected DecorateurCasesAccessibles baseDecorateur;
    protected Piece piece;
    protected Plateau plateau;

    /***
     * @name DecorateurCasesAccessible()
     * @brief constructeur avec le decorateur de base et la piece
     * @param _baseDecorateur
     * @param _piece
     */
    public DecorateurCasesAccessibles(DecorateurCasesAccessibles _baseDecorateur, Piece _piece) {
        this.baseDecorateur = _baseDecorateur;
        this.piece = _piece;
        this.plateau = _piece.getPlateau();
    }

    /***
     * @name getCasesPossibles()
     * @brief renvoie les cases possibles au déplacement
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> getCasesPossibles() {
        ArrayList<ArrayList<String>> retour = new ArrayList<>();

        if (baseDecorateur != null) {
            retour.addAll(baseDecorateur.getCasesPossibles());
        }

        retour.add(getMesCasesPossibles());
        return retour;
    }

    public abstract ArrayList<String> getMesCasesPossibles();
}
