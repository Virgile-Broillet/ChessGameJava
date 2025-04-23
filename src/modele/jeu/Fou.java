package modele.jeu;

import modele.plateau.*;

import java.util.ArrayList;
import java.util.List;

public class Fou extends Piece {

    public Fou(Plateau plateau, boolean estBlanc) {
        super(plateau, estBlanc);
    }

    /***
     * @name getDéplacementPossible()
     * @brief Override de la fonction pour gérer les déplacement possible spécifique
     * @return ArrayList<Case>
     */
    @Override
    public List<Case> getDeplacementsPossibles() {
        List<Case> deplacements = new ArrayList<>();

        // Utilisation du décorateur diagonal uniquement
        DecorateurCasesAccessibles decorateur = new DecorateurCasesEnDiagonale(null, this);

        ArrayList<ArrayList<String>> toutesLesCases = decorateur.getCasesPossibles();

        for (ArrayList<String> chemin : toutesLesCases) {
            for (String coord : chemin) {
                Case c = convertirCoordEnCase(coord);
                if (c != null) deplacements.add(c);
            }
        }

        return deplacements;
    }

    /***
     * @name convertirCoordEnCases()
     * @param coord
     * @return Case
     */
    private Case convertirCoordEnCase(String coord) {
        if (coord.length() != 2) return null;
        char colonne = coord.charAt(0);
        int ligne = Character.getNumericValue(coord.charAt(1));

        int x = colonne - 'A';
        int y = 8 - ligne;

        return plateau.getCase(x, y);
    }

    @Override
    public String toString() {
        return estBlanc ? "♗" : "♝";
    }
}
