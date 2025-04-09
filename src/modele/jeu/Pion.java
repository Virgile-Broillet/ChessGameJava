package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

import java.util.ArrayList;

public class Pion extends Piece {

    private boolean premierDeplacement = true; // Permet de vérifier si le pion est au premier coup

    public Pion(Plateau _plateau, boolean _estBlanc) {
        super(_plateau, _estBlanc);
    }

    @Override
    public ArrayList<Case> getDeplacementsPossibles() {
        ArrayList<Case> deplacements = new ArrayList<>();

        int direction = (this.estBlanc) ? -1 : 1; // Les blancs montent, les noirs descendent

        // Récupérer la case actuelle du pion
        Case positionActuelle = this.getCase();  // Cette méthode existe dans la classe Piece (héritée par Pion)

        int x = -1;  // Initialisation des variables pour éviter les erreurs
        int y = -1;

        // Vérifier si le pion est sur une case (la case n'est pas null)
        if (positionActuelle != null) {
            x = positionActuelle.getX();
            y = positionActuelle.getY();
        } else {
            System.out.println("Le pion n'est pas sur une case.");
        }

        // Avancer d'une case
        if (plateau.estCaseLibre(x, (y + direction))) {
            deplacements.add(plateau.getCase(x, (y + direction)));
        }

        // Avancer de deux cases si c'est le premier déplacement
        if (premierDeplacement && plateau.estCaseLibre(x, (y + direction)) && plateau.estCaseLibre(x, (y + 2 * direction))) {
            // Vérifier qu'il n'y a pas de pièce bloquante sur la case intermédiaire
            if (plateau.estCaseLibre(x, (y + direction))) {
                deplacements.add(plateau.getCase(x, (y + 2 * direction)));  // Ajouter la case à 2 cases si la case intermédiaire est libre
            }
        }

        // Capturer en diagonale (vérifier si une pièce adverse est présente)
        if (plateau.estCaseOccupeeParAdversaire((x - 1), (y + direction), this)) {
            deplacements.add(plateau.getCase((x - 1), (y + direction)));
        }
        if (plateau.estCaseOccupeeParAdversaire((x + 1), (y + direction), this)) {
            deplacements.add(plateau.getCase((x + 1), (y + direction)));
        }

        return deplacements;
    }

    // Méthode pour déplacer le pion et mettre à jour l'état de son premier déplacement
    public void deplacer(Case destination) {
        super.allerSurCase(destination);
        premierDeplacement = false; // Après le premier mouvement, on ne peut plus avancer de deux cases
    }
}
