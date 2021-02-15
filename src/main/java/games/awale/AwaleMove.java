package games.awale;

import iialib.games.model.IMove;

public class AwaleMove implements IMove {
    public final int boxId;

    public AwaleMove(int boxId) {
        this.boxId = boxId;
    }

    @Override
    public String toString() {
        return "Déplacement des graines de la case " + boxId;
    }
}
