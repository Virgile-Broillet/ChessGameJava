package modele.jeu;

import modele.plateau.Case;
import modele.plateau.DecorateurCasesAccessibles;
import modele.plateau.Direction;
import modele.plateau.Plateau;

import java.util.List;

/**
 * Entités amenées à bouger
 */
public abstract class Piece {

    protected Case c;
    protected Plateau plateau;
    protected DecorateurCasesAccessibles decorateurCasesAccessibles;
    public Boolean estBlanc;

    public abstract List<Case> getDeplacementsPossibles();

    public Piece(Plateau _plateau, boolean _estBlanc ) {
        plateau = _plateau;
        estBlanc = _estBlanc;
    }

    public void quitterCase() {
        c.quitterLaCase();
    }

    public void allerSurCase(Case _c) {
        if (c != null) {
            quitterCase();
        }
        c = _c;
        plateau.arriverCase(c, this);
    }

    public Case getCase() {
        return c;
    }

    public Plateau getPlateau() {return this.plateau;}
}
