package modele.jeu;

import modele.plateau.Case;

public class Coup {
    protected Case dep;
    protected Case arr;
    public Coup(Case _dep, Case _arr) {
        dep = _dep;
        arr = _arr;
    }

    public Case getDepart() {
        return dep;
    }

    public Case getArrivee() {
        return arr;
    }
}
