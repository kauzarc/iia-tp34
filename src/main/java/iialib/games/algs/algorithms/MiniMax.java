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
		public int minOrMax;
		public Move bestMove;

		public Result(int minOrMax, Move bestMove) {
			this.minOrMax = minOrMax;
			this.bestMove = bestMove;
		}
	}

	// Constants
	/**
	 * DEFAULT value for depth limit
	 */
	private final static int DEPTH_MAX_DEFAULT = 4;

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
	private int depthMax = DEPTH_MAX_DEFAULT;

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
		Result result = this.maxMin(board, playerRole, this.depthMax);

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

	private Result minMax(Board board, Role playerRole, int depth) {
		this.nbNodes++;
		if (depth == 0 || board.isGameOver()) {
			this.nbLeaves++;
			return new Result(this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> moves = board.possibleMoves(playerRole);
		int minValue = IHeuristic.MAX_VALUE;
		Move bestMove = null;
		Role opponent = getOpponentRole(playerRole);
		for (Move currentMove : moves) {
			Board currentBoard = board.play(currentMove, playerRole);
			Result result = this.maxMin(currentBoard, opponent, depth - 1);
			if (result.minOrMax < minValue) {
				minValue = result.minOrMax;
				bestMove = currentMove;
			}
		}
		return new Result(minValue, bestMove);
	}

	private Result maxMin(Board board, Role playerRole, int depth) {
		this.nbNodes++;
		if (depth == 0 || board.isGameOver()) {
			this.nbLeaves++;
			return new Result(this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> moves = board.possibleMoves(playerRole);
		int maxValue = IHeuristic.MIN_VALUE;
		Move bestMove = null;
		Role opponent = getOpponentRole(playerRole);
		for (Move currentMove : moves) {
			Board currentBoard = board.play(currentMove, playerRole);
			Result result = this.minMax(currentBoard, opponent, depth - 1);
			if (result.minOrMax > maxValue) {
				maxValue = result.minOrMax;
				bestMove = currentMove;
			}
		}
		return new Result(maxValue, bestMove);
	}

}
