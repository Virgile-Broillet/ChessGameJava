package VueControleur;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


import modele.jeu.*;
import modele.plateau.*;


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
    private static final int pxCase = 75; // nombre de pixel par case
    private boolean estTourBlanc = true;
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

        icoRoiBlanc = chargerIcone("images/wK.png");
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
        setSize(sizeX * pxCase, sizeY * pxCase);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // Grille contenant les cases graphiques

        tabJLabel = new JLabel[sizeX][sizeY]; // Initialisation du tableau des JLabel

        // Boucle pour initialiser toutes les cases du tableau
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // On conserve la référence de chaque JLabel

                final int xx = x; // Permet de capturer les variables x et y dans la classe anonyme
                final int yy = y; // Permet de capturer les variables x et y dans la classe anonyme

                // Ajout d'un écouteur de clics sur chaque case
                jlab.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (caseClic1 == null) {
                            caseClic1 = plateau.getCases()[xx][yy]; // Première case cliquée

                            if (caseClic1.getPiece() != null) {
                                Piece piece = caseClic1.getPiece();

                                // Vérifie si c'est le bon joueur qui joue
                                if ((estTourBlanc && piece.estBlanc) || (!estTourBlanc && !piece.estBlanc)) {
                                    ArrayList<Case> casesPossibles = calculerCasesPossibles(caseClic1);
                                    surlignerCasesPossibles(casesPossibles);
                                } else {
                                    // Mauvais tour, on annule la sélection
                                    caseClic1 = null;
                                }
                            }
                        } else {
                            caseClic2 = plateau.getCases()[xx][yy]; // Deuxième case cliquée
                            jeu.envoyerCoup(new Coup(caseClic1, caseClic2)); // Effectuer le coup

                            estTourBlanc = !estTourBlanc; // Changement de tour

                            // Réinitialisation
                            resetCouleursCases();
                            caseClic1 = null;
                            caseClic2 = null;
                        }
                    }
                });

                jlab.setOpaque(true);

                // Initialisation des couleurs de fond des cases (alternance claire et sombre)
                if ((y % 2 == 0 && x % 2 == 0) || (y % 2 != 0 && x % 2 != 0)) {
                    tabJLabel[x][y].setBackground(new Color(112, 102, 119)); // Case sombre
                } else {
                    tabJLabel[x][y].setBackground(new Color(204, 183, 174)); // Case claire
                }

                // Ajouter chaque case à la grille
                grilleJLabels.add(jlab);
            }
        }

        // Ajouter la grille au JPanel
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
                        if (e.estBlanc) {
                            if (c.getPiece() instanceof Roi) {
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
                            }
                        }  else {
                            if (c.getPiece() instanceof Roi) {
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
                        }
                    } else {
                        tabJLabel[x][y].setIcon(null);
                    }
                }

            }
        }
    }

    // Fonction qui surligne les cases accessibles en changeant leur couleur
    private void surlignerCasesPossibles(ArrayList<Case> casesPossibles) {
        // D'abord, réinitialiser la couleur des cases (retourner au damier initial)
        resetCouleursCases();

        // Ensuite, surligner les cases accessibles avec une couleur spécifique
        for (Case c : casesPossibles) {
            int x = c.getX();
            int y = c.getY();
            tabJLabel[x][y].setBackground(new Color(189, 144, 92)); // Change la couleur de fond pour surligner
        }
    }

    // Fonction pour réinitialiser les couleurs des cases du plateau (damier classique)
    private void resetCouleursCases() {
        // Parcourir chaque case du plateau
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                // Alterner les couleurs des cases pour créer le damier (clair et sombre)
                if ((x + y) % 2 == 0) {
                    tabJLabel[x][y].setBackground(new Color(112, 102, 119)); // Case sombre
                } else {
                    tabJLabel[x][y].setBackground(new Color(204, 183, 174)); // Case claire
                }
            }
        }
    }

    // Exemple de calcul des cases accessibles pour chaque pièce
    private ArrayList<Case> calculerCasesPossibles(Case caseClic1) {
        ArrayList<Case> casesPossibles = new ArrayList<>();
        Piece piece = caseClic1.getPiece();

        int x = caseClic1.getX();
        int y = caseClic1.getY();

        // Si la pièce est un Roi
        if (piece instanceof Roi) {
            casesPossibles = ((Roi) piece).getDeplacementsPossibles();
        }

        // Si la pièce est une Reine
        else if (piece instanceof Reine) {
            casesPossibles = (ArrayList<Case>) ((Reine) piece).getDeplacementsPossibles();
        }

        // Si la pièce est un Cavalier
        else if (piece instanceof Cavalier) {
            casesPossibles = ((Cavalier) piece).getDeplacementsPossibles();
        }

        // Si la pièce est un Fou
        else if (piece instanceof Fou) {
            casesPossibles = (ArrayList<Case>) ((Fou) piece).getDeplacementsPossibles();
        }

        // Si la pièce est une Tour
        else if (piece instanceof Tour) {
            casesPossibles = ((Tour) piece).getDeplacementsPossibles();
        }

        if (piece instanceof Pion) {
            casesPossibles = ((Pion) piece).getDeplacementsPossibles();
        }

        return casesPossibles;
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