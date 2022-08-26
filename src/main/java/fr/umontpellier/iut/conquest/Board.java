package fr.umontpellier.iut.conquest;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modélise un plateau.
 */
public class Board {
    /**
     * Tableau des pions.
     */
    private Pawn[][] field;

    /**
     * Constructeur.
     *
     * @param size : la taille du plateau.
     */
    public Board(int size) {
        field = new Pawn[size][size];
    }

    /**
     * Constructeur par copie
     * @param boardToCopy : Le board que l'on souhaite copier
     */
    public Board(Board boardToCopy){
        field = boardToCopy.copyField();
    }

    /**
     * Constructeur pour Test.
     *
     * @param field : plateau prédéfini.
     */
    public Board(Pawn[][] field) {
        this.field = field;
    }

    /**
     * Les méthodes suivantes sont utilisées pour les tests automatiques. Il ne faut pas les utiliser.
     */
    public Pawn[][] getField() {
        return field;
    }

    /**
     * Retourne la taille du plateau.
     */
    public int getSize() {
        return field.length;
    }

    /**
     * Affiche le plateau.
     */
    public String toString() {
        int size = field.length;
        StringBuilder b = new StringBuilder();
        for (int r = -1; r < size; r++) {
            for (int c = -1; c < size; c++) {
                if (r == -1 && c == -1) {
                    b.append("_");
                } else if (r == -1) {
                    b.append("_").append(c);
                } else if (c == -1) {
                    b.append(r).append("|");
                } else if (field[r][c] == null) {
                    b.append("_ ");
                } else if (field[r][c].getPlayer().getColor() == 1) {
                    b.append("X ");
                } else {
                    b.append("O ");
                }
            }
            b.append("\n");
        }
        b.append("---");
        return b.toString();
    }

    /**
     * Initialise le plateau avec les pions de départ.
     * Rappel :
     * - player1 commence le jeu avec un pion en haut à gauche (0,0) et un pion en bas à droite.
     * - player2 commence le jeu avec un pion en haut à droite et un pion en bas à gauche.
     */
    public void initField(Player player1, Player player2) {
        field[0][0] = new Pawn(player1);
        field[field.length-1][field.length-1] = new Pawn(player1);
        field[0][field.length-1] = new Pawn(player2);
        field[field.length-1][0] = new Pawn(player2);
    }

    /**
     * Vérifie si un coup est valide.
     * Rappel :
     * - Les coordonnées du coup doivent être dans le plateau.
     * - Le pion bougé doit appartenir au joueur.
     * - La case d'arrivée doit être libre.
     * - La distance entre la case d'arrivée est au plus 2.
     */
    public boolean isValid(Move move, Player player) {
        //Coordonnées de départ ne sont pas négatives ou en dehors du plateau
        if ((move.getRow1() >= 0 && move.getColumn1() >= 0) &&
                (move.getRow1() <= this.field.length - 1 && move.getColumn1() <= this.field.length - 1))

            //Coordonnées d'arrivées ne sont pas négatives ou en dehors du plateau
            if ((move.getRow2() >= 0 && move.getColumn2() >= 0) &&
                    (move.getRow2() <= this.field.length - 1) && (move.getColumn2() <= this.field.length - 1))

                //Vérfie si la case d'arrivée est libre
                if (field[move.getRow2()][move.getColumn2()] == null)

                    //Différence entre la distance de départ et d'arrivée n'est pas plus de 2
                    if ( (Math.abs(move.getRow2() - move.getRow1()) <= 2)
                            && (Math.abs(move.getColumn2() - move.getColumn1()) <= 2) )

                        //Vérifie si le pion appartient au joueur et existe
                        if ( this.field[move.getRow1()][move.getColumn1()] != null && this.field[move.getRow1()][move.getColumn1()].getPlayer().equals(player))
                            //Toute les conditions sont valides, on retourne true
                            return true;

        //Au moins une condition n'est pas valide, on retourne false
        return false;
    }

    /**
     * Vérifie qu'une case est occupée
     * @param x Ordonné de la case à tester
     * @param y Abscisse de la case à tester
     * @return true si la case est occupée, false sinon ou si la case n'est pas dans le plateau
     */
    public boolean isCaseOccuped(int x, int y){
        if(0 <= x && x < this.field.length && 0 <= y && y < this.field.length )
            return this.field[x][y] != null;
        else
            return false;
    }

