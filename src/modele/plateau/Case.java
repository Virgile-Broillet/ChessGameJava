/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.jeu.Piece;

import java.awt.*;
import java.util.ArrayList;

public class Case {

    private int x, y;
    private Plateau plateau;
    private Piece piece;

    /***
     * @name Case()
     * @brief constructeur de la classe case
     * @param x
     * @param y
     */
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    /***
     * @name quitterLaCase()
     * @brief fonction qui indique que la piece a quitter la case
     */
    public void quitterLaCase() {
        piece = null;
    }

    /***
     * @name getX
     * @breif getter X
     * @return x
     */
    public int getX() {
        return x;
    }

    /***
     * @name getY
     * @breif getter Y
     * @return y
     */
    public int getY() {
        return y;
    }

    /***
     * @name setPiece
     * @brief copie les données d'une piece
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /***
     * @name getPiece
     * @brief retourne une piece
     * @return piece
     */
    public Piece getPiece() {
        return piece;
    }

    /***
     * @name estLibre()
     * @brief renvoie true si la case est libre false sinon
     * @return boolean
     */
    public boolean estLibre() {
        return piece == null;
    }

    /***
     * @name getCase()
     * @brief Getter de case
     * @return case
     */
    public Case getCase() { return this; }

    /***
     * @name coordVersString()
     * @brief converti les coordées x et y passé en paramètre en string
     * @param x
     * @param y
     * @return string
     */
    public static String coordVersString(int x, int y) {
        char colonne = (char) ('A' + x);
        int ligne = 8 - y;
        return "" + colonne + ligne;
    }

}
