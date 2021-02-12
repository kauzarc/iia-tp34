package games.awale;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        // TODO
        return null;
    }

    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        // TODO
        return null;
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        // TODO
        return false;
    }

    @Override
    public boolean isGameOver() {
        // TODO
        return false;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        // TODO
        return null;
    }

}
