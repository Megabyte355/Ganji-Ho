import java.util.ArrayList;


public class Computer {

    public enum Color {WHITE, BLACK}
    Color playerColor;
    int depth;
    BoardNode root;
    
    ArrayList<ArrayList<BoardNode>> allNodes;
    
    public Computer(Color c) {
        playerColor = c;
        depth = 3;
        root = null;
        
        // Instantiate all levels
        allNodes = new ArrayList<ArrayList<BoardNode>>();
        for(int i = 0; i < depth; i++) {
            allNodes.add(new ArrayList<BoardNode>());
        }
    }
    
    public void readBoard(Board b) {
        
        if(root == null) {
            // First time - evaluate all children
            root = new BoardNode(b);
            
            // Recursive call to get children of all children (until max depth)
//            ArrayList<Cell> blackMoves = b.getBlackPlaceableCells();
//            
//            for(Cell c : blackMoves) {
//                Board possibleBoard = new Board(b);
//                
//            }
            
        } else {
            // Find the current board in children
            
            // Make that board the new root
            
            // Evaluate the depth+1 level
        }
        
        // Get list of possible moves
        // Evaluate heuristic for each
        
        // Create children of possible moves
        // Evaluate heuristic for each
        
        // 
    }
    
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
    
    // Recursive
    public void generateChildren(BoardNode n, int depth) {
        
    }

}
