package iialib.games.algs.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import iialib.games.algs.GameAlgorithm;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

public class Human<Move extends IMove, Role extends IRole, Board extends IBoard<Move, Role, Board>>
        implements GameAlgorithm<Move, Role, Board> {

    @Override
    public Move bestMove(Board board, Role playerRole) {
        System.out.println("[Human]");

        ArrayList<Move> moves = board.possibleMoves(playerRole);

        System.out.println("Here are the moves you can play :");

        for (int i = 0; i < moves.size(); ++i) {
            System.out.println(String.valueOf(i) + ". " + moves.get(i).toString());
        }

        int index = -1;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (index == -1) {
            try {
                System.out.println("Please enter a number :");
                index = Integer.parseInt(br.readLine());
            } catch (IOException ioe) {
                System.out.println(ioe);
                index = -1;
            }
        }

        return moves.get(index);
    }

}
