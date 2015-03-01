import java.util.Scanner;


public class ProgramDriver {
    
    public static void main(String[] args) {
        printWelcomeMessage();
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        
        System.out.println(board);
        
        boolean whiteCanPlay = board.getWhitePlaceableCells().size() > 0; 
        boolean blackCanPlay = board.getBlackPlaceableCells().size() > 0;
        while(whiteCanPlay && blackCanPlay) {

            // White player's turn
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
        
        System.out.println("\nThank you for playing!");
        reader.close();
        
    }
    
    private static void printWelcomeMessage() {
        System.out.println("Ganji-Ho");
        System.out.println("Created by Gary Chang - 29368841\n");
    }

}
