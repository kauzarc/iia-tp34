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

    public static IHeuristic<AwaleBoard, AwaleRole> player1 = (board, role) -> {
        // TODO
        return 1;
    };

    public static IHeuristic<AwaleBoard, AwaleRole> player2 = (board, role) -> {
        // TODO
        return 1;
    };
}
