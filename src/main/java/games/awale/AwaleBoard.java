package games.awale;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {

    private final int[] board;

    /*-------------------- CONSTRUCTEUR -----------------------*/
    public AwaleBoard() {
        this.board = new int[12];
        for (int i = 0; i < 12; i++) {
            this.board[i] = 4;
        }
    }

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        // TODO
        return null;
    }

    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        // faire une copie de board
        int numero_box = move.numero_box;
        int number_of_seeds = this.board[numero_box];
        this.board[move.numero_box] = 0;
        for(int i = 1; i <= number_of_seeds; ++i) {
            ++this.board[numero_box + i];
        }
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
