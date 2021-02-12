package games.otherGame;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;

public class OtherGame extends AbstractGame<OtherGameMove, OtherGameRole, OtherGameBoard> {

	OtherGame(ArrayList<AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>> players, OtherGameBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {

		OtherGameRole roleP1 = OtherGameRole.PLAYER_ONE;
		OtherGameRole roleP2 = OtherGameRole.PLAYER_TWO;

		// GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algV = new
		// MiniMax<OtherGameMove, OtherGameRole, OtherGameBoard>(
		// roleV, roleH, OtherGameHeuristics.hVertical, 4); // Minimax depth 4

		// GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algH = new
		// MiniMax<OtherGameMove, OtherGameRole, OtherGameBoard>(
		// roleH, roleV, OtherGameHeuristics.hHorizontal, 2); // Minimax depth 2

		// AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard> playerV = new
		// AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>(
		// roleV, algV);

		GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algV = new AlphaBeta<OtherGameMove, OtherGameRole, OtherGameBoard>(
				roleP2, roleP1, OtherGameHeuristics.h, 4); // AlphaBeta depth 4

		GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algH = new AlphaBeta<OtherGameMove, OtherGameRole, OtherGameBoard>(
				roleP2, roleP1, OtherGameHeuristics.h, 2); // AlphaBeta depth 2

		AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard> playerV = new AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>(
				roleP1, algV);

		AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard> playerH = new AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>(
				roleP2, algH);

		ArrayList<AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>> players = new ArrayList<AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>>();

		players.add(playerV); // First Player
		players.add(playerH); // Second Player

		// Setting the initial Board
		OtherGameBoard initialBoard = new OtherGameBoard();

		OtherGame game = new OtherGame(players, initialBoard);
		game.runGame();
	}

}