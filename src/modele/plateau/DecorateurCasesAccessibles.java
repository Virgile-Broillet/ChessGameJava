package modele.plateau;

import java.util.ArrayList;

public abstract class DecorateurCasesAccessibles {

    private DecorateurCasesAccessibles baseDecorateur;

    public DecorateurCasesAccessibles(DecorateurCasesAccessibles _baseDecorateur) {
        baseDecorateur = _baseDecorateur;
    }

    public ArrayList<ArrayList<String>> getCasesPossibles() {
        ArrayList<ArrayList<String>> retour = new ArrayList<ArrayList<String>>();

        if (baseDecorateur != null) {
            // ajouter les cases récupérées par le décorateur de base
        }

        return retour;
    }

    public abstract ArrayList<String> getMesCasesPossibles();


}
