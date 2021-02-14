package games.dominos;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.NegAlphaBeta;

public class DominosGame extends AbstractGame<DominosMove, DominosRole, DominosBoard> {

	DominosGame(ArrayList<AIPlayer<DominosMove, DominosRole, DominosBoard>> players, DominosBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {

		DominosRole roleV = DominosRole.VERTICAL;
		DominosRole roleH = DominosRole.HORIZONTAL;

		GameAlgorithm<DominosMove, DominosRole, DominosBoard> algV = new NegAlphaBeta<>(roleH, roleV,
				DominosHeuristics.hVertical, 1); // NegAlphaBeta depth 2

		GameAlgorithm<DominosMove, DominosRole, DominosBoard> algH = new NegAlphaBeta<>(roleV, roleH,
				DominosHeuristics.hHorizontal, 6); // NegAlphaBeta depth 2

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