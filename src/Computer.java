import java.util.ArrayList;
import java.util.Collections;


public class Computer {

    public enum Color {WHITE, BLACK}
    Color playerColor;
    int maxDepth;
    BoardNode root;
    private ArrayList<BoardNode> leaves;

    public Computer(Color c) {
        playerColor = c;
        maxDepth = 3;
        root = null;
        leaves = new ArrayList<BoardNode>();
    }
    
    public void readBoard(Board b) {
        root = new BoardNode(b, null);
        leaves.clear();
        
        // Recursive call to get children of all children (until max depth)
        generateChildren(root, 1, playerColor);
        evaluateTree();
    }
    
    public String getBestMove(Board b) {
        
        // Assume children are sorted
        Cell lastMove = root.findBestChildBoard().getBoard().getLastMove();
        return b.getCellPositionString(lastMove.col, lastMove.row);
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

        int whiteMoves = node.getBoard().getWhitePlaceableCells().size();
        int blackMoves = node.getBoard().getBlackPlaceableCells().size(); 
        
        int heuristic = 0;
        switch(playerColor) {
        case WHITE:
            heuristic = blackMoves == 0 ? 9999 : whiteMoves - blackMoves;
            break;
        case BLACK:
            heuristic = whiteMoves == 0 ? 9999 : blackMoves - whiteMoves;
            break;
        default:
            break;
        }
        node.setHeuristicValue(heuristic);
    }
    
    public void resetHeuristics(BoardNode root) {
        root.setHeuristicValue(null);
        for(BoardNode child : root.getChildren()) {
            resetHeuristics(child);
        }
    }
    
    public void evaluateTree() {
        resetHeuristics(root);
        
        for(BoardNode current : leaves) {
            evaluateNode(current);
            minimaxPropagation(current);
        }
    }
    
    // Recursively propagate values up the tree
    public void minimaxPropagation(BoardNode node) {
        if(node == root) {
            return;
        }
        
        Cell lastMove = node.getBoard().getLastMove();
        int color = node.getBoard().readCell(lastMove.col, lastMove.row);
        BoardNode parentNode = node.getParent();
        
        Integer parentHeuristic = parentNode.getHeuristicValue();
        Integer currentHeuristic = node.getHeuristicValue();
        
        if((color == 1 && playerColor == Color.WHITE) || color == 2 && playerColor == Color.BLACK) {
            // If it was the computer that played - Maximize
            if(parentHeuristic == null || parentHeuristic < currentHeuristic) {
                parentNode.setHeuristicValue(currentHeuristic);
                minimaxPropagation(parentNode);
            }
        } else {
            // If it was the opponent who played - Minimize
            if(parentHeuristic == null || parentHeuristic > currentHeuristic) {
                parentNode.setHeuristicValue(currentHeuristic);
                minimaxPropagation(parentNode);
            }
        }
    }
    
    // Recursive algorithm to populate all children nodes of a node
    public void generateChildren(BoardNode parentNode, int depth, Color playerTurnColor) {
        
        if(depth > this.maxDepth) {
            // Keep track of every leaf
            leaves.add(parentNode);
            return;
        }
        
        if(playerTurnColor == Color.BLACK) {
            ArrayList<Cell> blackMoves = parentNode.getBoard().getBlackPlaceableCells();
            
            if(blackMoves.size() == 0) {
                leaves.add(parentNode);
                return;
            }
            
            for(Cell c : blackMoves) {
                Board possibleBoard = new Board(parentNode.getBoard());
                possibleBoard.placeBlack(c.col, c.row);
                
                BoardNode possibleBoardNode = new BoardNode(possibleBoard, c);
                
                if(!parentNode.hasChild(possibleBoardNode)) {
                    parentNode.addChild(possibleBoardNode);
                }
                
                generateChildren(possibleBoardNode, depth + 1, Color.WHITE);
            }
        } else if(playerTurnColor == Color.WHITE) {
            ArrayList<Cell> whiteMoves = parentNode.getBoard().getWhitePlaceableCells();
            
            if(whiteMoves.size() == 0) {
                leaves.add(parentNode);
                return;
            }
            
            for(Cell c : whiteMoves) {
                Board possibleBoard = new Board(parentNode.getBoard());
                possibleBoard.placeWhite(c.col, c.row);
                
                BoardNode possibleBoardNode = new BoardNode(possibleBoard, c);
                
                if(!parentNode.hasChild(possibleBoardNode)) {
                    parentNode.addChild(possibleBoardNode);
                }
                
                generateChildren(possibleBoardNode, depth + 1, Color.BLACK);
            }
        }
    }
}
