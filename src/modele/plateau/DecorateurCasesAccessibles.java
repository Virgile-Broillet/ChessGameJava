package modele.plateau;

import java.util.ArrayList;

public abstract class DecorateurCasesAccessibles {

    private DecorateurCasesAccessibles baseDecorateur;

    public DecorateurCasesAccessibles(DecorateurCasesAccessibles _baseDecorateur) {
        baseDecorateur = _baseDecorateur;
    }

    public ArrayList<ArrayList<String>> getCasesPossibles() {
        ArrayList<ArrayList<String>> retour = new ArrayList<ArrayList<String>>();

        // Si un décorateur de base existe, ajouter ses cases
        if (baseDecorateur != null) {
            retour.addAll(baseDecorateur.getCasesPossibles());
        }

        retour.add(getMesCasesPossibles()); // Ajouter les cases spécifiques à ce décorateur
        return retour;
    }


    public abstract ArrayList<String> getMesCasesPossibles();


}
