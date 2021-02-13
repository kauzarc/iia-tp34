package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

import java.util.*;
import java.util.ArrayList;

public class MiniMax<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>>
		implements GameAlgorithm<Move, Role, Board> {

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
	private int depth;

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
		ArrayList<Move> moves = board.possibleMoves(playerRole);
		int max = IHeuristic.MIN_VALUE;
		Move best_move = null;

		Iterator<Move> iter = moves.iterator();
		while (iter.hasNext()) {
			Move currentMove = iter.next();
			int newVal = this.minMax(board.play(currentMove, playerRole), playerRole, true);
			if (newVal > max) {
				max = newVal;
				best_move = currentMove;
			}
		}
		return best_move;
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

	private int minMax(Board board, Role playerRole, boolean min_or_max) {
		ArrayList<Move> moves = board.possibleMoves(playerRole);
		this.nbNodes++;

		if((this.depth == 0) || board.isGameOver()) {
			this.nbLeaves++;
			return this.h.eval(board, playerRole);
		} else {
			int val = (min_or_max ? IHeuristic.MIN_VALUE : IHeuristic.MAX_VALUE);

			Iterator<Move> iter = moves.iterator();
			while (iter.hasNext()) {
				this.depth--;
				int depthTemp = this.depth;
				Move currentMove = iter.next();
				Board currentboard = board.play(currentMove, playerRole);
				boolean inv = !min_or_max;
				int currentVal = this.minMax(currentboard, getOpponentRole(playerRole), inv);
				val = (min_or_max ? Math.max(val, currentVal) : Math.max(val, currentVal));
				this.depth = depthTemp;
			}
			return val;
		}
	}



	// private int algoMaxMin(Board board) {
	// 	this.nbNodes++;
	// 	if((this.depth == 0) || board.isGameOver()) {
	// 		this.nbLeaves++;
	// 		return this.h.eval(board, this.playerMaxRole);
	// 	} else {
	// 		this.depth--;
	// 		int depthTemp = this.depth;
	// 		int max = IHeuristic.MIN_VALUE;
	// 		ArrayList<Move> moves = board.possibleMoves(this.playerMinRole);

	// 		Iterator<Move> iter = moves.iterator();
	// 		while (iter.hasNext()) {
	// 			Currentboard = board.play(move, this.playerMaxRole);
	// 			int min = this.algoMinMax(Currentboard);
	// 			max = Math.max(max, min);
	// 			this.depth = depthTemp;
	// 		}
	// 		return max;
	// 	}
	// }

	// private int algoMinMax(Board board) {
	// 	board = board.play(move, this.playerMinRole);
	// 	if((this.depth == 0) || board.isGameOver()) {
	// 		this.nbLeaves++;
	// 		return this.h.eval(board, this.playerMinRole);
	// 	} else {
	// 		this.depth--;
	// 		this.nbNodes++;
	// 		int depthTemp = this.depth;
	// 		int min = IHeuristic.MAX_VALUE;
	// 		ArrayList<Move> moves = board.possibleMoves(this.playerMaxRole);

	// 		Iterator<Move> iter = moves.iterator();
	// 		while (iter.hasNext()) {
	// 			int max = this.algoMaxMin(board, iter.next());
	// 			min = Math.min(min, max);
	// 			this.depth = depthTemp;
	// 		}
	// 		return min;
	// 	}
	// }
}
