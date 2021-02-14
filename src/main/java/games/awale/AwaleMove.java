package games.awale;

import iialib.games.model.IMove;

public class AwaleMove implements IMove {
    public final int numeroBox;

    public AwaleMove(int numeroBox) {
        this.numeroBox = numeroBox;
    }

    @Override
    public String toString() {
        return "Déplacement des graines de la case " + numeroBox;
    }
}
