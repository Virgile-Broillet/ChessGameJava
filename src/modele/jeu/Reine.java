package modele.jeu;

import modele.plateau.*;

import java.util.ArrayList;
import java.util.List;

public class Reine extends Piece {

    public Reine(Plateau plateau, boolean estBlanc) {
        super(plateau, estBlanc);
    }

    @Override
    public List<Case> getDeplacementsPossibles() {
        List<Case> deplacements = new ArrayList<>();

        // On crée un décorateur vide (null) comme base
        DecorateurCasesAccessibles base = null;

        // D'abord le décorateur ligne (tour)
        base = new DecorateurCasesEnLigne(base, this);

        // Ensuite, on ajoute les déplacements diagonaux (fou)
        base = new DecorateurCasesEnDiagonale(base, this);

        // On récupère toutes les cases possibles via les décorateurs
        ArrayList<ArrayList<String>> toutesLesCases = base.getCasesPossibles();

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
        return estBlanc ? "♕" : "♛";
    }
}
