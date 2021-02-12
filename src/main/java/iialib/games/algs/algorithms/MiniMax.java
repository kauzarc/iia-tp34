package iialib.games.algs.algorithms;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;
import games.dominos.DominosBoard;
import games.dominos.DominosMove;

import java.util.*;
import java.util.ArrayList;

public class MiniMax<Move extends IMove,Role extends IRole,Board extends IBoard<Move,Role,Board>> implements GameAlgorithm<Move,Role,Board> {

	// Constants
	/** Defaut value for depth limit
     */
	private final static int DEPTH_MAX_DEFAUT = 4;

	// Attributes
	/** Role of the max player
     */
	private final Role playerMaxRole;

	/** Role of the min player
     */
	private final Role playerMinRole;

	/** Algorithm max depth
     */
	private int depthMax = DEPTH_MAX_DEFAUT;
	private int depth;


	/** Heuristic used by the max player
     */
	private IHeuristic<Board, Role> h;

	//
	/** number of internal visited (developed) nodes (for stats)
     */
	private int nbNodes;

	/** number of leaves nodes nodes (for stats)

     */
	private int nbLeaves;

	// --------- Constructors ---------

	public MiniMax(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
		this.playerMaxRole = playerMaxRole;
		this.playerMinRole = playerMinRole;
		this.h = h;
	}

	//
	public MiniMax(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
		this(playerMaxRole, playerMinRole, h);
		this.depthMax = depthMax;
	}

	/*
	 * IAlgo METHODS =============
	 */

	@Override
	public Move bestMove(Board board, Role playerRole) {
		System.out.println("[MiniMax]");

		this.depth = this.depthMax;
		ArrayList<Move> moves = board.possibleMoves(playerRole);
		Move firstMove = moves.get(0);
		int max = this.algoMaxMin(board, firstMove);
		Move bestMove = firstMove;

		Iterator<Move> iter = moves.iterator();
		while (iter.hasNext() && this.depth > 0) {
			this.depth--;
			Move currentMove = iter.next();
			int newVal = this.algoMaxMin(board, currentMove);
			if (newVal > max) {
				bestMove = currentMove;
				max = newVal;
			}
			this.depth = this.depthMax;
		}
		return bestMove;
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
	private int algoMaxMin(Board board, Move move) {
		board = board.play(move, this.playerMaxRole);
		ArrayList<Move> moves = board.possibleMoves(this.playerMinRole);
		if((this.depth == 0) || (moves.size() <= 1)) {
			this.nbLeaves++;
			return this.h.eval(board, this.playerMinRole);
		} else {
			this.depth--;
			this.nbNodes++;
			int depthTemp = this.depth;
			int max = IHeuristic.MIN_VALUE;

			Iterator<Move> iter = moves.iterator();
			while (iter.hasNext()) {
				int min = this.algoMinMax(board, iter.next());
				max = Math.max(max, min);
				this.depth = depthTemp;
			}
			return max;
		}
	}

	private int algoMinMax(Board board, Move move) {
		board = board.play(move, this.playerMinRole);
		ArrayList<Move> moves = board.possibleMoves(this.playerMaxRole);
		if((this.depth == 0) || (moves.size() <= 1)) {
			this.nbLeaves++;
			return this.h.eval(board, this.playerMaxRole);
		} else {
			this.depth--;
			this.nbNodes++;
			int depthTemp = this.depth;
			int min = IHeuristic.MAX_VALUE;

			Iterator<Move> iter = moves.iterator();
			while (iter.hasNext()) {
				int max = this.algoMaxMin(board, iter.next());
				min = Math.min(min, max);
				this.depth = depthTemp;
			}
			return min;
		}
	}
}
