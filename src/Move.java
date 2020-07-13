package Game;

import java.awt.Color;

public class Move {
    private Piece piece;
    private int newRow;
    private int newColumn;
    private int Row;
    private int column;
    public Move(Piece p, int row, int column ,int newrow, int newcolumn){

        piece = p;
        newRow = newrow;
        newColumn = newcolumn;
        Row = row;
        column = column;
    }

    public Piece getPiece(){
        return piece;
    }

    public int getnewRow(){
        return newRow;
    }

    public int getnewColumn(){
        return newColumn;
    }

    @Override
    public String toString(){
        return "The " + getColor() + " " + piece.toString() + " from [" + Row +"," + column + "] moved to [" + newRow +"," + newColumn + "]";
    }
    public String getColor(){
        if(piece.getColor() == Color.WHITE){
            return "white";
        }
        else{
            return "black";
        }
    }
}