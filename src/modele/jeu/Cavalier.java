package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

import java.util.ArrayList;

public class Cavalier extends Piece {

    public Cavalier(Plateau _plateau, boolean _estBlanc) {
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

        // Directions possibles du cavalier (mouvement en L)
        int[][] directions = {
                {-2, -1}, {-2, 1},  // Haut-gauche, Haut-droit
                {-1, -2}, {-1, 2},  // Gauche-haut, Droite-haut
                { 1, -2}, { 1, 2},  // Gauche-bas, Droite-bas
                { 2, -1}, { 2, 1}   // Bas-gauche, Bas-droit
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
        return estBlanc ? "♘" : "♞";
    }
}
