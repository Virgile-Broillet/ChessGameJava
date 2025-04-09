package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;

public abstract class DecorateurCasesAccessibles {

    protected DecorateurCasesAccessibles baseDecorateur;
    protected Piece piece;
    protected Plateau plateau;

    public DecorateurCasesAccessibles(DecorateurCasesAccessibles _baseDecorateur, Piece _piece) {
        this.baseDecorateur = _baseDecorateur;
        this.piece = _piece;
        this.plateau = _piece.getPlateau();
    }

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
