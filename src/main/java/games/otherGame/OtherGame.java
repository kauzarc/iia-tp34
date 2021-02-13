package games.otherGame;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.algs.algorithms.MiniMax;

public class OtherGame extends AbstractGame<OtherGameMove, OtherGameRole, OtherGameBoard> {

	OtherGame(ArrayList<AIPlayer<OtherGameMove, OtherGameRole, OtherGameBoard>> players, OtherGameBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {

		OtherGameRole roleP1 = OtherGameRole.PLAYER_ONE;
		OtherGameRole roleP2 = OtherGameRole.PLAYER_TWO;

		// GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algV = new
		// MiniMax<OtherGameMove, OtherGameRole, OtherGameBoard>(
		// roleP1, roleP2, OtherGameHeuristics.p1, 3); // Minimax depth 4

		// GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algH = new
		// MiniMax<OtherGameMove, OtherGameRole, OtherGameBoard>(
		// roleP1, roleP2, OtherGameHeuristics.p2, 3); // Minimax depth 2

		// GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algV = new
		// AlphaBeta<OtherGameMove, OtherGameRole, OtherGameBoard>(
		// roleP2, roleP1, OtherGameHeuristics.h, 4); // AlphaBeta depth 4

		GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algV = new AlphaBeta<OtherGameMove, OtherGameRole, OtherGameBoard>(
				roleP2, roleP1, OtherGameHeuristics.p1, 2); // AlphaBeta depth 4

		GameAlgorithm<OtherGameMove, OtherGameRole, OtherGameBoard> algH = new AlphaBeta<OtherGameMove, OtherGameRole, OtherGameBoard>(
				roleP2, roleP1, OtherGameHeuristics.p2, 3); // AlphaBeta depth 2

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