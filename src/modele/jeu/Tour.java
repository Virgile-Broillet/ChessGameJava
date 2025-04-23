package modele.jeu;

import modele.plateau.*;

import java.util.ArrayList;
import java.util.List;

public class Tour extends Piece {

    public Tour(Plateau plateau, boolean estBlanc) {
        super(plateau, estBlanc);
    }

    @Override
    public List<Case> getDeplacementsPossibles() {
        List<Case> deplacements = new ArrayList<>();

        // Utilisation du décorateur linéaire uniquement
        DecorateurCasesAccessibles decorateur = new DecorateurCasesEnLigne(null, this);

        ArrayList<ArrayList<String>> toutesLesCases = decorateur.getCasesPossibles();

        for (ArrayList<String> chemin : toutesLesCases) {
            for (String coord : chemin) {
                Case c = convertirCoordEnCase(coord);
                if (c != null) deplacements.add(c);
            }
        }

        return deplacements;
    }

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
