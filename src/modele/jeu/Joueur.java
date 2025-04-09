package modele.jeu;

public class Joueur {
    private Jeu jeu;
    private boolean estBlanc;

    public Joueur(Jeu _jeu, boolean _estBlanc) {
        jeu = _jeu;
        estBlanc = _estBlanc;
    }

    public Coup getCoup() {

        synchronized (jeu) {
            try {
                jeu.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return jeu.coupRecu;
    }

    public boolean estBlanc() {
        return estBlanc;
    }
}
