package fr.umontpellier.iut.conquest;

import java.util.ArrayDeque;
import java.util.Deque;

public class Caretaker {

    final private Deque<Memento> lifo_memento = new ArrayDeque<>();

    public void addMemento(Memento memento){
        lifo_memento.push(memento);
    }

    public Memento getMemento(){
        return lifo_memento.pop();
    }

    public boolean isEmpty(){
        return lifo_memento.isEmpty();
    }

}
