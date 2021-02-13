package games.otherGame;

import iialib.games.algs.IHeuristic;

public class OtherGameHeuristics {

	public static IHeuristic<OtherGameBoard, OtherGameRole> p1 = (board, role) -> {
		try {
			return board.getHeadValue();
		} catch (NumberFormatException e) {
			return 0;
		}
	};

	public static IHeuristic<OtherGameBoard, OtherGameRole> p2 = (board, role) -> {
		try {
			return -board.getHeadValue();
		} catch (NumberFormatException e) {
			return 0;
		}
	};

}
