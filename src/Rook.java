package Game;
import java.awt.Color;

public class Rook extends Piece {
    public Rook(Color c, int row, int column) {
        super(c, row, column);
    }
    @Override
    public String toString() {
        return "Rook";
    }
}
