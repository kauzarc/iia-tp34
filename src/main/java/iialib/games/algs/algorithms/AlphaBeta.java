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
		public int alphaOrBeta;
		public Move bestMove;

		public ABResult(int alphaOrBeta, Move bestMove) {
			this.alphaOrBeta = alphaOrBeta;
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

		ABResult result = this.alphaBetaMaxMin(board, playerRole, 0, IHeuristic.MIN_VALUE, IHeuristic.MAX_VALUE);
		if (result.bestMove == null) {
			return board.possibleMoves(playerRole).get(0);
		}
		return result.bestMove;
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

	private ABResult alphaBetaMinMax(Board board, Role playerRole, int depth, int alpha, int beta) {
		++this.nbNodes;
		if (depth >= this.depthMax || board.isGameOver()) {
			++this.nbLeaves;
			return new ABResult(this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> possibleMoves = board.possibleMoves(playerRole);
		Role opponent = this.getOpponentRole(playerRole);
		Move bestMove = null;
		for (Move move : possibleMoves) {
			Board newBoard = board.play(move, playerRole);
			ABResult result = this.alphaBetaMaxMin(newBoard, opponent, depth + 1, alpha, beta);
			if (result.alphaOrBeta < beta) {
				beta = result.alphaOrBeta;
				bestMove = move;
			}
			if (beta <= alpha) {
				return new ABResult(beta, bestMove);
			}
		}
		return new ABResult(beta, bestMove);
	}

	private ABResult alphaBetaMaxMin(Board board, Role playerRole, int depth, int alpha, int beta) {
		++this.nbNodes;
		if (depth >= this.depthMax || board.isGameOver()) {
			++this.nbLeaves;
			return new ABResult(this.h.eval(board, playerRole), null);
		}

		ArrayList<Move> possibleMoves = board.possibleMoves(playerRole);
		Role opponent = this.getOpponentRole(playerRole);
		Move bestMove = null;
		for (Move move : possibleMoves) {
			Board newBoard = board.play(move, playerRole);
			ABResult result = this.alphaBetaMinMax(newBoard, opponent, depth + 1, alpha, beta);
			if (result.alphaOrBeta > alpha) {
				alpha = result.alphaOrBeta;
				bestMove = move;
			}
			if (alpha >= beta) {
				return new ABResult(alpha, bestMove);
			}
		}
		return new ABResult(alpha, bestMove);
	}
}
