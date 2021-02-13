package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

import java.util.ArrayList;

public class MiniMax<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>>
		implements GameAlgorithm<Move, Role, Board> {

	private class Result {
		public int value;
		public Move bestMove;

		public Result(int value, Move bestMove) {
			this.value = value;
			this.bestMove = bestMove;
		}
	}

	// Constants
	/**
	 * Defaut value for depth limit
	 */
	private final static int DEPTH_MAX_DEFAUT = 4;

	// Attributes
	/**
	 * Role of the max player
	 */
	private final Role playerMaxRole;

	/**
	 * Role of the min player
	 */
	private final Role playerMinRole;

	/**
	 * Algorithm max depth et depth
	 */
	private int depthMax = DEPTH_MAX_DEFAUT;

	/**
	 * Heuristic used by the max player
	 */
	private IHeuristic<Board, Role> h;

	//
	/**
	 * number of internal visited (developed) nodes (for stats)
	 */
	private int nbNodes;

	/**
	 * number of leaves nodes nodes (for stats)
	 *
	 */
	private int nbLeaves;

	// --------- Constructors ---------

	public MiniMax(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
		this.playerMaxRole = playerMaxRole;
		this.playerMinRole = playerMinRole;
		this.h = h;
	}

	public MiniMax(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
		this(playerMaxRole, playerMinRole, h);
		this.depthMax = depthMax;
	}

	/*
	 * IAlgo METHODS =============
	 */

	@Override
	public Move bestMove(Board board, Role playerRole) {
		Result result = this.maxMin(board, playerRole, this.depthMax, 1);

		if (result.bestMove == null) {
			return board.possibleMoves(playerRole).get(0);
		}
		return result.bestMove;
	}

	/*
	 * PUBLIC METHODS ==============
	 */

	public String toString() {
		return "MiniMax(ProfMax=" + depthMax + ")";
	}

	/*
	 * PRIVATE METHODS ===============
	 */

	private Role getOpponentRole(Role playerRole) {
		return playerRole == this.playerMinRole ? this.playerMaxRole : this.playerMinRole;
	}

	private Result maxMin(Board board, Role playerRole, int depth, int p) {
		this.nbNodes++;
		if (depth == 0 || board.isGameOver()) {
			this.nbLeaves++;
			return new Result(p * this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> moves = board.possibleMoves(playerRole);
		int bestValue = IHeuristic.MIN_VALUE;
		Move bestMove = null;
		Role opponent = getOpponentRole(playerRole);
		for (Move currentMove : moves) {
			Board currentBoard = board.play(currentMove, playerRole);
			Result result = this.maxMin(currentBoard, opponent, depth - 1, p * -1);
			if (-result.value > bestValue) {
				bestValue = -result.value;
				bestMove = currentMove;
			}
		}
		return new Result(bestValue, bestMove);
	}

	// private int algoMaxMin(Board board) {
	// this.nbNodes++;
	// if((this.depth == 0) || board.isGameOver()) {
	// this.nbLeaves++;
	// return this.h.eval(board, this.playerMaxRole);
	// } else {
	// this.depth--;
	// int depthTemp = this.depth;
	// int max = IHeuristic.MIN_VALUE;
	// ArrayList<Move> moves = board.possibleMoves(this.playerMinRole);

	// Iterator<Move> iter = moves.iterator();
	// while (iter.hasNext()) {
	// Currentboard = board.play(move, this.playerMaxRole);
	// int min = this.algoMinMax(Currentboard);
	// max = Math.max(max, min);
	// this.depth = depthTemp;
	// }
	// return max;
	// }
	// }

	// private int algoMinMax(Board board) {
	// board = board.play(move, this.playerMinRole);
	// if((this.depth == 0) || board.isGameOver()) {
	// this.nbLeaves++;
	// return this.h.eval(board, this.playerMinRole);
	// } else {
	// this.depth--;
	// this.nbNodes++;
	// int depthTemp = this.depth;
	// int min = IHeuristic.MAX_VALUE;
	// ArrayList<Move> moves = board.possibleMoves(this.playerMaxRole);

	// Iterator<Move> iter = moves.iterator();
	// while (iter.hasNext()) {
	// int max = this.algoMaxMin(board, iter.next());
	// min = Math.min(min, max);
	// this.depth = depthTemp;
	// }
	// return min;
	// }
	// }
}
