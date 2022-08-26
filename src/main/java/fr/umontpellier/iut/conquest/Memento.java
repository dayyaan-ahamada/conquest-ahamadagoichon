package fr.umontpellier.iut.conquest;

public class Memento {

    public Board boardState;

    public Memento(Board boardState) {
        this.boardState = new Board(boardState);
    }

    public Board getSavedBoardState(){
        return boardState;
    }

}
