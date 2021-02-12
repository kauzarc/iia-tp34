package games.otherGame;

import iialib.games.model.IMove;

public class OtherGameMove implements IMove {

    public final String value;

    OtherGameMove(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OtherGameMove)) {
            return false;
        }
        OtherGameMove gameMove = (OtherGameMove) o;
        return this.value.equals(gameMove.value);
    }

    @Override
    public int hashCode() {
        int result = this.value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
