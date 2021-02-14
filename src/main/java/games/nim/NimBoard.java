package games.nim;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;

public class NimBoard implements IBoard<NimMove, NimRole, NimBoard> {

    static public int DEFAULT_BOARD_SIZE = 20;

    public int BOARD_SIZE = DEFAULT_BOARD_SIZE;

    private int board;
    private NimRole currentPLayer;

    public NimBoard() {
        this.board = this.BOARD_SIZE;
    }

    public NimBoard(int boardSize) {
        this.BOARD_SIZE = boardSize;
        this.board = boardSize;
    }

    public NimBoard(int boardSize, NimRole currentPlayer) {
        this.BOARD_SIZE = boardSize;
        this.board = boardSize;
        this.currentPLayer = currentPlayer;
    }

    public int getNbSticks() {
        return this.board;
    }

    public NimBoard play(NimMove move, NimRole player) {
        return new NimBoard(this.board - move.nb, player);
    }

    public ArrayList<NimMove> possibleMoves(NimRole player) {
        ArrayList<NimMove> movesList = new ArrayList<>();
        for (int i = 1; i <= Math.min(3, this.board); ++i) {
            movesList.add(new NimMove(i));
        }
        return movesList;
    }

    public boolean isValidMove(NimMove move, NimRole player) {
        return this.board - move.nb >= 0;
    }

    public boolean isGameOver() {
        return this.board == 0;
    }

    public ArrayList<Score<NimRole>> getScores() {
        ArrayList<Score<NimRole>> scores = new ArrayList<Score<NimRole>>();
        NimRole opponent = this.currentPLayer == NimRole.PLAYER1 ? NimRole.PLAYER2 : NimRole.PLAYER1;
        scores.add(new Score<NimRole>(opponent, Score.Status.WIN, 1));
        return scores;
    }

    @Override
    public String toString() {
        return String.valueOf(this.board);
    }
}