/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.jeu.Piece;

public class Case {

    private int x, y;
    private Plateau plateau;
    private Piece piece;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    public void quitterLaCase() {
        piece = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean estLibre() {
        return piece == null;
    }

    public static String coordVersString(int x, int y) {
        char colonne = (char) ('A' + x);
        int ligne = 8 - y;
        return "" + colonne + ligne;
    }

}
