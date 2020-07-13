package Game;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {

            Board b = new Board();
            Scanner command = new Scanner(System.in);
            boolean win = false;
            while (b.isWinner() == false && win == false) {
                b.printBoard();
                System.out.println("It's " + b.whoseTurn() + "'s turn");
                int row = command.nextInt();
                int column = command.nextInt();
                int newRow = command.nextInt();
                int newColumn = command.nextInt();
                if (row != -1 && column != -1 && newRow != -1 && newColumn != -1) {
                    if ((row >= 0 && row <= 7) && (column >= 0 && column <= 7) && (newRow >= 0 && newRow <= 7) && (newColumn >= 0 && newColumn <= 7)) {
                        if (b.getPiece(row, column) != null) {
                            b.movePiece(b.getPiece(row, column), newRow, newColumn);
                        }
                        else{
                            System.out.println("There is no piece at [" + row + "," + column + "]");
                        }
                    } else {
                        System.out.println("Outside the board.\n");
                    }
                } else {
                    win = true;
                }
                System.out.println("");
                System.out.println("Board:");
                int i = 0;
                while (i < b.getMoves().size()) {
                    System.out.println(b.getMoves().get(i));
                    i++;
                }
                System.out.println("");
            }
            System.out.print(b.getWinner());
        }

}
