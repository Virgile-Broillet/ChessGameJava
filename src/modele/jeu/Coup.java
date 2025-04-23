package modele.jeu;

import modele.plateau.Case;

public class Coup {
    protected Case dep;
    protected Case arr;
    public Coup(Case _dep, Case _arr) {
        dep = _dep;
        arr = _arr;
    }

    /***
     * @name getDepart()
     * @brief getter de la case de départ
     * @return Case
     */
    public Case getDepart() {
        return dep;
    }

    /***
     * @name getArrivee()
     * @brief getter de la case d'arrivee
     * @return Case
     */
    public Case getArrivee() {
        return arr;
    }
}
