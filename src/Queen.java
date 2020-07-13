package Game;
import java.awt.Color;

public class Queen extends Piece {
    public Queen(Color c, int row, int column) {
        super(c, row, column);
    }
    @Override
    public String toString() {
        return "Queen";
    }
}
