package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

import java.util.ArrayList;

public class Pion extends Piece {

    public boolean premierDeplacement = true; // Permet de vérifier si le pion est au premier coup

    public Pion(Plateau _plateau, boolean _estBlanc) {
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

        // Avancer de deux cases si c'est le premier déplacement et les cases sont libres
        if (premierDeplacement && plateau.estCaseLibre(x, (y + direction)) && plateau.estCaseLibre(x, (y + 2 * direction))) {
            deplacements.add(plateau.getCase(x, (y + 2 * direction)));  // Ajouter la case à 2 cases si les deux cases sont libres
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

    /***
     * @name deplacer
     * @brief Méthode pour déplacer le pion et mettre à jour l'état de son premier déplacement
     * @param destination
     */
    public void deplacer(Case destination) {
        super.allerSurCase(destination);
        premierDeplacement = false; // Désactive le premier déplacement une fois qu'il a été effectué
    }
}
