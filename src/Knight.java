package Game;
import java.awt.Color;

public class Knight extends Piece {
    public Knight(Color c, int row, int column) {
        super(c, row, column);
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
