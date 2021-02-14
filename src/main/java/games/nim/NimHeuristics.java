package games.nim;

import iialib.games.algs.IHeuristic;

public class NimHeuristics {

	public static IHeuristic<NimBoard, NimRole> player1 = (board, role) -> {
		int n = board.getNbSticks();
		int p = role == NimRole.PLAYER1 ? 1 : -1;
		if (n == 0) {
			return p * IHeuristic.MAX_VALUE;
		}
		if (n == 1) {
			return p * IHeuristic.MIN_VALUE;
		}
		return board.BOARD_SIZE - board.getNbSticks();
	};

	public static IHeuristic<NimBoard, NimRole> player2 = (board, role) -> {
		int n = board.getNbSticks();
		int p = role == NimRole.PLAYER2 ? 1 : -1;
		if (n == 0) {
			return p * IHeuristic.MAX_VALUE;
		}
		if (n == 1) {
			return p * IHeuristic.MIN_VALUE;
		}
		return board.BOARD_SIZE - board.getNbSticks();
	};

}
