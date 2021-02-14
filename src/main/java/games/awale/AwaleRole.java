package games.awale;

import iialib.games.model.IRole;

public enum AwaleRole implements IRole {
    PLAYER1, PLAYER2;

    public AwaleRole getOpponentRole() {
        return this == AwaleRole.PLAYER1 ? AwaleRole.PLAYER2 : AwaleRole.PLAYER1;
    }
}