package iialib.games.algs;

import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;
import iialib.games.model.Player;

@FunctionalInterface
public interface IHeuristic<Board extends IBoard<?, Role, Board>, Role extends IRole> {

	// As `-java.lang.Integer.MIN_VALUE == java.lang.Integer.MIN_VALUE` we prefer
	// to use a reversible MIN_VALUE (java.lang.Integer.MAX_VALUE)
	public static int MIN_VALUE = -java.lang.Integer.MAX_VALUE;
	public static int MAX_VALUE = java.lang.Integer.MAX_VALUE;

	int eval(Board board, Role role);

}
