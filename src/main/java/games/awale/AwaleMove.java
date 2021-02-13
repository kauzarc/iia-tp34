package games.awale;

import iialib.games.model.IMove;

public class AwaleMove implements IMove {
    public final int number_of_seeds;
    public final int numero_box;

    public AwaleMove(int number_of_seeds, int numero_box) {
        this.number_of_seeds = number_of_seeds;
        this.numero_box = numero_box;
    }

    @Override
    public String toString() {
        return "Déplacement de " + number_of_seeds + "graines à partir de la case " + numero_box;
    }
}
