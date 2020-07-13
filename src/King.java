package Game;
import java.awt.Color;

public class King extends Piece {
    public King(Color c, int row, int column) {
        super(c, row, column);
    }

    @Override
    public String toString() {
        return "King";
    }

}
