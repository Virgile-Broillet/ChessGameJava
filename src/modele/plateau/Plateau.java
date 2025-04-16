/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.jeu.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Case getCase(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return grilleCases[x][y];  // Retourne la case correspondante
        }
        return null;  // Si les coordonnées sont hors du plateau
    }

    private void initPlateauVide() {

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleCases[x][y] = new Case(x, y);
                map.put(grilleCases[x][y], new Point(x, y));
            }

        }

    }

    public Roi trouverRoi(boolean estBlanc) {
        // Parcourir toutes les cases du plateau
        for (int x = 0; x < Plateau.SIZE_X; x++) {
            for (int y = 0; y < Plateau.SIZE_Y; y++) {
                Case c = grilleCases[x][y];  // Récupérer chaque case
                Piece piece = c.getPiece();   // Récupérer la pièce sur cette case

                // Vérifier si la pièce est un roi et si sa couleur correspond à celle recherchée
                if (piece instanceof Roi && piece.estBlanc == estBlanc) {
                    return (Roi) piece;  // Retourner le roi trouvé
                }
            }
        }
        return null;  // Retourner null si le roi n'a pas été trouvé (ce qui ne devrait jamais arriver)
    }


    public boolean estEnEchec(boolean estBlanc) {
        // Trouver le roi du joueur
        Roi roi = trouverRoi(estBlanc);
        if (roi == null) return false;  // Si le roi n'existe pas (ce qui est impossible en règle générale)

        // Vérifier si une pièce adverse menace le roi
        for (int x = 0; x < Plateau.SIZE_X; x++) {
            for (int y = 0; y < Plateau.SIZE_Y; y++) {
                Case c = grilleCases[x][y];
                Piece piece = c.getPiece();

                // Si la case contient une pièce ennemie, vérifier si cette pièce peut attaquer le roi
                if (piece != null && piece.estBlanc != estBlanc) {
                    ArrayList<Case> coupsValides = (ArrayList<Case>) piece.getDeplacementsPossibles();
                    if (coupsValides.contains(roi.getCase())) {
                        return true;  // Si le roi est une cible pour cette pièce, il est en échec
                    }
                }
            }
        }

        return false;  // Le roi n'est pas en échec
    }

    public void placerPieces() {
        Roi roiB = new Roi(this, true);
        Reine reineB = new Reine(this, true);

        Cavalier cavalierB1 = new Cavalier(this, true);
        Cavalier cavalierB2 = new Cavalier(this, true);

        Fou fouB1 = new Fou(this, true);
        Fou fouB2 = new Fou(this, true);

        Tour tourB1 = new Tour(this, true);
        Tour tourB2 = new Tour(this, true);

        Pion pionB8 = new Pion(this, true);
        Pion pionB7 = new Pion(this, true);
        Pion pionB6 = new Pion(this, true);
        Pion pionB5 = new Pion(this, true);
        Pion pionB4 = new Pion(this, true);
        Pion pionB3 = new Pion(this, true);
        Pion pionB2 = new Pion(this, true);
        Pion pionB1 = new Pion(this, true);

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

        Roi roiN = new Roi(this, false);
        Reine reineN = new Reine(this, false);

        Cavalier cavalierN1 = new Cavalier(this, false);
        Cavalier cavalierN2 = new Cavalier(this, false);

        Fou fouN1 = new Fou(this, false);
        Fou fouN2 = new Fou(this, false);

        Tour tourN1 = new Tour(this, false);
        Tour tourN2 = new Tour(this, false);

        Pion pionN8 = new Pion(this, false);
        Pion pionN7 = new Pion(this, false);
        Pion pionN6 = new Pion(this, false);
        Pion pionN5 = new Pion(this, false);
        Pion pionN4 = new Pion(this, false);
        Pion pionN3 = new Pion(this, false);
        Pion pionN2 = new Pion(this, false);
        Pion pionN1 = new Pion(this, false);

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

        c.setPiece(p);

    }

    // Dans Plateau.java
    public void deplacerPiece(Case depart, Case arrivee) {
        if (depart == null || arrivee == null) {
            System.out.println("Case invalide");
            return;
        }

        Piece piece = depart.getPiece();
        if (piece == null) {
            System.out.println("Aucune pièce à déplacer");
            return;
        }

        // 1. Vérifier si le déplacement est valide
        ArrayList<Case> coupsValides = (ArrayList<Case>) piece.getDeplacementsPossibles();

        System.out.println("Coups valides : " + coupsValides);
        System.out.println("Case d'arrivée : " + arrivee);


        if (!coupsValides.contains(arrivee)) {
            System.out.println("Déplacement interdit pour cette pièce");
            return;
        }

        // 2. Vérification des autres conditions du déplacement (ex : capture)
        if (!arrivee.estLibre()) {
            Piece pieceAdverse = arrivee.getPiece();
            if (pieceAdverse.estBlanc == piece.estBlanc) {
                System.out.println("Tu ne peux pas capturer tes propres pièces");
                return;
            }
            // Retirer la pièce adverse
            pieceAdverse.quitterLaCase();
        }

        if (piece instanceof Pion) {
            Pion pion = (Pion) piece;
            pion.premierDeplacement = false; // Définir premierDeplacement à false après le premier coup
        }

        // 3. Exécution du déplacement
        depart.quitterLaCase();
        piece.allerSurCase(arrivee);

        // 4. Vérifier l'échec (à implémenter)
        if (estEnEchec(piece.estBlanc)) {
            System.out.println("Déplacement impossible : le roi serait en échec");
            depart.setPiece(piece);
            arrivee.quitterLaCase();
        }

        setChanged();
        notifyObservers();

    }


    public boolean estCaseLibre(int x, int y) {
        Case caseCible = grilleCases[x][y];
        return caseCible.estLibre();
    }

    public boolean estCaseOccupeeParAdversaire(int x, int y, Piece piece) {
        // Vérifie si la case est dans les limites du plateau
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            Case caseCible = grilleCases[x][y];
            Piece pieceCible = caseCible.getPiece();

            // Si la case est occupée par une pièce, on vérifie si c'est une pièce adverse
            if (pieceCible != null) {
                return pieceCible.estBlanc != piece.estBlanc;  // Compare les couleurs des pièces
            }
        }
        return false;  // Retourne false si la case est vide ou hors du plateau
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

    public ArrayList<Piece> getPiecesAdverses(Piece piece) {
        ArrayList<Piece> piecesAdverses = new ArrayList<>();

        boolean estBlanc = piece.estBlanc; // Récupère la couleur de la pièce

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                Case c = grilleCases[x][y]; // Récupère la case
                if (c.getPiece() != null && c.getPiece().estBlanc != estBlanc) { // Vérifie si la pièce est adverse
                    piecesAdverses.add(c.getPiece());
                }
            }
        }
        return piecesAdverses;
    }

    public boolean estCaseMenacee(Case caseCible, boolean parBlanc) {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                Case c = grilleCases[x][y];
                Piece p = c.getPiece();
                if (p != null && p.estBlanc != parBlanc) {
                    if (p instanceof Roi) continue;

                    ArrayList<Case> attaques = (ArrayList<Case>) p.getDeplacementsPossibles();
                    if (attaques.contains(caseCible)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }





    public boolean estDansLesLimites(int x, int y) {
        return x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y;
    }

}
