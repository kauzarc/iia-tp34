package games.otherGame;

import iialib.games.algs.IHeuristic;

public class OtherGameHeuristics {

	public static IHeuristic<OtherGameBoard, OtherGameRole> h = (board, role) -> {
		try {
			return board.getHeadValue();
		} catch (NumberFormatException e) {
			return 0;
		}
	};
}
