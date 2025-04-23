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
    protected Boolean estCaputuré = false;

    public abstract List<Case> getDeplacementsPossibles();

    public Piece(Plateau _plateau, boolean _estBlanc ) {
        plateau = _plateau;
        estBlanc = _estBlanc;
    }

    public boolean estBlanc() {
        return this.estBlanc;
    }
    public boolean estCaputuré(){return this.estCaputuré; }
    public void quitterLaCase() {
        // Vide la case où la pièce se trouve actuellement
        this.c.setPiece(null); // On enlève la référence de la pièce sur cette case
        this.c = null; // La pièce ne sait plus où elle est
        this.estCaputuré = true;
    }

    public void allerSurCase(Case _c) {
        // Déplace la pièce vers la nouvelle case
        this.c = _c;
        _c.setPiece(this);  // La nouvelle case contient désormais cette pièce
    }

    public Case getCase() {
        return c;
    }

    public Plateau getPlateau() {return this.plateau;}
}
