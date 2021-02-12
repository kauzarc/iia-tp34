package games.awale;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;

public class AwaleGame extends AbstractGame<AwaleMove, AwaleRole, AwaleBoard> {

    public AwaleGame(ArrayList<AIPlayer<AwaleMove, AwaleRole, AwaleBoard>> players, AwaleBoard initialBoard) {
        super(players, initialBoard);
    }

    public static void main(String[] args) {
        // TODO
    }

}
