package games.nim;

import iialib.games.algs.IHeuristic;

public class NimHeuristics {

	public static IHeuristic<NimBoard, NimRole> player1 = (board, role) -> {
        return board.getNbSticks();
	};

	public static IHeuristic<NimBoard, NimRole> player2 = (board, role) -> {
        return board.getNbSticks();
	};

}
