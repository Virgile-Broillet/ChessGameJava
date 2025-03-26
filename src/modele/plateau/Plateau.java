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

        Cavalier cavalierB1 = new Cavalier(this);
        Cavalier cavalierB2 = new Cavalier(this);


        Fou fouB1 = new Fou(this);
        Fou fouB2 = new Fou(this);

        Tour tourB1 = new Tour(this);
        Tour tourB2 = new Tour(this);

        Pion pionB8 = new Pion(this);
        Pion pionB7 = new Pion(this);
        Pion pionB6 = new Pion(this);
        Pion pionB5 = new Pion(this);
        Pion pionB4 = new Pion(this);
        Pion pionB3 = new Pion(this);
        Pion pionB2 = new Pion(this);
        Pion pionB1 = new Pion(this);


        Case cRoiB = grilleCases[4][7];
        Case cReineB = grilleCases[3][7];

        Case cFouB1 = grilleCases[2][7];
        Case cFouB2 = grilleCases[5][7];

        Case cCavalierB1 = grilleCases[6][7];
        Case cCavalierB2 = grilleCases[1][7];

        Case cTourB1 = grilleCases[0][7];
        Case cTourB2 = grilleCases[7][7];

        Case cPionB1 = grilleCases[0][6];
        Case cPionB2 = grilleCases[1][6];
        Case cPionB3 = grilleCases[2][6];
        Case cPionB4 = grilleCases[3][6];
        Case cPionB5 = grilleCases[4][6];
        Case cPionB6 = grilleCases[5][6];
        Case cPionB7 = grilleCases[6][6];
        Case cPionB8 = grilleCases[7][6];

        roiB.allerSurCase(cRoiB);
        reineB.allerSurCase(cReineB);

        fouB1.allerSurCase(cFouB1);
        fouB2.allerSurCase(cFouB2);

        cavalierB1.allerSurCase(cCavalierB1);
        cavalierB2.allerSurCase(cCavalierB2);

        tourB1.allerSurCase(cTourB1);
        tourB2.allerSurCase(cTourB2);

        pionB1.allerSurCase(cPionB1);
        pionB2.allerSurCase(cPionB2);
        pionB3.allerSurCase(cPionB3);
        pionB4.allerSurCase(cPionB4);
        pionB5.allerSurCase(cPionB5);
        pionB6.allerSurCase(cPionB6);
        pionB7.allerSurCase(cPionB7);
        pionB8.allerSurCase(cPionB8);

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
