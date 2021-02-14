package games.awale;

import java.util.ArrayList;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class AwaleBoard implements IBoard<AwaleMove, AwaleRole, AwaleBoard> {

    /*---------------------------------------------------------------------*/
    /*                              CONSTANTS                              */
    /*---------------------------------------------------------------------*/

    private final int[] board;
    private int nbOfSeedsWinPlayer1;
    private int nbOfSeedsWinPlayer2;

    /*---------------------------------------------------------------------*/
    /*                            CONSTRUCTORS                             */
    /*---------------------------------------------------------------------*/

    public AwaleBoard() {
        this.board = new int[12];
        for  (int i = 0; i < 12; ++i) {
            this.board[i] = 4;
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
    /*                            PUBLICS METHODS                          */
    /*---------------------------------------------------------------------*/

    @Override
    public AwaleBoard play(AwaleMove move, AwaleRole playerRole) {
        int[] newBoard = this.copyBoard();
        int numBoxInit = move.numeroBox;
        int nbOfSeeds = newBoard[numBoxInit];
        AwaleRole opponent = this.getOpponentRole(playerRole);
        int numBoxCurrent = numBoxInit;
        int newNbOfSeedsWinPlayer1 = this.nbOfSeedsWinPlayer1;
        int newNbOfSeedsWinPlayer2 = this.nbOfSeedsWinPlayer2;

        newBoard[numBoxInit] = 0;
        while (nbOfSeeds > 0) {
            numBoxCurrent = (numBoxCurrent + 1) % 12;
            if (numBoxCurrent != numBoxInit) {
                ++newBoard[numBoxCurrent];
                --nbOfSeeds;
            }
        }

        if (!this.wouldBecomeStarving(opponent, numBoxCurrent)) {
            while (isInTheOpponent(playerRole, numBoxCurrent) && (newBoard[numBoxCurrent] == 2 || newBoard[numBoxCurrent] == 3)) {
                if (playerRole == AwaleRole.PLAYER1) {
                    newNbOfSeedsWinPlayer1 += newBoard[numBoxCurrent];
                } else {
                    newNbOfSeedsWinPlayer2 += newBoard[numBoxCurrent];
                }
                newBoard[numBoxCurrent] = 0;
                numBoxCurrent = (numBoxCurrent - 1) % 12;
            }
        }
        return new AwaleBoard(newBoard, newNbOfSeedsWinPlayer1, newNbOfSeedsWinPlayer2);
    }

    @Override
    public ArrayList<AwaleMove> possibleMoves(AwaleRole playerRole) {
        ArrayList<AwaleMove> movesList = new ArrayList<>();
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : 6;
        boolean opponentIsStarving = this.isStarving(this.getOpponentRole(playerRole));

        for (int i = playerSide; i < (playerSide + 6); ++i) {
            if (this.board[i] != 0) {
                if (!opponentIsStarving || this.wouldFeedTheOpponent(playerRole, i)) {
                    movesList.add(new AwaleMove(i));
                }
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
            return this.board[move.numeroBox] != 0 && move.numeroBox < 6;
        }
        return this.board[move.numeroBox] != 0 && move.numeroBox > 5;
    }

    @Override
    public boolean isGameOver() {
        return this.nbOfSeedsWinPlayer1 >= 25
            || this.nbOfSeedsWinPlayer2 >= 25
            || 48 - this.nbOfSeedsWinPlayer1 - this.nbOfSeedsWinPlayer2 <= 6;
    }

    @Override
    public ArrayList<Score<AwaleRole>> getScores() {
        ArrayList<Score<AwaleRole>> scores = new ArrayList<Score<AwaleRole>>();
		if  (this.isGameOver()) {
            if  (this.nbOfSeedsWinPlayer1 > this.nbOfSeedsWinPlayer2) {
				scores.add(new Score<AwaleRole>(AwaleRole.PLAYER1, Score.Status.WIN, this.nbOfSeedsWinPlayer1));
				scores.add(new Score<AwaleRole>(AwaleRole.PLAYER2, Score.Status.LOOSE, this.nbOfSeedsWinPlayer2));
			} else if  (this.nbOfSeedsWinPlayer2 > this.nbOfSeedsWinPlayer1) {
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

		for  (int i = 11; i > 5; --i) {
            boardToString.append(this.board[i] + " ");
        }
        boardToString.append("\n");
		for  (int i = 0; i < 6; ++i) {
            boardToString.append(this.board[i] + " ");
        }
        boardToString.append("\n");
		return boardToString.toString();
	}

    /*---------------------------------------------------------------------*/
    /*                          PRIVATE METHODS                            */
    /*---------------------------------------------------------------------*/

    private int[] copyBoard() {
        int[] newBoard = new int[12];
		System.arraycopy(this.board, 0, newBoard, 0, 12);
        return newBoard;
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

    private boolean wouldBecomeStarving(AwaleRole playerRole, int numOfBox) {
        int playerSide = playerRole == AwaleRole.PLAYER1 ? 0 : 6;

        for (int i = playerSide; i < (playerSide + 6); ++i) {
            if ((i <= numOfBox && this.board[i] != 2 && this.board[i] != 3) || (i > numOfBox && this.board[i] != 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean wouldFeedTheOpponent(AwaleRole playerRole, int numOfBox) {
        int nbSeeds = this.board[numOfBox];
        int boxOfTheLastSeed = (numOfBox + nbSeeds) % 12;

        return nbSeeds > 6 || this.isInTheOpponent(playerRole, boxOfTheLastSeed);
    }

    private boolean isInTheOpponent(AwaleRole playerRole, int numOfBox) {
        if (playerRole == AwaleRole.PLAYER1) {
            return numOfBox > 5;
        }
        return numOfBox < 6 && numOfBox >= 0;
    }

    private void recoverAllTheSeeds(AwaleRole playerRole) {
        for (int i = 0; i < 12; ++i) {
            if (playerRole == AwaleRole.PLAYER1) {
                this.nbOfSeedsWinPlayer1 += this.board[i];
            } else {
                this.nbOfSeedsWinPlayer2 += this.board[i];
            }
            this.board[i] = 0;
        }
    }
}
