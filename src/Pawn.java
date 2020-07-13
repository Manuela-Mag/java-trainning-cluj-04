package Game;
import java.awt.Color;

public class Pawn extends Piece {
    public Pawn(Color c, int row, int column) {
        super(c, row, column);

    }
    @Override
    public String toString() {
        return "Pawn";
    }
}