    /**
     * Déplace un pion.
     *
     * @param move : un coup valide.
     *             Rappel :
     *             - Si le pion se déplace à distance 1 alors il se duplique pour remplir la case d'arrivée et la case de départ.
     *             - Si le pion se déplace à distance 2 alors il ne se duplique pas : la case de départ est maintenant vide et la case d'arrivée remplie.
     *             - Dans tous les cas, une fois que le pion est déplacé, tous les pions se trouvant dans les cases adjacentes à sa case d'arrivée prennent sa couleur.
     */
    public void movePawn(Move move) {
        if (isCaseOccuped(move.getRow1(), move.getColumn1())) {
            Player player = field[move.getRow1()][move.getColumn1()].getPlayer();

            //Si le pion se déplace à distance 1 alors il se duplique pour remplir la case d'arrivée et la case de départ.
            if (Math.abs(move.getColumn2() - move.getColumn1()) <= 1 && Math.abs(move.getRow2() - move.getRow1()) <= 1)
                field[move.getRow2()][move.getColumn2()] = new Pawn(player);

                //Si le pion se déplace à distance 2 alors il ne se duplique pas : la case de départ est maintenant vide et la case d'arrivée remplie.
            else if (Math.abs(move.getColumn2() - move.getColumn1()) == 2 || Math.abs(move.getRow2() - move.getRow1()) == 2) {
                field[move.getRow1()][move.getColumn1()] = null;
                field[move.getRow2()][move.getColumn2()] = new Pawn(player);
            }

            int x = move.getRow2();
            int y = move.getColumn2();

            //Tous les pions se trouvant dans les cases adjacentes à sa case d'arrivée prennent sa couleur.
            for (int i = x - 1; i <= x + 1; i++)
                for (int j = y - 1; j <= y + 1; j++)
                    if (isCaseOccuped(i, j))
                        field[i][j].setPlayer(player);

        }
    }


    /**
     * Retourne la liste de tous les coups valides de player.
     * S'il n'y a de coup valide, retourne une liste vide.
     */
    public List<Move> getValidMoves(Player player) {
        Move move;
        List<Move> result = new ArrayList<>();

        for (int i = 0; i < this.field.length; i++)
            for (int j = 0; j < this.field.length; j++)
                if (isCaseOccuped(i, j) && this.field[i][j].getPlayer().equals(player))
                    for (int k = -2; k <= 2; k++)
                        for (int l = -2; l <= 2; l++) {
                            move = new Move(i, j, i+k, j+l);
                            if (this.isValid(move, player))
                                result.add(move);
                        }
        return result;
    }

    /**
     * Retourne le nombre de pions d'un joueur.
     */
    public int getNbPawns(Player player) {
        int result = 0;
        for(int i = 0; i < this.field.length; i++)
            for(int j = 0; j < this.field.length; j++)
                if(isCaseOccuped(i,j) && this.field[i][j].getPlayer().equals(player))
                    result++;
        return result;
    }

    /**
     * Vérifie si le plateau est rempli
     * @return true si le plateau est rempli, false sinon
     */
    public boolean isFull(){
        for (int i = 0; i < this.field.length; i++)
            for (int j = 0; j < this.field.length; j++)
                if (!isCaseOccuped(i,j))
                    return false;
    return true;
    }

    /**
     * Copie le plateau du board courant
     * @return une matrice de pions
     */
    public Pawn[][] copyField(){
        Pawn[][] fieldCopy = new Pawn[this.field.length][this.field.length];
        for(int i = 0; i < this.field.length; i++)
            for(int j = 0; j < this.field.length; j++)
                if (isCaseOccuped(i,j))
                    fieldCopy[i][j] = new Pawn(field[i][j].getPlayer());
        return fieldCopy;
    }

    /**
     * Met toutes les cases du plateau à null
     */
    public void clear(){
        for(int i = 0; i < this.field.length; i++)
            for(int j = 0; j < this.field.length; j++)
                field[i][j] = null;
    }

}
