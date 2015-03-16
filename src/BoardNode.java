import java.util.ArrayList;
import java.util.Collections;

public class BoardNode implements Comparable<BoardNode> {
    Board board;
    int heuristic;
    Cell lastMove;
    
    private BoardNode parent;
    private ArrayList<BoardNode> children;
    
    public BoardNode(Board b, Cell move) {
        parent = null;
        board = b;
        heuristic = 0;
        lastMove = (move != null) ? new Cell(move) : null;
        
        children = new ArrayList<BoardNode>();
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setHeuristicValue(int h) {
        heuristic = h;
    }
    
    public int getHeuristicValue() {
        return heuristic;
    }
    
    public void addChild(BoardNode b) {
        // TODO: Do we really need this?
        
//        b.parent = this;
//        children.add(b);
//        Collections.sort(children);
    }

    public void setParent(BoardNode p) {
        parent = p;
    }
    
    public BoardNode getParent() {
        return parent;
    }
    
    public BoardNode findMinChildBoard() {
        // TODO
//        
//        if(children.size() == 0) {
//            return null;
//        }
//        
//        // Assume children are sorted already
//        return children.get(0);
        
        return null;
    }
    
    public ArrayList<BoardNode> findAllMinChildBoards() {
        // TODO
        
//        if(children.size() == 0) {
//            return null;
//        }
//        ArrayList<BoardNode> results = new ArrayList<BoardNode>();
//        BoardNode minBoard = children.get(0);
//        int minHeuristic = minBoard.heuristic;
//        results.add(minBoard);
//        
//        // Assume children are sorted already
//        for(BoardNode b : children) {
//            if(b.heuristic == minHeuristic) {
//                results.add(b);
//            } else {
//                break;
//            }
//        }
//        return results;
        
        return null;
    }
    
    // Puts worst boards first, best last
    public int compareTo(BoardNode other) {
        if(heuristic < other.heuristic) {
            return -1;
        } else if (heuristic < other.heuristic) {
            return 1;
        }
        return 0;
    }
    
    public boolean equals(BoardNode b) {
        for(int col = 0; col < board.cells.length; col++) {
            for(int row = 0; row < board.cells.length; row++) {
                if(board.cells[col][row].equals(b.board.cells[col][row])) {
                    return false;
                }
            }
        }
        return true;
    }
}
