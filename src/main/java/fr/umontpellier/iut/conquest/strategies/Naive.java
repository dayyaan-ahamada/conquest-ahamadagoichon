package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

import java.util.List;

public class Naive implements Strategy {

    @Override
    public Move getMove(Board board, Player player) {
        List<Move> listMove = board.getValidMoves(player);
        int randomIndex = (int)(Math.random() * listMove.size());
        Move move = listMove.get(randomIndex);

        return move;
    }
}
