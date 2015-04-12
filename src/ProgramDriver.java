import java.util.Scanner;

public class ProgramDriver {
    
    public static void main(String[] args) {
        System.out.println("Ganji-Ho");
        System.out.println("Created by Gary Chang - 29368841");
        
        Scanner reader = new Scanner(System.in);
        
        System.out.println("\nChoose a game mode:");
        System.out.println("\t1 - Player vs Computer");
        System.out.println("\t2 - Computer vs Player");
        System.out.println("\t3 - Player vs Player");
        System.out.print("Choice: ");
        String input = reader.nextLine();
        
        int option = 0;
        try {
            option = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            System.out.println("Input Error. Please restart.");
        }
        
        if(option == 1) {
            onePlayerGameTypeA();
        } else if(option == 2) {
            onePlayerGameTypeB();
        } else if(option == 3) {
            twoPlayerGame();
        }
        
        reader.close();
        System.out.println("\nThank you for playing!");
        
    }

    private static void onePlayerGameTypeA() {
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        
        Computer computer = new Computer(Color.BLACK);
        
        System.out.println(board);
        
        boolean whiteCanPlay = board.getWhitePlaceableCells().size() > 0; 
        boolean blackCanPlay = board.getBlackPlaceableCells().size() > 0;
        while(whiteCanPlay && blackCanPlay) {

            // White player's turn
            board.printWhitePlaceableCells();
            whiteCanPlay = humanTurn(board, Color.WHITE, reader);
            if(!whiteCanPlay) {
                System.out.println("WHITE ran out of moves! BLACK wins!");
                break;
            }
            System.out.println(board);

            // Black player's turn
            board.printBlackPlaceableCells();
            blackCanPlay = computerTurn(computer, board);
            if(!blackCanPlay) {
                System.out.println("BLACK ran out of moves! WHITE wins!");
                break;                   
            }
            System.out.println(board);
        }
        reader.close();
    }
    
    
    private static boolean humanTurn(Board board, Color color, Scanner reader) {
        boolean canPlay = board.getPlaceableCells(color).size() > 0;
        if(canPlay) {
            boolean validMove = false;
            while(!validMove) {
                System.out.print(color.toString() + "'s move: ");
                String input = reader.nextLine();
                validMove = board.place(input, color);
            }
        }
        return canPlay;
    }
    
    private static boolean computerTurn(Computer computer, Board board) {
        boolean canPlay = board.getPlaceableCells(computer.playerColor).size() > 0;
        if(canPlay) {
            computer.readBoard(board);
            try {
                computer.playOnBoard(board);
            } catch(BadMoveException e) {
                System.out.println(e.getMessage());
                System.out.println("Aborting program...");
                System.exit(0);
            }
        }
        return canPlay;
    }
    
    private static void onePlayerGameTypeB() {
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        
        Computer computer = new Computer(Color.WHITE);
        
        System.out.println(board);
        
        boolean whiteCanPlay = board.getWhitePlaceableCells().size() > 0; 
        boolean blackCanPlay = board.getBlackPlaceableCells().size() > 0;
        while(whiteCanPlay && blackCanPlay) {
            // White player's turn
            board.printWhitePlaceableCells();
            whiteCanPlay = computerTurn(computer, board);
            if(!whiteCanPlay) {
                System.out.println("WHITE ran out of moves! BLACK wins!");
                break;
            }
            System.out.println(board);

            // Black player's turn
            board.printBlackPlaceableCells();
            blackCanPlay = humanTurn(board, Color.BLACK, reader);
            if(!blackCanPlay) {
                System.out.println("BLACK ran out of moves! WHITE wins!");
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
            whiteCanPlay = humanTurn(board, Color.WHITE, reader);
            if(!whiteCanPlay) {
                System.out.println("WHITE ran out of moves! BLACK wins!");
                break;
            }
            System.out.println(board);

            // Black player's turn
            board.printBlackPlaceableCells();
            blackCanPlay = humanTurn(board, Color.BLACK, reader);
            if(!blackCanPlay) {
                System.out.println("BLACK ran out of moves! WHITE wins!");
                break;                   
            }
            System.out.println(board);
        }
        reader.close();
    }

}
