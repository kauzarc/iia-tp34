package iialib.games.algs.algorithms;

import java.util.ArrayList;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

public class NegAlphaBeta<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>>
		implements GameAlgorithm<Move, Role, Board> {

	private class Result {
		public int bestAlpha;
		public Move bestMove;

		public Result(int bestAlpha, Move bestMove) {
			this.bestAlpha = bestAlpha;
			this.bestMove = bestMove;
		}
	}

	// Constants
	/**
	 * DefauLt value for depth limit
	 */
	private final static int DEPTH_MAX_DEFAULT = 4;

	// Attributes
	/**
	 * Role of the max player
	 */
	private final Role playerMinRole;

	/**
	 * Role of the min player
	 */
	private final Role playerMaxRole;

	/**
	 * Algorithm max depth
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
	 */
	private int nbLeaves;

	// --------- Constructors ---------

	public NegAlphaBeta(Role playerMinRole, Role playerMaxRole, IHeuristic<Board, Role> h) {
		this.playerMinRole = playerMinRole;
		this.playerMaxRole = playerMaxRole;
		this.h = h;
	}

	//
	public NegAlphaBeta(Role playerMinRole, Role playerMaxRole, IHeuristic<Board, Role> h, int depthMax) {
		this(playerMinRole, playerMaxRole, h);
		this.depthMax = depthMax;
	}

	/*
	 * IAlgo METHODS =============
	 */
	@Override
	public Move bestMove(Board board, Role playerRole) {
		System.out.println("[NegAlphaBeta]");

		Result result = this.negAlphaBeta(board, playerRole, 0, IHeuristic.MIN_VALUE, IHeuristic.MAX_VALUE, 1);
		if (result.bestMove == null) {
			ArrayList<Move> moves = board.possibleMoves(playerRole);
			return moves.get(0);
		}
		return result.bestMove;
	}

	/*
	 * PUBLIC METHODS ==============
	 */

	public String toString() {
		return "NegAlphaBeta(ProfMax=" + depthMax + ")";
	}

	/*
	 * PRIVATE METHODS ===============
	 */
	private Role getOpponentRole(Role playerRole) {
		return playerRole == this.playerMinRole ? this.playerMaxRole : this.playerMinRole;
	}

	private Result negAlphaBeta(Board board, Role playerRole, int depth, int alpha, int beta, int p) {
		++this.nbNodes;
		if (depth >= this.depthMax || board.isGameOver()) {
			++this.nbLeaves;
			return new Result(p * this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> possibleMoves = board.possibleMoves(playerRole);
		Role opponent = this.getOpponentRole(playerRole);
		Move bestMove = null;
		for (Move move : possibleMoves) {
			Board newBoard = board.play(move, playerRole);
			Result result = this.negAlphaBeta(newBoard, opponent, depth + 1, -beta, -alpha, p * -1);
			if (-result.bestAlpha > alpha) {
				alpha = -result.bestAlpha;
				bestMove = move;
			}
			if (alpha >= beta) {
				return new Result(beta, bestMove);
			}
		}
		return new Result(alpha, bestMove);
	}
}
