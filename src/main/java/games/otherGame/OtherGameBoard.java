package games.otherGame;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class OtherGameBoard implements IBoard<OtherGameMove, OtherGameRole, OtherGameBoard> {

	// ---------------------- Attributes ---------------------
	// Attributes
	private OtherGameMove head;
	private ArrayList<OtherGameMove> moves;

	// Graph of the game - useful to debug algorithm with course example
	// (IA - TD1 - Exercice 3)
	private Map<OtherGameMove, List<OtherGameMove>> graph = Map.ofEntries(
			Map.entry(new OtherGameMove("A"),
					Arrays.asList(new OtherGameMove("B"), new OtherGameMove("C"), new OtherGameMove("D"))),
			Map.entry(new OtherGameMove("B"), Arrays.asList(new OtherGameMove("E"), new OtherGameMove("F"))),
			Map.entry(new OtherGameMove("C"), Arrays.asList(new OtherGameMove("G"), new OtherGameMove("H"))),
			Map.entry(new OtherGameMove("D"), Arrays.asList(new OtherGameMove("I"), new OtherGameMove("J"))),
			Map.entry(new OtherGameMove("E"), Arrays.asList(new OtherGameMove("K"), new OtherGameMove("L"))),
			Map.entry(new OtherGameMove("F"), Arrays.asList(new OtherGameMove("M"), new OtherGameMove("N"))),
			Map.entry(new OtherGameMove("G"), Arrays.asList(new OtherGameMove("O"), new OtherGameMove("P"))),
			Map.entry(new OtherGameMove("H"), Arrays.asList(new OtherGameMove("Q"), new OtherGameMove("R"))),
			Map.entry(new OtherGameMove("I"), Arrays.asList(new OtherGameMove("S"), new OtherGameMove("T"))),
			Map.entry(new OtherGameMove("J"), Arrays.asList(new OtherGameMove("U"), new OtherGameMove("V"))),
			Map.entry(new OtherGameMove("K"), Arrays.asList(new OtherGameMove("2"), new OtherGameMove("8"))),
			Map.entry(new OtherGameMove("L"), Arrays.asList(new OtherGameMove("3"), new OtherGameMove("5"))),
			Map.entry(new OtherGameMove("M"), Arrays.asList(new OtherGameMove("6"), new OtherGameMove("7"))),
			Map.entry(new OtherGameMove("N"), Arrays.asList(new OtherGameMove("1"), new OtherGameMove("9"))),
			Map.entry(new OtherGameMove("O"), Arrays.asList(new OtherGameMove("4"), new OtherGameMove("2"))),
			Map.entry(new OtherGameMove("P"), Arrays.asList(new OtherGameMove("3"), new OtherGameMove("7"))),
			Map.entry(new OtherGameMove("Q"), Arrays.asList(new OtherGameMove("6"), new OtherGameMove("10"))),
			Map.entry(new OtherGameMove("R"), Arrays.asList(new OtherGameMove("4"), new OtherGameMove("3"))),
			Map.entry(new OtherGameMove("S"), Arrays.asList(new OtherGameMove("2"), new OtherGameMove("4"))),
			Map.entry(new OtherGameMove("T"), Arrays.asList(new OtherGameMove("1"), new OtherGameMove("6"))),
			Map.entry(new OtherGameMove("U"), Arrays.asList(new OtherGameMove("7"), new OtherGameMove("3"))),
			Map.entry(new OtherGameMove("V"), Arrays.asList(new OtherGameMove("2"), new OtherGameMove("8"))));
	// ---------------------- Constructors ---------------------

	public OtherGameBoard() {
		this.head = new OtherGameMove("0");
		this.moves = new ArrayList<OtherGameMove>();
	}

	// Constructors

	public OtherGameBoard(OtherGameMove head, ArrayList<OtherGameMove> moves) {
		this.head = head;
		this.moves = new ArrayList<>(moves);
		this.moves.add(head);
	}

	// --------------------- Public Methods ---------------------

	public int getHeadValue() {
		return Integer.parseInt(this.head.toString());
	}

	// --------------------- IBoard Methods ---------------------

	@Override
	public ArrayList<OtherGameMove> possibleMoves(OtherGameRole playerRole) {
		return new ArrayList<OtherGameMove>(this.graph.get(this.head));
	}

	@Override
	public OtherGameBoard play(OtherGameMove move, OtherGameRole playerRole) {
		return new OtherGameBoard(move, this.moves);
	}

	@Override
	public boolean isValidMove(OtherGameMove move, OtherGameRole playerRole) {
		return this.graph.containsKey(move);
	}

	@Override
	public boolean isGameOver() {
		return !this.graph.containsKey(this.head);
	}

	@Override
	public ArrayList<Score<OtherGameRole>> getScores() {
		ArrayList<Score<OtherGameRole>> scores = new ArrayList<Score<OtherGameRole>>();

		scores.add(
				new Score<OtherGameRole>(OtherGameRole.PLAYER_ONE, Score.Status.WIN, Integer.parseInt(this.head.toString())));
		return scores;
	}

	// --------------------- Other Methods ---------------------
	@Override
	public String toString() {
		StringBuilder retstr = new StringBuilder(new String(""));
		for (OtherGameMove move : this.moves) {
			retstr.append(move.toString());
		}
		return retstr.toString();
	}
}
