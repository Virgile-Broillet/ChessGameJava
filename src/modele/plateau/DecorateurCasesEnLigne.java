package modele.plateau;

import java.util.ArrayList;

public class DecorateurCasesEnLigne extends DecorateurCasesAccessibles {
    public DecorateurCasesEnLigne(DecorateurCasesAccessibles _baseDecorateur) {
        super(_baseDecorateur);
    }

    @Override
    public ArrayList<String> getMesCasesPossibles() {
        ArrayList<String> casesPossibles = new ArrayList<>();
        // Logique pour les déplacements en ligne (ex. Tour)
        casesPossibles.add("A1"); // Exemple simplifié
        casesPossibles.add("A2");
        return casesPossibles;
    }

    @Override
    public ArrayList<ArrayList<String>> getCasesPossibles() {
        ArrayList<ArrayList<String>> retour = super.getCasesPossibles();
        retour.add(getMesCasesPossibles());
        return retour;
    }
}

