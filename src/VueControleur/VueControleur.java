package VueControleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


import modele.jeu.*;
import modele.plateau.*;

import java.awt.event.ActionListener;
import javax.swing.Timer;


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


    private JLabel timerBlancLabel;
    private JLabel timerNoirLabel;
    private int tempsBlanc = 0;
    private int tempsNoir = 0;
    private Timer timer;
    private boolean tourBlanc = true;
    private JLabel timerLabel;

    private JPanel piecesBlancCapturées;
    private JPanel piecesNoirCapturées;

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

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estTourBlanc) {
                    tempsBlanc++;
                    timerBlancLabel.setText("Blanc: " + tempsBlanc + "s");
                } else {
                    tempsNoir++;
                    timerNoirLabel.setText("Noir: " + tempsNoir + "s");
                }
            }
        });
        timer.start();
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Grille centrale
        JPanel grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX));
        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                jlab.setOpaque(true);
                jlab.setHorizontalAlignment(JLabel.CENTER);
                jlab.setVerticalAlignment(JLabel.CENTER);
                jlab.setPreferredSize(new Dimension(pxCase, pxCase));

                final int xx = x;
                final int yy = y;

                jlab.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Case caseCliquee = plateau.getCases()[xx][yy];

                        // Premier clic : sélection d'une pièce
                        if (caseClic1 == null) {
                            if (caseCliquee.getPiece() != null) {
                                Piece piece = caseCliquee.getPiece();

                                // Vérifie si c’est le bon joueur
                                if ((estTourBlanc && piece.estBlanc) || (!estTourBlanc && !piece.estBlanc)) {
                                    caseClic1 = caseCliquee;
                                    ArrayList<Case> casesPossibles = calculerCasesPossibles(caseClic1);
                                    surlignerCasesPossibles(casesPossibles);
                                }
                            }

                        } else {
                            // Si on reclique sur la même case => on annule la sélection
                            if (caseClic1.equals(caseCliquee)) {
                                resetCouleursCases();
                                caseClic1 = null;
                                return;
                            }

                            // Sinon, c’est un vrai coup
                            caseClic2 = caseCliquee;
                            jeu.envoyerCoup(new Coup(caseClic1, caseClic2));

                            // Changer de tour uniquement si c'est un coup valide
                            if (caseClic1.getPiece() != null && !caseClic1.equals(caseClic2)) {
                                estTourBlanc = !estTourBlanc;  // Changer de tour
                            }

                            // Réinitialisation
                            resetCouleursCases();
                            caseClic1 = null;
                            caseClic2 = null;
                        }
                    }

                });

                if ((x + y) % 2 == 0) {
                    jlab.setBackground(new Color(112, 102, 119));
                } else {
                    jlab.setBackground(new Color(204, 183, 174));
                }

                tabJLabel[x][y] = jlab;
                grilleJLabels.add(jlab);
            }
        }

        // Coordonnées autour de la grille
        JPanel coordGauche = new JPanel(new GridLayout(sizeY, 1));
        for (int y = sizeY - 1; y >= 0; y--) {
            coordGauche.add(new JLabel("" + (y + 1), JLabel.CENTER));
        }

        JPanel coordBas = new JPanel(new GridLayout(1, sizeX));
        for (int x = 0; x < sizeX; x++) {
            coordBas.add(new JLabel("" + (char) ('A' + x), JLabel.CENTER));
        }

        JPanel grilleAvecCoord = new JPanel(new BorderLayout());
        grilleAvecCoord.add(coordGauche, BorderLayout.WEST);
        grilleAvecCoord.add(coordBas, BorderLayout.SOUTH);
        grilleAvecCoord.add(grilleJLabels, BorderLayout.CENTER);

        // Timer principal en haut
        timerLabel = new JLabel("Temps: 0s", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panneau central (timer + grille)
        JPanel panneauCentral = new JPanel(new BorderLayout());
        panneauCentral.add(timerLabel, BorderLayout.NORTH);
        panneauCentral.add(grilleAvecCoord, BorderLayout.CENTER);

        // Panneau joueur blanc
        JPanel panneauBlanc = new JPanel();
        panneauBlanc.setLayout(new BoxLayout(panneauBlanc, BoxLayout.Y_AXIS));
        panneauBlanc.setPreferredSize(new Dimension(120, pxCase * sizeY));

        timerBlancLabel = new JLabel("Blanc: 0s");
        timerBlancLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerBlancLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panneauBlanc.add(timerBlancLabel);
        panneauBlanc.add(Box.createVerticalStrut(10));
        panneauBlanc.add(new JLabel("Capturées :"));
        piecesBlancCapturées = new JPanel(new FlowLayout());
        panneauBlanc.add(piecesBlancCapturées);

        // Panneau joueur noir
        JPanel panneauNoir = new JPanel();
        panneauNoir.setLayout(new BoxLayout(panneauNoir, BoxLayout.Y_AXIS));
        panneauNoir.setPreferredSize(new Dimension(120, pxCase * sizeY));

        timerNoirLabel = new JLabel("Noir: 0s");
        timerNoirLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerNoirLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panneauNoir.add(timerNoirLabel);
        panneauNoir.add(Box.createVerticalStrut(10));
        panneauNoir.add(new JLabel("Capturées :"));
        piecesNoirCapturées = new JPanel(new FlowLayout());
        panneauNoir.add(piecesNoirCapturées);

        // Organisation de la fenêtre principale
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panneauBlanc, BorderLayout.WEST);
        getContentPane().add(panneauNoir, BorderLayout.EAST);
        getContentPane().add(panneauCentral, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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

        // Vérifie si le roi est en échec après chaque mise à jour
        boolean roiBlancEnEchec = plateau.estEnEchec(true);
        boolean roiNoirEnEchec = plateau.estEnEchec(false);

        if (roiBlancEnEchec) {
            JOptionPane.showMessageDialog(this, "⚠ Le roi BLANC est en échec !", "Échec", JOptionPane.WARNING_MESSAGE);
            /*
            // Vérifier si c'est un mat
            if (estMat(true)) {
                JOptionPane.showMessageDialog(this, "Le roi BLANC est en MAT ! La partie est terminée.", "Mat", JOptionPane.INFORMATION_MESSAGE);

                // Arrêter le temps
                stopTimer();

                // Code pour terminer la partie (par exemple, désactiver les entrées utilisateur)
            }
            */
        }

        if (roiNoirEnEchec) {
            JOptionPane.showMessageDialog(this, "⚠ Le roi NOIR est en échec !", "Échec", JOptionPane.WARNING_MESSAGE);
            /*
            // Vérifier si c'est un mat
            if (estMat(false)) {
                JOptionPane.showMessageDialog(this, "Le roi NOIR est en MAT ! La partie est terminée.", "Mat", JOptionPane.INFORMATION_MESSAGE);

                // Arrêter le temps
                stopTimer();

                // Code pour terminer la partie (par exemple, désactiver les entrées utilisateur)
            }
             */
        }
    }

    /*
    private void stopTimer() {
        // Arrêter le timer (supposons que timer est un objet qui gère le temps)
        if (timer != null) {
            timer.stop();  // Vous pouvez avoir une méthode stop() dans votre classe Timer
        }
    }
    */

    /*
    private boolean estMat(boolean estBlanc) {
        // Trouver le roi du joueur
        Roi roi = plateau.trouverRoi(estBlanc);
        if (roi == null) return false;  // Si le roi n'existe pas (ce qui est impossible en règle générale)

        // Vérifier si le roi peut se déplacer sur une case non attaquée
        ArrayList<Case> deplacementsPossibles = roi.getDeplacementsPossibles();

        for (Case casePossible : deplacementsPossibles) {
            // Déplacer le roi temporairement
            Piece pieceDepart = casePossible.getPiece();
            casePossible.setPiece(roi);
            roi.getCase().setPiece(null);

            // Vérifier si le roi est toujours en échec après ce mouvement
            boolean roiEnEchec = plateau.estEnEchec(estBlanc);

            // Annuler le déplacement (restaurer l'état)
            casePossible.setPiece(pieceDepart);
            roi.getCase().setPiece(roi);

            // Si le roi n'est pas en échec après ce déplacement, ce n'est pas un mat
            if (!roiEnEchec) {
                return false;
            }
        }

        return true;  // Si aucune case n'est sûre, c'est un mat
    }
    */
}