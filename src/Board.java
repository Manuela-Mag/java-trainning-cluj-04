package Game;

import java.awt.Color;
import java.util.ArrayList;

public class Board {
    private int turnCount;
    private Piece[][] board = new Piece[8][8];
    private ArrayList<Move> moves = new ArrayList<>();

    public Board() {
        turnCount = 0;

        //Black
        board[0][0] = new Rook(Color.BLACK, 0, 0);
        board[0][1] = new Knight(Color.BLACK, 0, 1);
        board[0][2] = new Bishop(Color.BLACK, 0, 2);
        board[0][3] = new Queen(Color.BLACK, 0, 3);
        board[0][4] = new King(Color.BLACK, 0, 4);
        board[0][5] = new Bishop(Color.BLACK, 0, 5);
        board[0][6] = new Knight(Color.BLACK, 0, 6);
        board[0][7] = new Rook(Color.BLACK, 0, 7);
        board[1][0] = new Pawn(Color.BLACK, 1, 0);
        board[1][1] = new Pawn(Color.BLACK, 1, 1);
        board[1][2] = new Pawn(Color.BLACK, 1, 2);
        board[1][3] = new Pawn(Color.BLACK, 1, 3);
        board[1][4] = new Pawn(Color.BLACK, 1, 4);
        board[1][5] = new Pawn(Color.BLACK, 1, 5);
        board[1][6] = new Pawn(Color.BLACK, 1, 6);
        board[1][7] = new Pawn(Color.BLACK, 1, 7);

        // White
        board[6][0] = new Pawn(Color.WHITE, 6, 0);
        board[6][1] = new Pawn(Color.WHITE, 6, 1);
        board[6][2] = new Pawn(Color.WHITE, 6, 2);
        board[6][3] = new Pawn(Color.WHITE, 6, 3);
        board[6][4] = new Pawn(Color.WHITE, 6, 4);
        board[6][5] = new Pawn(Color.WHITE, 6, 5);
        board[6][6] = new Pawn(Color.WHITE, 6, 6);
        board[6][7] = new Pawn(Color.WHITE, 6, 7);
        board[7][0] = new Rook(Color.WHITE, 7, 0);
        board[7][1] = new Knight(Color.WHITE, 7, 1);
        board[7][2] = new Bishop(Color.WHITE, 7, 2);
        board[7][3] = new Queen(Color.WHITE, 7, 3);
        board[7][4] = new King(Color.WHITE, 7, 4);
        board[7][5] = new Bishop(Color.WHITE, 7, 5);
        board[7][6] = new Knight(Color.WHITE, 7, 6);
        board[7][7] = new Rook(Color.WHITE, 7, 7);
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    System.out.print(" ");
                    System.out.print(board[i][j].colorToString().substring(0,1));
                    System.out.print(board[i][j].toString().substring(0, 2));
                    System.out.print(" ");
                } else {
                    System.out.print(" " + i + "," + j + " ");
                }
            }
            System.out.println("");
        }
    }

    public void movePiece(Piece piece, int newRow, int newColumn) {
        if (whoseTurn().toLowerCase().equals(piece.colorToString().toLowerCase())) {
            if (isLegalMove(piece, newRow, newColumn)) {
                moves.add(new Move(piece, piece.getRow(), piece.getColumn(), newRow, newColumn)); 
                addPiece(board[piece.getRow()][piece.getColumn()], newRow, newColumn); 
                removePiece(piece);
                piece.setColumn(newColumn);    
                piece.setRow(newRow);
                piece.incrementMoveCount();    
                turnCount++;                   
            }
        }
        else{
            System.out.println("It is  " + whoseTurn() + "'s turn.  You can't move that piece yet.");
        }
    }

    public void addPiece(Piece p, int Row, int Column) {
        board[Row][Column] = p;
    }

    public void removePiece(Piece p) {
        board[p.getRow()][p.getColumn()] = null;
    }

    public boolean isLegalMove(Piece p, int newRow, int newColumn) {
        if (p != null) {
            ArrayList<int[]> legalMoves = getLegalMoves(p);
            int i = 0;
            while (i < legalMoves.size()) {
                if (legalMoves.get(i)[0] == newRow && legalMoves.get(i)[1] == newColumn) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public ArrayList<int[]> getValidMoves(Piece p, Piece [][] b) { 
        if (p != null) {
            switch (p.toString()) {
                case "Pawn":
                    return getPawnMoves(p,b);
                case "King":
                    return getKingMoves(p,b);
                case "Queen":
                    return getQueenMoves(p,b);
                case "Bishop":
                    return getBishopMoves(p,b);
                case "Rook":
                    return getRookMoves(p,b);
                case "Knight":
                    return getKnightMoves(p,b);
            }
        }
        return null;
    }

    public ArrayList<int[]> getLegalMoves(Piece p) { 
        ArrayList<int[]> legalMoves = new ArrayList<>();
        ArrayList<int[]> validMoves = getValidMoves(p, board);
        ArrayList<int[]> opponentsNextMoves = new ArrayList<>();
        int tempRow = p.getRow();
        int tempColumn = p.getColumn();
        boolean inCheck = false;

        Piece[][] tempBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {                    
            for (int j = 0; j < 8; j++) {                
                tempBoard[i][j] = board[i][j];           
            }                                            
        }

        int validMoveCount = 0;
        while (validMoveCount < validMoves.size()) {

            for (int i = 0; i < 8; i++) {                    
                for (int j = 0; j < 8; j++) {                
                    tempBoard[i][j] = board[i][j];           
                }                                            
            }
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]] = p;                              //MOVES PIECE(P) ON TEMP BOARD
            tempBoard[tempRow][tempColumn] = null;                                                                            //TO THE CURRENT VALID MOVE
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setRow(validMoves.get(validMoveCount)[0]);
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setColumn(validMoves.get(validMoveCount)[1]);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) { 
                    if (tempBoard[i][j] != null && p.getColor() != tempBoard[i][j].getColor()) {
                        int tempMoveCount = 0;
                        while (tempMoveCount < getValidMoves(tempBoard[i][j], tempBoard).size()) {  //GETS ALL VALID MOVES FOR EACH OPPOSING PIECE ON THE BOARD
                            opponentsNextMoves.add(getValidMoves(tempBoard[i][j], tempBoard).get(tempMoveCount));
                            tempMoveCount++;
                        }
                    }
                }
            }

            int checkCount = 0;
            inCheck = false;
            while (checkCount < opponentsNextMoves.size()) {
                if (p.getColor() == Color.WHITE) {
                    if (opponentsNextMoves.get(checkCount)[0] == findWhiteKing(tempBoard)[0] && opponentsNextMoves.get(checkCount)[1] == findWhiteKing(tempBoard)[1]) {
                        inCheck = true;
                    }
                } else if (p.getColor() == Color.BLACK) {
                    if (opponentsNextMoves.get(checkCount)[0] == findBlackKing(tempBoard)[0] && opponentsNextMoves.get(checkCount)[1] == findBlackKing(tempBoard)[1]) {
                        inCheck = true;
                    }
                }
                checkCount++;
            }
            if (inCheck == false) {
                legalMoves.add(validMoves.get(validMoveCount));
            }

            opponentsNextMoves.clear();
            tempBoard[tempRow][tempColumn] = p;
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setRow(validMoves.get(validMoveCount)[0]);
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setColumn(validMoves.get(validMoveCount)[1]);
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[0]] = null;

            validMoveCount++;
        }
        p.setRow(tempRow);
        p.setColumn(tempColumn);
        return legalMoves;
    }
    public int[] findWhiteKing(Piece[][] board){
        int [] kingCoordinates = new int [2];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] != null && board[i][j].toString().equals("King") && board[i][j].getColor() == Color.WHITE){
                    kingCoordinates[0] = i;
                    kingCoordinates[1] = j;
                    return kingCoordinates;
                }
            }
        }
        return null;
    }

    public int[] findBlackKing(Piece[][] board){
        int [] kingCoordinates = new int [2];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] != null && board[i][j].toString().equals("King") && board[i][j].getColor() == Color.BLACK){
                    kingCoordinates[0] = i;
                    kingCoordinates[1] = j;
                    return kingCoordinates;
                }
            }
        }
        return null;
    }

    private ArrayList<int[]> getPawnMoves(Piece p, Piece [][] b) { 
        ArrayList<int[]> pawnMoves = new ArrayList<>();
        if (p != null) {
            if (p.getColor() == Color.WHITE) {
                if (p.getRow() != 0) {
                    if (p.getMoveCount() == 0  && b[p.getRow() - 1][p.getColumn()] == null && b[p.getRow() - 2][p.getColumn()] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - 1;
                        moves[1] = p.getColumn();
                        pawnMoves.add(moves);
                        int[] move = new int[2];
                        move[0] = p.getRow() - 2;
                        move[1] = p.getColumn();
                        pawnMoves.add(move);
                    }
                   if (b[p.getRow() - 1][p.getColumn()] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - 1;
                        moves[1] = p.getColumn();
                        pawnMoves.add(moves);
                    }
                    if (p.getColumn() != 0 && p.getColumn() != 7) {
                        if (b[p.getRow() - 1][p.getColumn() + 1] != null && b[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, RIGHT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() + 1;
                            pawnMoves.add(moves);
                        }
                        if (b[p.getRow() - 1][p.getColumn() - 1] != null && b[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, LEFT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() - 1;
                            pawnMoves.add(moves);
                        }
                    }
                   else if (p.getColumn() == 0) {
                        if (b[p.getRow() - 1][p.getColumn() + 1] != null && b[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, RIGHT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() + 1;
                            pawnMoves.add(moves);
                        }
                    }
                    else if (p.getColumn() == 7) {
                        if (b[p.getRow() - 1][p.getColumn() - 1] != null && b[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.BLACK) { //up one right one
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() - 1;
                            pawnMoves.add(moves);
                        }
                    }
                }
            }
            else if (p.getRow() != 7) {
                if (p.getMoveCount() == 0  && b[p.getRow() + 1][p.getColumn()] == null && b[p.getRow() + 2][p.getColumn()] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + 1;
                    moves[1] = p.getColumn();
                    pawnMoves.add(moves);
                    int[] move = new int[2];
                    move[0] = p.getRow() + 2;
                    move[1] = p.getColumn();
                    pawnMoves.add(move);
                }
                if (b[p.getRow() + 1][p.getColumn()] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + 1;
                    moves[1] = p.getColumn();
                    pawnMoves.add(moves);
                }
                if (p.getColumn() != 0 && p.getColumn() != 7) {
                    if (b[p.getRow() + 1][p.getColumn() + 1] != null && b[p.getRow() + 1][p.getColumn() + 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, RIGHT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() + 1;
                        pawnMoves.add(moves);
                    }
                    if (b[p.getRow() + 1][p.getColumn() - 1] != null && b[p.getRow() + 1][p.getColumn() - 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, LEFT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() - 1;
                        pawnMoves.add(moves);
                    }
                } 
                else if (p.getColumn() == 0) {
                    if (b[p.getRow() + 1][p.getColumn() + 1] != null && b[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, RIGHT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() + 1;
                        pawnMoves.add(moves);
                    }
                }
                else if (p.getColumn() == 7) {
                    if (b[p.getRow() + 1][p.getColumn() - 1] != null && b[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, LEFT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() - 1;
                        pawnMoves.add(moves);
                    }
                }
            }
            return pawnMoves;

        }
        return null;
    }
    private ArrayList<int[]> getKingMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> kingMoves = new ArrayList<>();
        if(p.getRow() == 0 && p.getColumn() == 0){
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        else if(p.getRow() == 0 && p.getColumn() == 7){
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        else if(p.getRow() == 7 && p.getColumn() == 7){
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }

        }
        else if(p.getRow() == 7 && p.getColumn() == 0){
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        else if(p.getRow() == 0){
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
        }
        else if(p.getColumn() == 7){
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        else if(p.getRow() == 7){
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
        }

            else if(p.getColumn() == 0){
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
             if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        else{
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
        }

        return kingMoves;
    }

   private ArrayList<int[]> getQueenMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> queenMoves = new ArrayList<>();

        if (p.getRow() != 0 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0) && (p.getColumn() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn() - i] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                else if (b[p.getRow() - i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                      
                }
                else if (b[p.getRow() - i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                      
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
       if (p.getRow() != 7 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7) && (p.getColumn() - i >= 0)) {
                if (b[p.getRow() + i][p.getColumn() - i] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                else if (b[p.getRow() + i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;                                                                                       
                }
                else if (b[p.getRow() + i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                       
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn() + i] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                else if (b[p.getRow() - i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                        
                }
                else if (b[p.getRow() - i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                        
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() + i <= 7)) {
                if (b[p.getRow() + i][p.getColumn() + i] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                else if (b[p.getRow() + i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;                                                                                         
                }
                else if (b[p.getRow() + i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                        
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn()] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                else if (b[p.getRow() - i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                      
                }
                else if (b[p.getRow() - i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;                                                                                      
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getRow() != 7) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7)) {
                if (b[p.getRow() + i][p.getColumn()] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                else if (b[p.getRow() + i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                     
                }
                else if (b[p.getRow() + i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) { 
                    hitPiece = true;                                                                                       
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                i++;
            }
        }
       if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7)) {
                if (b[p.getRow()][p.getColumn() + i] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                else if (b[p.getRow()][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                }
                else if (b[p.getRow()][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() - i >= 0)) {
                if (b[p.getRow()][p.getColumn() - i] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                else if (b[p.getRow()][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                }
                else if (b[p.getRow()][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        return queenMoves;
    }

    private ArrayList<int[]> getBishopMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> bishopMoves = new ArrayList<>();
        if(p != null){
           if (p.getRow() != 0 && p.getColumn() != 0) {
                int i = 1;
                boolean hitPiece = false;
                while (hitPiece == false && (p.getRow() - i >= 0) && (p.getColumn() - i >= 0)) {
                    if (b[p.getRow() - i][p.getColumn() - i] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - i;
                        moves[1] = p.getColumn() - i;
                        bishopMoves.add(moves);
                    }
                    else if (b[p.getRow() - i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                    }
                    else if (b[p.getRow() - i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - i;
                        moves[1] = p.getColumn() - i;
                        bishopMoves.add(moves);
                    }
                    i++;
                }
            } if (p.getRow() != 7 && p.getColumn() != 0) {
                int i = 1;
                boolean hitPiece = false;
                while (hitPiece == false && (p.getRow() + i <= 7) && (p.getColumn() - i >= 0)) {
                    if (b[p.getRow() + i][p.getColumn() - i] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + i;
                        moves[1] = p.getColumn() - i;
                        bishopMoves.add(moves);
                    }
                    else if (b[p.getRow() + i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                    }
                    else if (b[p.getRow() + i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + i;
                        moves[1] = p.getColumn() - i;
                        bishopMoves.add(moves);
                    }
                    i++;
                }
            }if (p.getColumn() != 0) {
                int i = 1;
                boolean hitPiece = false;
                while (hitPiece == false && (p.getColumn() + i < 7) && (p.getRow() - i > 0)) {
                    if (b[p.getRow() - i][p.getColumn() + i] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - i;
                        moves[1] = p.getColumn() + i;
                        bishopMoves.add(moves);
                    }
                    else if (b[p.getRow() - i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                    }
                    else if (b[p.getRow() - i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - i;
                        moves[1] = p.getColumn() + i;
                        bishopMoves.add(moves);
                    }
                    i++;
                }
            }
            if (p.getColumn() != 0) {
                int i = 1;
                boolean hitPiece = false;
                while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() + i <= 7)) {
                    if (b[p.getRow() + i][p.getColumn() + i] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + i;
                        moves[1] = p.getColumn() + i;
                        bishopMoves.add(moves);
                    }
                    else if (b[p.getRow() + i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                    }
                    else if (b[p.getRow() + i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                        hitPiece = true;
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + i;
                        moves[1] = p.getColumn() + i;
                        bishopMoves.add(moves);
                    }
                    i++;
                }
            }
        }
        return bishopMoves;
    }

    private ArrayList<int[]> getRookMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> rookMoves = new ArrayList<>();

        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn()] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                }
                else if (b[p.getRow() - i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                }
                else if (b[p.getRow() - i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getRow() != 7) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7)) {
                if (b[p.getRow() + i][p.getColumn()] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                }
                else if (b[p.getRow() + i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                }
                else if (b[p.getRow() + i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7)) {
                if (b[p.getRow()][p.getColumn() + i] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    rookMoves.add(moves);
                }
                else if (b[p.getRow()][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                }
                else if (b[p.getRow()][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() - i >= 0)) {
                if (b[p.getRow()][p.getColumn() - i] == null) {
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    rookMoves.add(moves);
                }
                else if (b[p.getRow()][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                }
                else if (b[p.getRow()][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) {
                    hitPiece = true;
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    rookMoves.add(moves);
                }
                i++;
            }
        }

        return rookMoves;
    }

    private ArrayList<int[]> getKnightMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> knightMoves = new ArrayList<>();
        if(p.getRow() + 2 <= 7 && p.getColumn() + 1 <= 7){
            if(b[p.getRow() + 2][p.getColumn() + 1] == null || p.getColor() != b[p.getRow() + 2][p.getColumn() + 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 2;
                moves[1] = p.getColumn() + 1;
                knightMoves.add(moves);
            }
        }
        if(p.getRow() + 1 <= 7 && p.getColumn() + 2 <= 7){
            if(b[p.getRow() + 1][p.getColumn() + 2] == null || p.getColor() != b[p.getRow() + 1][p.getColumn() + 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 2;
                knightMoves.add(moves);
            }
        }
        if(p.getRow() - 1 >= 0 && p.getColumn() + 2 <= 7){
            if(b[p.getRow() - 1][p.getColumn() + 2] == null || p.getColor() != b[p.getRow() - 1][p.getColumn() + 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 2;
                knightMoves.add(moves);
            }
        }
        if(p.getRow() - 2 >= 0 && p.getColumn() + 1 <= 7){
            if(b[p.getRow() - 2][p.getColumn() + 1] == null || p.getColor() != b[p.getRow() - 2][p.getColumn() + 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 2;
                moves[1] = p.getColumn() + 1;
                knightMoves.add(moves);
            }
        }
       if(p.getRow() - 2 >= 0 && p.getColumn() - 1 >= 0){
            if(b[p.getRow() - 2][p.getColumn() - 1] == null || p.getColor() != b[p.getRow() - 2][p.getColumn() - 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 2;
                moves[1] = p.getColumn() - 1;
                knightMoves.add(moves);
            }
        }
        if(p.getRow() - 1 >= 0 && p.getColumn() - 2 >= 0){
            if(b[p.getRow() - 1][p.getColumn() - 2] == null || p.getColor() != b[p.getRow() - 1][p.getColumn() - 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 2;
                knightMoves.add(moves);
            }
        }
        if(p.getRow() + 1 <= 7 && p.getColumn() - 2 >= 0){
            if(b[p.getRow() + 1][p.getColumn() - 2] == null || p.getColor() != b[p.getRow() + 1][p.getColumn() - 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 2;
                knightMoves.add(moves);
            }
        }
        if(p.getRow() + 2 <= 7 && p.getColumn() - 1 >= 0){
            if(b[p.getRow() + 2][p.getColumn() - 1] == null || p.getColor() != b[p.getRow() + 2][p.getColumn() - 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 2;
                moves[1] = p.getColumn() - 1;
                knightMoves.add(moves);
            }
        }
        return knightMoves;
    }

    public void promotePawn(Piece p, String newPiece) {
        int tempRow;
        int tempColumn;
        Color tempColor;
        switch (newPiece) {
            case "Queen":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Queen(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;
            case "Rook":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Rook(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;
            case "Bishop":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Bishop(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;
            case "Knight":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Knight(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;

        }
    }

    public boolean isWinner() {
        if(getWinner().equals("White") || getWinner().equals("Black")){
            return true;
        }
        return false;
    }

    public String getWinner() {
        if (inCheck(Color.BLACK) == true && getLegalMoves(board[findBlackKing(board)[0]][findBlackKing(board)[1]]) == null) {
            return "White has won the game";
        }
        else if (inCheck(Color.WHITE) == true && getLegalMoves(board[findWhiteKing(board)[0]][findWhiteKing(board)[1]]) == null) {
            return "Black has won the game";
        }
        if(findBlackKing(board) == null){
            return "White has won the game";
        }
        if(findWhiteKing(board) == null){
            return "Black has won the game";
        }

        return "Equal game";
    }

    public ArrayList<Move> getMoves(){
        return moves;
    }

    public Piece getPiece(int row, int column){
        return board[row][column];
    }

    public String whoseTurn(){
        if(turnCount % 2 == 0){
            return "White";
        }
        else{
            return "Black";
        }
    }

    public boolean inCheck(Color c){
        ArrayList<int[]> opponentsNextMoves = new ArrayList<>();
        boolean inCheck = false;

        if(c == Color.BLACK){
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null && board[i][j].getColor() != Color.BLACK) {
                        int tempMoveCount = 0;
                        while (tempMoveCount < getValidMoves(board[i][j], board).size()) {
                            opponentsNextMoves.add(getValidMoves(board[i][j], board).get(tempMoveCount));
                            tempMoveCount++;
                        }
                    }
                }
            }
        }
        else if(c == Color.WHITE){
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null && board[i][j].getColor() != Color.WHITE) {
                        int tempMoveCount = 0;
                        while (tempMoveCount < getValidMoves(board[i][j], board).size()) {
                            opponentsNextMoves.add(getValidMoves(board[i][j], board).get(tempMoveCount));
                            tempMoveCount++;
                        }
                    }
                }
            }
        }
        int i = 0;
        while( i < opponentsNextMoves.size()){
            if (c == Color.WHITE) {
                if (findWhiteKing(board) != null) {
                    if (opponentsNextMoves.get(i)[0] == findWhiteKing(board)[0] && opponentsNextMoves.get(i)[1] == findWhiteKing(board)[1]) {
                        inCheck = true;
                    }
                }
            }
            else if (c == Color.BLACK) {
                if (findBlackKing(board) != null) {
                    if (opponentsNextMoves.get(i)[0] == findBlackKing(board)[0] && opponentsNextMoves.get(i)[1] == findBlackKing(board)[1]) {
                        inCheck = true;
                    }
                }
            }
            i++;
        }
        return inCheck;
    }
}

