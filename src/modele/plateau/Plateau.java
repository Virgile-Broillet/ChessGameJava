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


    protected HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
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
        roiB.isWhite = true;
        Reine reineB = new Reine(this);
        reineB.isWhite = true;

        Cavalier cavalierB1 = new Cavalier(this);
        cavalierB1.isWhite = true;
        Cavalier cavalierB2 = new Cavalier(this);
        cavalierB2.isWhite = true;

        Fou fouB1 = new Fou(this);
        fouB1.isWhite = true;
        Fou fouB2 = new Fou(this);
        fouB2.isWhite = true;

        Tour tourB1 = new Tour(this);
        tourB1.isWhite = true;
        Tour tourB2 = new Tour(this);
        tourB2.isWhite = true;

        Pion pionB8 = new Pion(this);
        pionB8.isWhite = true;
        Pion pionB7 = new Pion(this);
        pionB7.isWhite = true;
        Pion pionB6 = new Pion(this);
        pionB6.isWhite = true;
        Pion pionB5 = new Pion(this);
        pionB5.isWhite = true;
        Pion pionB4 = new Pion(this);
        pionB4.isWhite = true;
        Pion pionB3 = new Pion(this);
        pionB3.isWhite = true;
        Pion pionB2 = new Pion(this);
        pionB2.isWhite = true;
        Pion pionB1 = new Pion(this);
        pionB1.isWhite = true;

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

        map.put(cRoiB, new Point(4, 7));
        map.put(cReineB, new Point(3, 7));
        map.put(cFouB1, new Point(2, 7));
        map.put(cFouB2, new Point(5, 7));
        map.put(cCavalierB1, new Point(6, 7));
        map.put(cCavalierB2, new Point(1, 7));
        map.put(cTourB1, new Point(0, 7));
        map.put(cTourB2, new Point(7, 7));

        map.put(cPionB1, new Point(0, 6));
        map.put(cPionB2, new Point(1, 6));
        map.put(cPionB3, new Point(2, 6));
        map.put(cPionB4, new Point(3, 6));
        map.put(cPionB5, new Point(4, 6));
        map.put(cPionB6, new Point(5, 6));
        map.put(cPionB7, new Point(6, 6));
        map.put(cPionB8, new Point(7, 6));

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

        Roi roiN = new Roi(this);
        roiN.isWhite = false;
        Reine reineN = new Reine(this);
        reineN.isWhite = false;

        Cavalier cavalierN1 = new Cavalier(this);
        cavalierN1.isWhite = false;
        Cavalier cavalierN2 = new Cavalier(this);
        cavalierN2.isWhite = false;

        Fou fouN1 = new Fou(this);
        fouN1.isWhite = false;
        Fou fouN2 = new Fou(this);
        fouN2.isWhite = false;

        Tour tourN1 = new Tour(this);
        tourN1.isWhite = false;
        Tour tourN2 = new Tour(this);
        tourN2.isWhite = false;

        Pion pionN8 = new Pion(this);
        pionN8.isWhite = false;
        Pion pionN7 = new Pion(this);
        pionN7.isWhite = false;
        Pion pionN6 = new Pion(this);
        pionN6.isWhite = false;
        Pion pionN5 = new Pion(this);
        pionN5.isWhite = false;
        Pion pionN4 = new Pion(this);
        pionN4.isWhite = false;
        Pion pionN3 = new Pion(this);
        pionN3.isWhite = false;
        Pion pionN2 = new Pion(this);
        pionN2.isWhite = false;
        Pion pionN1 = new Pion(this);
        pionN1.isWhite = false;

        Case cRoiN = grilleCases[4][0];
        Case cReineN = grilleCases[3][0];

        Case cFouN1 = grilleCases[2][0];
        Case cFouN2 = grilleCases[5][0];

        Case cCavalierN1 = grilleCases[6][0];
        Case cCavalierN2 = grilleCases[1][0];

        Case cTourN1 = grilleCases[0][0];
        Case cTourN2 = grilleCases[7][0];

        Case cPionN1 = grilleCases[0][1];
        Case cPionN2 = grilleCases[1][1];
        Case cPionN3 = grilleCases[2][1];
        Case cPionN4 = grilleCases[3][1];
        Case cPionN5 = grilleCases[4][1];
        Case cPionN6 = grilleCases[5][1];
        Case cPionN7 = grilleCases[6][1];
        Case cPionN8 = grilleCases[7][1];

        roiN.allerSurCase(cRoiN);
        reineN.allerSurCase(cReineN);

        fouN1.allerSurCase(cFouN1);
        fouN2.allerSurCase(cFouN2);

        cavalierN1.allerSurCase(cCavalierN1);
        cavalierN2.allerSurCase(cCavalierN2);

        tourN1.allerSurCase(cTourN1);
        tourN2.allerSurCase(cTourN2);

        pionN1.allerSurCase(cPionN1);
        pionN2.allerSurCase(cPionN2);
        pionN3.allerSurCase(cPionN3);
        pionN4.allerSurCase(cPionN4);
        pionN5.allerSurCase(cPionN5);
        pionN6.allerSurCase(cPionN6);
        pionN7.allerSurCase(cPionN7);
        pionN8.allerSurCase(cPionN8);

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
