package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

import java.util.ArrayList;

public class Tour extends Piece {

    public Tour(Plateau _plateau, boolean _estBlanc) {
        super(_plateau, _estBlanc);
    }

    @Override
    public ArrayList<Case> getDeplacementsPossibles() {
        ArrayList<Case> deplacements = new ArrayList<>();
        Case positionActuelle = this.getCase();

        if (positionActuelle == null) return deplacements;

        int x = positionActuelle.getX();
        int y = positionActuelle.getY();

        // Déplacements en ligne droite (horizontal et vertical)
        // Haut
        for (int i = y - 1; i >= 0; i--) {
            Case caseCible = plateau.getCase(x, i);
            if (caseCible.estLibre()) {
                deplacements.add(caseCible);
            } else if (plateau.estCaseOccupeeParAdversaire(x, i, this)) {
                deplacements.add(caseCible);
                break; // Une pièce adverse peut être capturée
            } else {
                break; // Bloqué par une pièce alliée
            }
        }

        // Bas
        for (int i = y + 1; i < 8; i++) {
            Case caseCible = plateau.getCase(x, i);
            if (caseCible.estLibre()) {
                deplacements.add(caseCible);
            } else if (plateau.estCaseOccupeeParAdversaire(x, i, this)) {
                deplacements.add(caseCible);
                break; // Une pièce adverse peut être capturée
            } else {
                break; // Bloqué par une pièce alliée
            }
        }

        // Gauche
        for (int i = x - 1; i >= 0; i--) {
            Case caseCible = plateau.getCase(i, y);
            if (caseCible.estLibre()) {
                deplacements.add(caseCible);
            } else if (plateau.estCaseOccupeeParAdversaire(i, y, this)) {
                deplacements.add(caseCible);
                break; // Une pièce adverse peut être capturée
            } else {
                break; // Bloqué par une pièce alliée
            }
        }

        // Droite
        for (int i = x + 1; i < 8; i++) {
            Case caseCible = plateau.getCase(i, y);
            if (caseCible.estLibre()) {
                deplacements.add(caseCible);
            } else if (plateau.estCaseOccupeeParAdversaire(i, y, this)) {
                deplacements.add(caseCible);
                break; // Une pièce adverse peut être capturée
            } else {
                break; // Bloqué par une pièce alliée
            }
        }

        return deplacements;
    }

    @Override
    public String toString() {
        return estBlanc ? "♖" : "♜";
    }
}
