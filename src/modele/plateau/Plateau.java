/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;


import modele.jeu.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;


public class Plateau extends Observable {

    public static final int SIZE_X = 8;
    public static final int SIZE_Y = 8;


    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    private Case[][] grilleCases = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées

    public Plateau() {
        initPlateauVide();
    }

    public Case[][] getCases() {
        return grilleCases;
    }

    private void initPlateauVide() {

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleCases[x][y] = new Case(this);
                map.put(grilleCases[x][y], new Point(x, y));
            }

        }

    }

    public void placerPieces() {
        Roi roiB = new Roi(this);
        Reine reineB = new Reine(this);
        Cavalier cavalierB = new Cavalier(this);
        Fou fouB = new Fou(this);
        Pion pionB = new Pion(this);
        Tour tourB = new Tour(this);


        Case cRoiB = grilleCases[4][7];
        Case cReineB = grilleCases[3][7];
        Case cFouB1 = grilleCases[2][7];
        Case cFouB2 = grilleCases[5][7];
        Case cCavalierB1 = grilleCases[6][7];
        Case cCavalierB2 = grilleCases[1][7];
        Case cTourB1 = grilleCases[0][7];
        Case cTourB2 = grilleCases[7][7];

        for (int i = 0 ; i < 8; i++){
            Case cPionB = grilleCases[i][6];
            pionB.allerSurCase(cPionB);
        }
        roiB.allerSurCase(cRoiB);
        reineB.allerSurCase(cReineB);
        fouB.allerSurCase(cFouB1);
        cavalierB.allerSurCase(cFouB2);
        tourB.allerSurCase(cCavalierB1);

        setChanged();
        notifyObservers();

    }

    public void arriverCase(Case c, Piece p) {

        c.p = p;

    }

    public void deplacerPiece(Case c1, Case c2) {
        if (c1.p != null) {
            c1.p.allerSurCase(c2);

        }
        setChanged();
        notifyObservers();

    }


    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Case caseALaPosition(Point p) {
        Case retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleCases[p.x][p.y];
        }
        return retour;
    }


}
