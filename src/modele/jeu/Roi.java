package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

import java.util.ArrayList;

public class Roi extends Piece {

    public Roi(Plateau _plateau, boolean _estBlanc) {
        super(_plateau, _estBlanc);
    }

    @Override
    public ArrayList<Case> getDeplacementsPossibles() {
        ArrayList<Case> deplacements = new ArrayList<>();

        Case positionActuelle = this.getCase();

        if (positionActuelle == null) return deplacements;

        int x = positionActuelle.getX();
        int y = positionActuelle.getY();

        // Directions possibles (1 case autour du roi)
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
                if (caseCible.estLibre() || plateau.estCaseOccupeeParAdversaire(newX, newY, this)) {
                    deplacements.add(caseCible);
                }
            }
        }

        return deplacements;
    }

    @Override
    public String toString() {
        return estBlanc ? "♔" : "♚";
    }
}
