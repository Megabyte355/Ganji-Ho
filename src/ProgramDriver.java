import java.util.Scanner;

public class ProgramDriver {
    
    public static void main(String[] args) {
        System.out.println("Ganji-Ho");
        System.out.println("Created by Gary Chang - 29368841");
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println("\nChoose a game mode:");
        System.out.println("\t1 - Player vs Computer");
        System.out.println("\t2 - Player vs Player");
        System.out.print("Choice: ");
        String input = reader.nextLine();
        
        int option = 0;
        try {
            option = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            System.out.println("Input Error. Please restart.");
        }
        
        if(option == 1) {
            onePlayerGame();
        } else if(option == 2) {
            twoPlayerGame();
        }
        
        reader.close();
        System.out.println("\nThank you for playing!");
        
    }

    private static void onePlayerGame() {
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        
        Computer computer = new Computer(Computer.Color.BLACK);
        
        System.out.println(board);
        
        boolean whiteCanPlay = board.getWhitePlaceableCells().size() > 0; 
        boolean blackCanPlay = board.getBlackPlaceableCells().size() > 0;
        while(whiteCanPlay && blackCanPlay) {

            // White player's turn
            board.printWhitePlaceableCells();
            whiteCanPlay = board.getWhitePlaceableCells().size() > 0;
            if(whiteCanPlay) {
                boolean validMove = false;
                while(!validMove) {
                    System.out.print("White's move: ");
                    String input = reader.nextLine();
                    validMove = board.placeWhite(input);
                }
            } else {
                System.out.println("White ran out of moves! Black wins!");
                break;
            }
            System.out.println(board);

            // Black player's turn
            board.printBlackPlaceableCells();
            blackCanPlay = board.getBlackPlaceableCells().size() > 0;
            if(blackCanPlay) {
                
                computer.readBoard(board);
                try {
                    computer.playBestMove(board);
                } catch(BadMoveException e) {
                    return;
                }
                
                
//                
//                
//                System.out.print("Black's move: " + move);
//                if(!board.placeBlack(move)) {
//                    System.out.println("Invalid move attempted by computer! Aborting game...");
//                    return;
//                }
                
//                System.out.println("test");
//                boolean validMove = false;
//                while(!validMove) {
//                    System.out.print("Black's move: ");
//                    String input = reader.nextLine();
//                    validMove = board.placeBlack(input);
//                }
            } else {
                System.out.println("Black ran out of moves! White wins!");
                break;
            }
            System.out.println(board);
        }
        reader.close();
    }
    
    private static void twoPlayerGame() {
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        
        System.out.println(board);
        
        boolean whiteCanPlay = board.getWhitePlaceableCells().size() > 0; 
        boolean blackCanPlay = board.getBlackPlaceableCells().size() > 0;
        while(whiteCanPlay && blackCanPlay) {

            // White player's turn
            board.printWhitePlaceableCells();
            whiteCanPlay = board.getWhitePlaceableCells().size() > 0;
            if(whiteCanPlay) {
                boolean validMove = false;
                while(!validMove) {
                    System.out.print("White's move: ");
                    String input = reader.nextLine();
                    validMove = board.placeWhite(input);
                }
            } else {
                System.out.println("White ran out of moves! Black wins!");
                break;
            }
            System.out.println(board);

            // Black player's turn
            board.printBlackPlaceableCells();
            blackCanPlay = board.getBlackPlaceableCells().size() > 0;
            if(blackCanPlay) {
                boolean validMove = false;
                while(!validMove) {
                    System.out.print("Black's move: ");
                    String input = reader.nextLine();
                    validMove = board.placeBlack(input);
                }
            } else {
                System.out.println("Black ran out of moves! White wins!");
                break;
            }
            System.out.println(board);
        }
        reader.close();
    }

}
