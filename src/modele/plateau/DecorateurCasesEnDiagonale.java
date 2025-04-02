package modele.plateau;

import java.util.ArrayList;

public class DecorateurCasesEnDiagonale extends DecorateurCasesAccessibles {
    public DecorateurCasesEnDiagonale(DecorateurCasesAccessibles _baseDecorateur) {
        super(_baseDecorateur);
    }

    @Override
    public ArrayList<String> getMesCasesPossibles() {
        ArrayList<String> casesPossibles = new ArrayList<>();
        // Logique pour les déplacements en diagonale (ex. Fou)
        casesPossibles.add("A1"); // Exemple simplifié
        casesPossibles.add("B2");
        return casesPossibles;
    }

    @Override
    public ArrayList<ArrayList<String>> getCasesPossibles() {
        ArrayList<ArrayList<String>> retour = super.getCasesPossibles();
        retour.add(getMesCasesPossibles());
        return retour;
    }
}