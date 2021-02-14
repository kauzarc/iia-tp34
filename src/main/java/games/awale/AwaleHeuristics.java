package games.awale;

import iialib.games.algs.IHeuristic;

public class AwaleHeuristics {

    private final static int TOTAL_SEED = 12 * 4;

    public static IHeuristic<AwaleBoard, AwaleRole> h1 = (board, role) -> {
        int seedLeft = board.getNumberSeedsLeftOnBoard();
        int score1 = seedLeft * board.getNumberSeedsCapturable(role)
                + (TOTAL_SEED - seedLeft) * board.getNumberSeedsCaptured(role);
        int score2 = seedLeft * board.getNumberSeedsCapturable(role.getOpponentRole())
                + (TOTAL_SEED - seedLeft) * board.getNumberSeedsCaptured(role.getOpponentRole());
        return score1 - score2;
    };

    public static IHeuristic<AwaleBoard, AwaleRole> h2 = (board, role) -> {
        return board.getNumberSeedsCaptured(role) - board.getNumberSeedsCaptured(role.getOpponentRole());
    };

    public static IHeuristic<AwaleBoard, AwaleRole> h3 = (board, role) -> {
        int score1 = board.getNumberSeedsCapturable(role) + board.getNumberSeedsCaptured(role);
        int score2 = board.getNumberSeedsCapturable(role.getOpponentRole())
                + board.getNumberSeedsCaptured(role.getOpponentRole());
        return score1 - score2;
    };
}
