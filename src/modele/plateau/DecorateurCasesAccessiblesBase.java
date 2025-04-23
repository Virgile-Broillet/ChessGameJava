package modele.plateau;

import modele.jeu.Piece;
import java.util.ArrayList;

public class DecorateurCasesAccessiblesBase {
    private Piece piece;
    private Plateau plateau;

    // Nouveau constructeur acceptant à la fois la Piece et le Plateau
    public DecorateurCasesAccessiblesBase(Piece piece, Plateau plateau) {
        this.piece = piece;
        this.plateau = plateau;
    }

    // Méthode pour accéder au plateau si nécessaire
    public Plateau getPlateau() {
        return plateau;
    }

    // Méthode pour accéder à la pièce si nécessaire
    public Piece getPiece() {
        return piece;
    }
}
