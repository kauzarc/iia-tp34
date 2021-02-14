package games.nim;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.NegAlphaBeta;

public class NimGame extends AbstractGame<NimMove, NimRole, NimBoard> {

	NimGame(ArrayList<AIPlayer<NimMove, NimRole, NimBoard>> players, NimBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {
		NimRole role1 = NimRole.PLAYER1;
		NimRole role2 = NimRole.PLAYER2;

		GameAlgorithm<NimMove, NimRole, NimBoard> alg1 = new NegAlphaBeta<NimMove, NimRole, NimBoard>(role1, role2,
				NimHeuristics.player1, 2);
		GameAlgorithm<NimMove, NimRole, NimBoard> alg2 = new NegAlphaBeta<NimMove, NimRole, NimBoard>(role2, role1,
				NimHeuristics.player2, 2);

		AIPlayer<NimMove, NimRole, NimBoard> player1 = new AIPlayer<NimMove, NimRole, NimBoard>(role1, alg1);
		AIPlayer<NimMove, NimRole, NimBoard> player2 = new AIPlayer<NimMove, NimRole, NimBoard>(role2, alg2);

		ArrayList<AIPlayer<NimMove, NimRole, NimBoard>> players = new ArrayList<AIPlayer<NimMove, NimRole, NimBoard>>();

		players.add(player1);
		players.add(player2);

		NimBoard initialBoard = new NimBoard();

		NimGame game = new NimGame(players, initialBoard);
		game.runGame();
	}
}