package games.awale;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {

    /*---------------------------------------------------------------------*/
    /* CONSTANTS */
    /*---------------------------------------------------------------------*/

    private final int[] board;
    private int numberOfSeedsWinPlayer1;
    private int numberOfSeedsWinPlayer2;

    /*---------------------------------------------------------------------*/
    /* CONSTRUCTORS */
    /*---------------------------------------------------------------------*/

    public AwaleBoard() {
        this.board = new int[12];
        for (int i = 0; i < 12; ++i) {
            this.board[i] = 4;
        }
        this.numberOfSeedsWinPlayer1 = 0;
        this.numberOfSeedsWinPlayer2 = 0;
    }

    public AwaleBoard(int[] board, int nbP1, int nbP2) {
        this.board = board;
        this.numberOfSeedsWinPlayer1 = nbP1;
        this.numberOfSeedsWinPlayer2 = nbP2;
    }

    /*---------------------------------------------------------------------*/
    /* PUBLICS METHODS */
    /*---------------------------------------------------------------------*/

    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        int[] newboard = copyBoard();
        int numeroBox = move.numeroBox;
        int numberOfSeeds = newboard[numeroBox];
        newboard[numeroBox] = 0;
        int i = numeroBox;

        while (numberOfSeeds > 0) {
            ++i;
            if (i > 11) {
                i = 0;
            }
            if (i != numeroBox) {
                ++newboard[i];
                --numberOfSeeds;
            }
        }
        if (playerRole == AwaleRole.PLAYER1) {
            while (i > 5) {
                if (newboard[i] == 2 || newboard[i] == 3) {
                    int temp = newboard[i];
                    newboard[i] = 0;
                    if (isStarving(AwaleRole.PLAYER2)) {
                        newboard[i] = temp;
                    } else {
                        this.numberOfSeedsWinPlayer1 += newboard[i];
                    }
                } else {
                    break;
                }
                --i;
            }
        }
        if (playerRole == AwaleRole.PLAYER2) {
            while (i < 6 && i >= 0) {
                if (newboard[i] == 2 || newboard[i] == 3) {
                    int temp = newboard[i];
                    newboard[i] = 0;
                    if (isStarving(AwaleRole.PLAYER1)) {
                        newboard[i] = temp;
                    } else {
                        this.numberOfSeedsWinPlayer2 += newboard[i];
                    }
                } else {
                    break;
                }
                --i;
            }
        }
        return new AwaleBoard(newboard, this.numberOfSeedsWinPlayer1, this.numberOfSeedsWinPlayer2);
    }

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        ArrayList<AwaleMove> movesList = new ArrayList<>();
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : 6;
        int limit = playerRole == AwaleRole.PLAYER1 ? 5 : 11;
        boolean opponentIsStarving = this.isStarving(this.getOpponentRole(playerRole));

        for (int i = playerSide; i < (playerSide + 6); ++i) {
            if (this.board[i] != 0) {
                int boxOfTheLastSeed = ((i + this.board[i]) % 11) - 1;
                if (!opponentIsStarving || (opponentIsStarving && boxOfTheLastSeed > limit)) {
                    movesList.add(new AwaleMove(i));
                }
            }
        }
        return movesList;
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        if (playerRole == AwaleRole.PLAYER1) {
            return this.board[move.numeroBox] != 0 && move.numeroBox < 6;
        }
        return this.board[move.numeroBox] != 0 && move.numeroBox > 5;
    }

    @Override
    public boolean isGameOver() {
        return this.numberOfSeedsWinPlayer1 >= 25 || this.numberOfSeedsWinPlayer2 >= 25
                || 48 - this.numberOfSeedsWinPlayer1 - this.numberOfSeedsWinPlayer2 <= 6;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        ArrayList<Score<AwaleRole>> scores = new ArrayList<Score<AwaleRole>>();
        if (this.isGameOver()) {
            if (this.numberOfSeedsWinPlayer1 >= 25) {
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER1, Score.Status.LOOSE, 0));
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER2, Score.Status.WIN, 1));
            } else {
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER1, Score.Status.WIN, 1));
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER2, Score.Status.LOOSE, 0));
            }
        }
        return scores;
    }

    @Override
    public String toString() {
        StringBuilder boardToString = new StringBuilder(new String(""));

        for (int i = 11; i > 5; --i) {
            boardToString.append(this.board[i] + " ");
        }
        boardToString.append("\n");
        for (int i = 0; i < 6; ++i) {
            boardToString.append(this.board[i] + " ");
        }
        boardToString.append("\n");
        return boardToString.toString();
    }

    public int getNumberSeedsCaptured(AwaleRole role) {
        switch (role) {
            case PLAYER1:
                return this.numberOfSeedsWinPlayer1;
            case PLAYER2:
                return this.numberOfSeedsWinPlayer2;
            default:
                return -1;
        }
    }

    public int getNumberSeedsCapturable(AwaleRole role) {
        int sum = 0;
        int otherPlayerSide = role == AwaleRole.PLAYER1 ? 6 : 0;
        for (int i = 0; i < 6; ++i) {
            int nbSeed = this.board[otherPlayerSide + i];
            if (nbSeed == 1 || nbSeed == 2) {
                sum += nbSeed;
            }
        }
        return sum;
    }

    public int getNumberSeedsLeftOnBoard() {
        int sum = 0;
        for (int seed : this.board) {
            sum += seed;
        }
        return sum;
    }

    /*---------------------------------------------------------------------*/
    /* PRIVATE METHODS */
    /*---------------------------------------------------------------------*/

    private int[] copyBoard() {
        int[] newboard = new int[12];
        System.arraycopy(this.board, 0, newboard, 0, 12);
        return newboard;
    }

    private AwaleRole getOpponentRole(AwaleRole playerRole) {
        return playerRole == AwaleRole.PLAYER1 ? AwaleRole.PLAYER2 : AwaleRole.PLAYER1;
    }

    private boolean isStarving(AwaleRole playerRole) {
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : 6;

        for (int i = playerSide; i < (playerSide + 6); ++i) {
            if (this.board[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
