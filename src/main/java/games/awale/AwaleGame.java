package games.awale;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.NegAlphaBeta;

public class AwaleGame extends AbstractGame<AwaleMove, AwaleRole, AwaleBoard> {

        public AwaleGame(ArrayList<AIPlayer<AwaleMove, AwaleRole, AwaleBoard>> players, AwaleBoard initialBoard) {
                super(players, initialBoard);
        }

        public static void main(String[] args) {
                AwaleRole role1 = AwaleRole.PLAYER1;
                AwaleRole role2 = AwaleRole.PLAYER2;

                GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> alg1 = new NegAlphaBeta<AwaleMove, AwaleRole, AwaleBoard>(
                                role1, role2, AwaleHeuristics.h4, 4);
                GameAlgorithm<AwaleMove, AwaleRole, AwaleBoard> alg2 = new NegAlphaBeta<AwaleMove, AwaleRole, AwaleBoard>(
                                role2, role1, AwaleHeuristics.h1, 4);

                AIPlayer<AwaleMove, AwaleRole, AwaleBoard> player1 = new AIPlayer<AwaleMove, AwaleRole, AwaleBoard>(
                                role1, alg1);
                AIPlayer<AwaleMove, AwaleRole, AwaleBoard> player2 = new AIPlayer<AwaleMove, AwaleRole, AwaleBoard>(
                                role2, alg2);

                ArrayList<AIPlayer<AwaleMove, AwaleRole, AwaleBoard>> players = new ArrayList<AIPlayer<AwaleMove, AwaleRole, AwaleBoard>>();

                players.add(player1);
                players.add(player2);

                AwaleBoard initialBoard = new AwaleBoard();

                AwaleGame game = new AwaleGame(players, initialBoard);
                game.runGame();
        }
}
