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


public class Reine extends Piece
{
    public Reine(Plateau _plateau) {
        super(_plateau);
        decorateurCasesAccessibles = new DecorateurCasesEnLigne(null);
    }


}
