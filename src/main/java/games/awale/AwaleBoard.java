package games.awale;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {

    public final static int SIDE_SIZE = 6;
    public final static int NB_SEED_BY_HOLE = 4;
    public final static int BOARD_SIZE = SIDE_SIZE * 2;
    public final static int TOTAL_SEED = BOARD_SIZE * NB_SEED_BY_HOLE;

    /*---------------------------------------------------------------------*/
    /* CONSTANTS */
    /*---------------------------------------------------------------------*/

    private final int[] board;

    /*---------------------------------------------------------------------*/
    /* ATTRIBUTES */
    /*---------------------------------------------------------------------*/

    private int nbOfSeedsWinPlayer1;
    private int nbOfSeedsWinPlayer2;

    /*---------------------------------------------------------------------*/
    /* CONSTRUCTORS */
    /*---------------------------------------------------------------------*/

    public AwaleBoard() {
        this.board = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; ++i) {
            this.board[i] = NB_SEED_BY_HOLE;
        }
        this.nbOfSeedsWinPlayer1 = 0;
        this.nbOfSeedsWinPlayer2 = 0;
    }

    public AwaleBoard(int[] board, int nbP1, int nbP2) {
        this.board = board;
        this.nbOfSeedsWinPlayer1 = nbP1;
        this.nbOfSeedsWinPlayer2 = nbP2;
    }

    /*---------------------------------------------------------------------*/
    /* PUBLICS METHODS */
    /*---------------------------------------------------------------------*/

    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        int[] newBoard = this.copyBoard();
        int numBoxInit = move.boxId;
        int nbOfSeeds = newBoard[numBoxInit];
        int numBoxCurrent = numBoxInit;
        int newNbOfSeedsWinPlayer1 = this.nbOfSeedsWinPlayer1;
        int newNbOfSeedsWinPlayer2 = this.nbOfSeedsWinPlayer2;
        AwaleRole opponent = playerRole.getOpponentRole();

        newBoard[numBoxInit] = 0;
        while (nbOfSeeds > 0) {
            numBoxCurrent = (numBoxCurrent + 1) % BOARD_SIZE;
            if (numBoxCurrent != numBoxInit) {
                ++newBoard[numBoxCurrent];
                --nbOfSeeds;
            }
        }

        if (!this.wouldBecomeStarving(opponent, numBoxCurrent)) {
            while (this.isInTheOpponent(playerRole, numBoxCurrent)
                    && (newBoard[numBoxCurrent] == 2 || newBoard[numBoxCurrent] == 3)) {
                if (playerRole == AwaleRole.PLAYER1) {
                    newNbOfSeedsWinPlayer1 += newBoard[numBoxCurrent];
                } else {
                    newNbOfSeedsWinPlayer2 += newBoard[numBoxCurrent];
                }
                newBoard[numBoxCurrent] = 0;
                numBoxCurrent = (numBoxCurrent - 1) % BOARD_SIZE;
            }
        }
        return new AwaleBoard(newBoard, newNbOfSeedsWinPlayer1, newNbOfSeedsWinPlayer2);
    }

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        ArrayList<AwaleMove> movesList = new ArrayList<>();
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : SIDE_SIZE;

        for (int i = 0; i < SIDE_SIZE; ++i) {
            int nbSeed = this.board[playerSide + i];
            if (nbSeed != 0 && !this.wouldBecomeStarvingAfterPlaying(playerRole, playerSide + i)) {
                movesList.add(new AwaleMove(i));
            }
        }
        if (movesList.isEmpty()) {
            this.recoverAllTheSeeds(playerRole);
        }
        return movesList;
    }

    @Override
    public boolean isValidMove(AwaleMove move, AwaleRole playerRole) {
        if (playerRole == AwaleRole.PLAYER1) {
            return this.board[move.boxId] != 0 && move.boxId >= 0 && move.boxId < SIDE_SIZE;
        }
        return this.board[move.boxId] != 0 && move.boxId >= SIDE_SIZE && move.boxId < BOARD_SIZE;
    }

    @Override
    public boolean isGameOver() {
        return this.nbOfSeedsWinPlayer1 >= 25 || this.nbOfSeedsWinPlayer2 >= 25
                || 48 - this.nbOfSeedsWinPlayer1 - this.nbOfSeedsWinPlayer2 <= 6
                || (this.possibleMoves(AwaleRole.PLAYER1).isEmpty() && this.possibleMoves(AwaleRole.PLAYER2).isEmpty());
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        ArrayList<Score<AwaleRole>> scores = new ArrayList<Score<AwaleRole>>();
        if (this.isGameOver()) {
            if (this.nbOfSeedsWinPlayer1 > this.nbOfSeedsWinPlayer2) {
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER1, Score.Status.WIN, this.nbOfSeedsWinPlayer1));
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER2, Score.Status.LOOSE, this.nbOfSeedsWinPlayer2));
            } else if (this.nbOfSeedsWinPlayer2 > this.nbOfSeedsWinPlayer1) {
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER1, Score.Status.LOOSE, this.nbOfSeedsWinPlayer1));
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER2, Score.Status.WIN, this.nbOfSeedsWinPlayer2));
            } else {
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER1, Score.Status.WIN, this.nbOfSeedsWinPlayer1));
                scores.add(new Score<AwaleRole>(AwaleRole.PLAYER2, Score.Status.WIN, this.nbOfSeedsWinPlayer2));
            }
        }
        return scores;
    }

    @Override
    public String toString() {
        StringBuilder boardToString = new StringBuilder(new String(""));

        for (int i = BOARD_SIZE - 1; i >= SIDE_SIZE; --i) {
            boardToString.append(this.board[i] + " ");
        }
        boardToString.append("\n");
        for (int i = 0; i < SIDE_SIZE; ++i) {
            boardToString.append(this.board[i] + " ");
        }
        boardToString.append("\n");
        return boardToString.toString();
    }

    public int getNumberSeedsCaptured(AwaleRole playerRole) {
        if (playerRole == AwaleRole.PLAYER1) {
            return this.nbOfSeedsWinPlayer1;
        }
        return this.nbOfSeedsWinPlayer2;
    }

    public int getNumberSeedsCapturable(AwaleRole playerRole) {
        int sum = 0;
        int otherPlayerSide = playerRole == AwaleRole.PLAYER1 ? SIDE_SIZE : 0;

        for (int i = 0; i < SIDE_SIZE; ++i) {
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
        int[] newBoard = new int[BOARD_SIZE];
        System.arraycopy(this.board, 0, newBoard, 0, BOARD_SIZE);
        return newBoard;
    }

    private boolean isStarving(AwaleRole playerRole) {
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : SIDE_SIZE;

        for (int i = 0; i < SIDE_SIZE; ++i) {
            int nbSeed = this.board[playerSide + i];
            if (nbSeed != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean wouldBecomeStarving(AwaleRole playerRole, int numOfBox) {
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : SIDE_SIZE;

        for (int i = 0; i < SIDE_SIZE; ++i) {
            int nbSeed = this.board[playerSide + i];
            if ((i <= numOfBox && nbSeed != 2 && nbSeed != 3) || (playerSide + i > numOfBox && nbSeed != 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean wouldBecomeStarvingAfterPlaying(AwaleRole playerRole, int numOfBox) {
        AwaleBoard tmpBoard = this.play(new AwaleMove(numOfBox), playerRole);

        return tmpBoard.isStarving(playerRole.getOpponentRole());
    }

    private boolean isInTheOpponent(AwaleRole playerRole, int numOfBox) {
        if (playerRole == AwaleRole.PLAYER1) {
            return numOfBox >= SIDE_SIZE;
        }
        return numOfBox < SIDE_SIZE && numOfBox >= 0;
    }

    private void recoverAllTheSeeds(AwaleRole playerRole) {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            if (playerRole == AwaleRole.PLAYER1) {
                this.nbOfSeedsWinPlayer1 += this.board[i];
            } else {
                this.nbOfSeedsWinPlayer2 += this.board[i];
            }
            this.board[i] = 0;
        }
    }
}
