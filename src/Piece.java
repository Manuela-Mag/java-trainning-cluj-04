package Game;
import java.awt.Color;

public abstract class Piece {
    private boolean activated;
    private int moveCount;
    private int Row;
    private int Column;
    private Color color;
    public Piece(Color c, int row, int column){
        moveCount = 0;
        color = c;
        Row = row;
        Column = column;
        activated = false;
    }
    @Override
    public abstract String toString();
    public void activatePiece(){
        activated = true;
    }
    public void deactivatePiece(){
        activated = false;
    }
    public boolean getActivationStatus(){
        return activated;
    }
    public int getRow(){
        return Row;
    }
    public int getColumn(){
        return Column;
    }
    public Color getColor(){
        return color;
    }
    public String colorToString(){
        if(color == Color.WHITE){
            return "white";
        }
        else if(color == Color.BLACK){
            return "black";
        }
        return "";
    }
    public int getMoveCount(){
        return moveCount;
    }
    public void setRow(int newRow){
        Row = newRow;
    }
    public void setColumn(int newColumn){
        Column = newColumn;
    }

    public void incrementMoveCount(){
        moveCount += 1;
    }
}
