package games.dominos;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;

public class DominosGame extends AbstractGame<DominosMove, DominosRole, DominosBoard> {

	DominosGame(ArrayList<AIPlayer<DominosMove, DominosRole, DominosBoard>> players, DominosBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {

		DominosRole roleV = DominosRole.VERTICAL;
		DominosRole roleH = DominosRole.HORIZONTAL;

		// GameAlgorithm<DominosMove, DominosRole, DominosBoard> algV = new
		// MiniMax<DominosMove, DominosRole, DominosBoard>(
		// roleV, roleH, DominosHeuristics.hVertical, 0); // Minimax depth 4

		// GameAlgorithm<DominosMove, DominosRole, DominosBoard> algH = new
		// MiniMax<DominosMove, DominosRole, DominosBoard>(
		// roleH, roleV, DominosHeuristics.hHorizontal, 2); // Minimax depth 2

		// AIPlayer<DominosMove, DominosRole, DominosBoard> playerV = new
		// AIPlayer<DominosMove, DominosRole, DominosBoard>(
		// roleV, algV);

		GameAlgorithm<DominosMove, DominosRole, DominosBoard> algV = new AlphaBeta<>(roleH, roleV,
				DominosHeuristics.hVertical, 2); // AlphaBeta depth 2

		GameAlgorithm<DominosMove, DominosRole, DominosBoard> algH = new AlphaBeta<>(roleV, roleH,
				DominosHeuristics.hHorizontal, 2); // AlphaBeta depth 2

		AIPlayer<DominosMove, DominosRole, DominosBoard> playerV = new AIPlayer<>(roleV, algV);

		AIPlayer<DominosMove, DominosRole, DominosBoard> playerH = new AIPlayer<>(roleH, algH);

		ArrayList<AIPlayer<DominosMove, DominosRole, DominosBoard>> players = new ArrayList<AIPlayer<DominosMove, DominosRole, DominosBoard>>();

		players.add(playerV); // First Player
		players.add(playerH); // Second Player

		// Setting the initial Board
		DominosBoard initialBoard = new DominosBoard();

		DominosGame game = new DominosGame(players, initialBoard);
		game.runGame();
	}

}