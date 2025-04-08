/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.jeu;

import modele.plateau.Case;
import modele.plateau.DecorateurCasesAccessibles;
import modele.plateau.DecorateurCasesEnLigne;
import modele.plateau.Plateau;

import java.util.ArrayList;


public class Roi extends Piece
{
    public Roi(Plateau _plateau, boolean _estBlanc) {
        super(_plateau, _estBlanc);
        decorateurCasesAccessibles = new DecorateurCasesEnLigne(null);
    }

    @Override
    public ArrayList<Case> getDeplacementsPossibles() {
        return null;
    }

}
