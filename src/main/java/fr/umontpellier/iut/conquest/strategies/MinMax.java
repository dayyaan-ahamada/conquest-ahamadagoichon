package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

public class MinMax implements Strategy {

    private int profondeur;

    public MinMax(int profondeur) {
        this.profondeur = profondeur;
    }

    @Override
    public Move getMove(Board board, Player player) {
        return null;
    }
}
