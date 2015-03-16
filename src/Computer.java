import java.util.ArrayList;
import java.util.Collections;


public class Computer {

    public enum Color {WHITE, BLACK}
    Color playerColor;
    int maxDepth;
    BoardNode root;
    
    ArrayList<ArrayList<BoardNode>> allNodes;
    
    public Computer(Color c) {
        playerColor = c;
        maxDepth = 3;
        root = null;
        
        // Instantiate all levels
        allNodes = new ArrayList<ArrayList<BoardNode>>();
        for(int i = 0; i <= maxDepth; i++) {
            allNodes.add(new ArrayList<BoardNode>());
        }
    }
    
    public void readBoard(Board b) {
        
        if(root == null) {
            // First time - evaluate all children
            root = new BoardNode(b, null);
            allNodes.get(0).add(root);
            
            // Recursive call to get children of all children (until max depth)
            generateChildren(root, 1, playerColor);

            // Sort all
            for(ArrayList<BoardNode> list : allNodes) {
                Collections.sort(list);
            }
            
        } else {
            // Find the current board in children
            int index = allNodes.get(0).indexOf(b);
            
            // Make that board the new root
            
            // Evaluate the depth+1 level
        }
        
        // Get list of possible moves
        // Evaluate heuristic for each
        
        // Create children of possible moves
        // Evaluate heuristic for each
        
        // 
    }
    
    public String getBestMove(Board b) {
        Cell lastMove = allNodes.get(1).get(0).lastMove;
        return b.getCellPositionString(lastMove.getColumn(), lastMove.getRow());
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
    
    // Recursive algorithm to populate all children nodes
    public void generateChildren(BoardNode parentNode, int depth, Color playerTurnColor) {
        
        if(depth > this.maxDepth) {
            return;
        }
        
        ArrayList<BoardNode> nodeList = allNodes.get(depth);
        if(playerTurnColor == Color.BLACK) {
            ArrayList<Cell> blackMoves = parentNode.getBoard().getBlackPlaceableCells();
            
            for(Cell c : blackMoves) {
                Board possibleBoard = new Board(parentNode.getBoard());
                possibleBoard.placeBlack(c.col, c.row);
                
                BoardNode possibleBoardNode = new BoardNode(possibleBoard, c);
                evaluateNode(possibleBoardNode);
                possibleBoardNode.setParent(parentNode);
                nodeList.add(possibleBoardNode);
                
                generateChildren(possibleBoardNode, depth + 1, Color.WHITE);
            }
        } else if(playerTurnColor == Color.WHITE) {
            ArrayList<Cell> whiteMoves = parentNode.getBoard().getWhitePlaceableCells();
            
            for(Cell c : whiteMoves) {
                Board possibleBoard = new Board(parentNode.getBoard());
                possibleBoard.placeWhite(c.col, c.row);
                
                BoardNode possibleBoardNode = new BoardNode(possibleBoard, c);
                evaluateNode(possibleBoardNode);
                possibleBoardNode.setParent(parentNode);
                nodeList.add(possibleBoardNode);
                
                generateChildren(possibleBoardNode, depth + 1, Color.BLACK);
            }
        }
    }
    
}
