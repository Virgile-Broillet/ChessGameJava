package VueControleur;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


import modele.jeu.*;
import modele.plateau.Case;
import modele.plateau.Plateau;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (clic position départ -> position arrivée pièce))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private final Plateau plateau; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)
    private final Jeu jeu;
    private final int sizeX; // taille de la grille affichée
    private final int sizeY;
    private static final int pxCase = 50; // nombre de pixel par case
    // icones affichées dans la grille
    private ImageIcon icoRoiBlanc;
    private ImageIcon icoReineBlanc;
    private ImageIcon icoCavalierBlanc;
    private ImageIcon icoFouBlanc;
    private ImageIcon icoPionBlanc;
    private ImageIcon icoTourBlanc;

    private ImageIcon icoRoiNoir;
    private ImageIcon icoReineNoir;
    private ImageIcon icoCavalierNoir;
    private ImageIcon icoFouNoir;
    private ImageIcon icoPionNoir;
    private ImageIcon icoTourNoir;


    private Case caseClic1; // mémorisation des cases cliquées
    private Case caseClic2;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        jeu = _jeu;
        plateau = jeu.getPlateau();
        sizeX = Plateau.SIZE_X;
        sizeY = Plateau.SIZE_Y;



        chargerLesIcones();
        placerLesComposantsGraphiques();

        plateau.addObserver(this);

        mettreAJourAffichage();

    }


    private void chargerLesIcones() {

        icoRoiBlanc = chargerIcone("Images/wK.png");
        icoReineBlanc = chargerIcone("images/wQ.png");
        icoCavalierBlanc = chargerIcone("images/wN.png");
        icoFouBlanc = chargerIcone("images/wB.png");
        icoPionBlanc = chargerIcone("images/wP.png");
        icoTourBlanc = chargerIcone("images/wR.png");

        icoRoiNoir = chargerIcone("Images/bK.png");
        icoReineNoir = chargerIcone("images/bQ.png");
        icoCavalierNoir = chargerIcone("images/bN.png");
        icoFouNoir = chargerIcone("images/bB.png");
        icoPionNoir = chargerIcone("images/bP.png");
        icoTourNoir = chargerIcone("images/bR.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        ImageIcon icon = new ImageIcon(urlIcone);

        // Redimensionner l'icône
        Image img = icon.getImage().getScaledInstance(pxCase, pxCase, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(img);

        return resizedIcon;
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Jeu d'Échecs");
        setResizable(false);
        setSize(sizeX * pxCase, sizeX * pxCase);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille


        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();

                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )

                final int xx = x; // permet de compiler la classe anonyme ci-dessous
                final int yy = y;
                // écouteur de clics
                jlab.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (caseClic1 == null) {
                            caseClic1 = plateau.getCases()[xx][yy];
                            /*if (plateau.getCases()[xx][yy].getPiece() == null) {
                                caseClic1 = null;
                            }*/
                        } else {
                            caseClic2 = plateau.getCases()[xx][yy];
                            jeu.envoyerCoup(new Coup(caseClic1, caseClic2));
                            caseClic1 = null;
                            caseClic2 = null;
                        }

                    }
                });


                jlab.setOpaque(true);

                if ((y%2 == 0 && x%2 == 0) || (y%2 != 0 && x%2 != 0)) {
                    tabJLabel[x][y].setBackground(new Color(50, 50, 110));
                } else {
                    tabJLabel[x][y].setBackground(new Color(150, 150, 210));
                }

                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }


    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {

                Case c = plateau.getCases()[x][y];

                if (c != null) {

                    Piece e = c.getPiece();

                    if (e!= null) {
                        if (c.getPiece() instanceof Roi && ) {
                            tabJLabel[x][y].setIcon(icoRoiBlanc);
                        } else if (c.getPiece() instanceof Reine) {
                            tabJLabel[x][y].setIcon(icoReineBlanc);
                        } else if (c.getPiece() instanceof Cavalier) {
                            tabJLabel[x][y].setIcon(icoCavalierBlanc);
                        } else if (c.getPiece() instanceof Fou) {
                            tabJLabel[x][y].setIcon(icoFouBlanc);
                        } else if (c.getPiece() instanceof Pion) {
                            tabJLabel[x][y].setIcon(icoPionBlanc);
                        } else if (c.getPiece() instanceof Tour) {
                            tabJLabel[x][y].setIcon(icoTourBlanc);
                        } else if (c.getPiece() instanceof Roi) {
                            tabJLabel[x][y].setIcon(icoRoiNoir);
                        } else if (c.getPiece() instanceof Reine) {
                            tabJLabel[x][y].setIcon(icoReineNoir);
                        } else if (c.getPiece() instanceof Cavalier) {
                            tabJLabel[x][y].setIcon(icoCavalierNoir);
                        } else if (c.getPiece() instanceof Fou) {
                            tabJLabel[x][y].setIcon(icoFouNoir);
                        } else if (c.getPiece() instanceof Pion) {
                            tabJLabel[x][y].setIcon(icoPionNoir);
                        } else if (c.getPiece() instanceof Tour) {
                            tabJLabel[x][y].setIcon(icoTourNoir);
                        }
                    } else {
                        tabJLabel[x][y].setIcon(null);
                    }
                }

            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*

        // récupérer le processus graphique pour rafraichir
        // (normalement, à l'inverse, a l'appel du modèle depuis le contrôleur, utiliser un autre processus, voir classe Executor)


        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }
}