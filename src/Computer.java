import java.util.ArrayList;
import java.util.Collections;


public class Computer {

    public enum Color {WHITE, BLACK}
    Color playerColor;
    int maxDepth;
    BoardNode root;

    public Computer(Color c) {
        playerColor = c;
        maxDepth = 3;
        root = null;
    }
    
    public void readBoard(Board b) {
        
        if(root == null) {
            // First time - evaluate all children
            root = new BoardNode(b, null);
            
            // Recursive call to get children of all children (until max depth)
            generateChildren(root, 1, playerColor);
            
        } else {
            
            // Find the current board in children
            BoardNode childBoard = root.getBoardNodeInChildren(b);
            root = childBoard;
            
            // for n=3 only (TO BE CHANGED LATER)
            generateChildren(root, 1, playerColor);
        }
    }
    
    public String getBestMove(Board b) {
        
        // Assume children are sorted
        Cell lastMove = root.findBestChildBoard().getBoard().getLastMove();
        return b.getCellPositionString(lastMove.getColumn(), lastMove.getRow());
    }
    
    public void playOnBoard(Board board) throws BadMoveException {
        
        // Assume children are sorted
        BoardNode bestBoardNode = root.findBestChildBoard();
        Cell lastMove = bestBoardNode.getBoard().getLastMove();
        String choice = board.getCellPositionString(lastMove.col, lastMove.row);
        System.out.println("Computer decides to play at \"" + choice + "\".");
        System.out.println("Heuristic value: " + bestBoardNode.heuristic);
        
        boolean success = false;
        switch(playerColor) {
        case WHITE:
            success = board.placeWhite(lastMove.col, lastMove.row);
            break;
        case BLACK:
            success = board.placeBlack(lastMove.col, lastMove.row);
            break;
        default:
            break;
        }
        
        if(!success) {
            String moveStr = board.getCellPositionString(lastMove.col, lastMove.row);
            throw new BadMoveException("Invalid move attempted by Computer at \"" + moveStr + "\".");
        }
        
        // Update the board
        root = bestBoardNode;
    }
    
    // Simple heuristic to evaluate a board for now    
    public void evaluateNode(BoardNode node) {

        ArrayList<Cell> whiteMoves = node.getBoard().getWhitePlaceableCells();
        ArrayList<Cell> blackMoves = node.getBoard().getBlackPlaceableCells();
        
        int heuristic = 0;
        switch(playerColor) {
        case WHITE:
            heuristic = whiteMoves.size() - blackMoves.size();
            break;
        case BLACK:
            heuristic = blackMoves.size() - whiteMoves.size();
            break;
        default:
            break;
        }
        
        node.setHeuristicValue(heuristic);
    }
    
    // Recursive algorithm to populate all children nodes of a node
    public void generateChildren(BoardNode parentNode, int depth, Color playerTurnColor) {
        
        if(depth > this.maxDepth) {
            return;
        }
        
        if(playerTurnColor == Color.BLACK) {
            ArrayList<Cell> blackMoves = parentNode.getBoard().getBlackPlaceableCells();
            
            for(Cell c : blackMoves) {
                Board possibleBoard = new Board(parentNode.getBoard());
                possibleBoard.placeBlack(c.col, c.row);
                
                BoardNode possibleBoardNode = new BoardNode(possibleBoard, c);
                evaluateNode(possibleBoardNode);
                parentNode.addChild(possibleBoardNode);
                
                generateChildren(possibleBoardNode, depth + 1, Color.WHITE);
            }
        } else if(playerTurnColor == Color.WHITE) {
            ArrayList<Cell> whiteMoves = parentNode.getBoard().getWhitePlaceableCells();
            
            for(Cell c : whiteMoves) {
                Board possibleBoard = new Board(parentNode.getBoard());
                possibleBoard.placeWhite(c.col, c.row);
                
                BoardNode possibleBoardNode = new BoardNode(possibleBoard, c);
                evaluateNode(possibleBoardNode);
                parentNode.addChild(possibleBoardNode);
                
                generateChildren(possibleBoardNode, depth + 1, Color.BLACK);
            }
        }
    }
    
}
