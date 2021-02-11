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

	public int algoMaxMin(Board board, Move move) {
		if(this.depthMax == 1) {
			return this.h.eval(board, this.playerMaxRole);
		} else {
			this.depthMax--;
			int max = Integer.MIN_VALUE;
			board = board.play(move, playerMaxRole);
			ArrayList<Move> moves = board.possibleMoves(this.playerMaxRole);
			Iterator<Move> iter = moves.iterator();
			while (iter.hasNext()) {
				int min = this.algoMinMax(board, iter.next());
				max = Math.max(max, min);
			}
			return max;
		}
	}

	public int algoMinMax(Board board, Move move) {
		if(this.depthMax == 1) {
			return this.h.eval(board, this.playerMaxRole);
		} else {
			this.depthMax--;
			int min = Integer.MAX_VALUE;
			board = board.play(move, playerMaxRole);
			ArrayList<Move> moves = board.possibleMoves(this.playerMinRole);
			Iterator<Move> iter = moves.iterator();
			while (iter.hasNext()) {
				int max = this.algoMaxMin(board, iter.next());
				min = Math.min(min, max);
			}
			return min;
		}
	}

	@Override
	public Move bestMove(Board board, Role playerRole) {
		System.out.println("[MiniMax]");

		Move bestMove = null;
		ArrayList<Move> moves = board.possibleMoves(playerRole);
		Move firstMove = moves.get(0);
		int max = this.algoMinMax(board, firstMove);
		bestMove = firstMove;
		Iterator<Move> iter = moves.iterator();
		while (iter.hasNext()) {
			Move currentMove = iter.next();
			int newVal = this.algoMinMax(board, currentMove);
			if (newVal > max) {
				bestMove = currentMove;
				max = newVal;
			}
		}
		this.depthMax = DEPTH_MAX_DEFAUT;
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

	//TODO
}
