package Game;
import java.awt.Color;

public class Bishop extends Piece {

    public Bishop(Color c, int row, int column) {
        super(c, row, column);
    }
    @Override
    public String toString() {
        return "Bishop";
    }
}