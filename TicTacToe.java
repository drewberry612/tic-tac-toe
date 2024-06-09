// RegNo. 2000878

import java.util.*;

public class TicTacToe {

    public static void main(String[] args) {
        AIplayer AI = new AIplayer();
        Board b = new Board();
        Point p = new Point(0, 0);
        Random rand = new Random();
        
        b.displayBoard();

        System.out.println("\nWho makes first move? (1)Computer (2)User: ");
        int choice = b.scan.nextInt();
        if (choice == 1) { // Makes computers first move
            AI.callMinimax(0, 1, b);
            System.out.println("\nComputer's Move:");
            b.placeAMove(AI.returnBestMove(), 1); 
            b.displayBoard();
        }
        
        while (!b.isGameOver()) { // Gameplay loop
            // User's turn
            System.out.println("\nYour move: line (1, 2, 3, 4 or 5) column (1, 2, 3, 4 or 5)");
            Point userMove = new Point(b.scan.nextInt()-1, b.scan.nextInt()-1);
            while (b.getState(userMove) != 0) {
                System.out.println("Invalid move. Make your move again: ");
                userMove.x=b.scan.nextInt()-1;
                userMove.y=b.scan.nextInt()-1;
            }
            b.placeAMove(userMove, 2);
            b.displayBoard();
            
            if (b.isGameOver()) {
                break;
            } 

            // Computer's turn
            AI.callMinimax(0, 1, b);
            System.out.println("\nComputer's Move:");
            b.placeAMove(AI.returnBestMove(), 1); 
            b.displayBoard();
        }
        if (b.hasXWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasOWon()) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }    
}