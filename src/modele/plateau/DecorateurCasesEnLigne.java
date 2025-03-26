package modele.plateau;

import java.util.ArrayList;

public class DecorateurCasesEnLigne extends DecorateurCasesAccessibles {
    public DecorateurCasesEnLigne(DecorateurCasesAccessibles _baseDecorateur) {
        super(_baseDecorateur);
    }

    @Override
    public ArrayList<String> getMesCasesPossibles() {
        return null;
    }
}
