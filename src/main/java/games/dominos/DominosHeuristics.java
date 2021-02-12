package games.dominos;

import iialib.games.algs.IHeuristic;

public class DominosHeuristics {

	public static IHeuristic<DominosBoard, DominosRole> hVertical = (board, role) -> {
		return board.nbVerticalMoves() - board.nbHorizontalMoves();
	};

	public static IHeuristic<DominosBoard, DominosRole> hHorizontal = (board, role) -> {
		return board.nbHorizontalMoves() - board.nbVerticalMoves();
	};

}
