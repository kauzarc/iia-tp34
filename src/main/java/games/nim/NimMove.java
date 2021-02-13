package games.nim;

import iialib.games.model.IMove;

public class NimMove implements IMove {

    public final int nb;

    public NimMove(int nb) {
        this.nb = nb;
    }

    @Override
    public String toString() {
        return "Le joueur enlève " + this.nb + " allumette(s)";
    }
}