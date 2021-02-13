package iialib.games.algs.algorithms;

import java.util.ArrayList;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

public class AlphaBeta<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>>
		implements GameAlgorithm<Move, Role, Board> {

	private class ABResult {
		public int best_alpha;
		public Move best_move;

		public ABResult(int best_alpha, Move best_move) {
			this.best_alpha = best_alpha;
			this.best_move = best_move;
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

	public AlphaBeta(Role playerMinRole, Role playerMaxRole, IHeuristic<Board, Role> h) {
		this.playerMinRole = playerMinRole;
		this.playerMaxRole = playerMaxRole;
		this.h = h;
	}

	//
	public AlphaBeta(Role playerMinRole, Role playerMaxRole, IHeuristic<Board, Role> h, int depthMax) {
		this(playerMinRole, playerMaxRole, h);
		this.depthMax = depthMax;
	}

	/*
	 * IAlgo METHODS =============
	 */
	@Override
	public Move bestMove(Board board, Role playerRole) {
		System.out.println("[AlphaBeta]");

		ABResult result = this.negAlphaBeta(board, playerRole, 0, IHeuristic.MIN_VALUE, IHeuristic.MAX_VALUE, 1);
		if (result.best_move == null) {
			ArrayList<Move> moves = board.possibleMoves(playerRole);
			int move_index = (int) (Math.random() * moves.size());
			return moves.get(move_index);
		}
		return result.best_move;
	}

	/*
	 * PUBLIC METHODS ==============
	 */

	public String toString() {
		return "AlphaBeta(ProfMax=" + depthMax + ")";
	}

	/*
	 * PRIVATE METHODS ===============
	 */
	private Role getOpponentRole(Role playerRole) {
		return playerRole == this.playerMinRole ? this.playerMaxRole : this.playerMinRole;
	}

	private ABResult negAlphaBeta(Board board, Role playerRole, int depth, int alpha, int beta, int p) {
		++this.nbNodes;
		if (depth >= this.depthMax || board.isGameOver()) {
			++this.nbLeaves;
			return new ABResult(p * this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> possibleMoves = board.possibleMoves(playerRole);
		Role opponent = this.getOpponentRole(playerRole);
		Move best_move = null;
		for (Move move : possibleMoves) {
			Board newBoard = board.play(move, playerRole);
			ABResult result = this.negAlphaBeta(newBoard, opponent, depth + 1, -beta, -alpha, -1 * p);
			// System.out.println(depth + " > move: " + move + ", player: " + playerRole +
			// ", result: " + (-result.best_alpha)
			// + ", for move: " + result.best_move);
			if (-result.best_alpha > alpha) {
				alpha = -result.best_alpha;
				best_move = move;
			}
			if (alpha >= beta) {
				return new ABResult(beta, best_move);
			}
		}
		return new ABResult(alpha, best_move);
	}
}
