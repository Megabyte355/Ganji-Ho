import java.util.ArrayList;
import java.util.Collections;

public class BoardNode implements Comparable<BoardNode> {
    Board board;
    int heuristic;
    
    BoardNode parent;
    ArrayList<BoardNode> children;
    
    public BoardNode(Board b) {
        parent = null;
        board = b;
        heuristic = 0;
        
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
        b.parent = this;
        children.add(b);
        Collections.sort(children);
    }
    
    public BoardNode findMinChildBoard() {
        
        if(children.size() == 0) {
            return null;
        }
        
        // Assume children are sorted already
        return children.get(0);
    }
    
    public ArrayList<BoardNode> findAllMinChildBoards() {
        if(children.size() == 0) {
            return null;
        }
        ArrayList<BoardNode> results = new ArrayList<BoardNode>();
        BoardNode minBoard = children.get(0);
        int minHeuristic = minBoard.heuristic;
        results.add(minBoard);
        
        // Assume children are sorted already
        for(BoardNode b : children) {
            if(b.heuristic == minHeuristic) {
                results.add(b);
            } else {
                break;
            }
        }
        return results;
    }
    
    public int compareTo(BoardNode other) {
        if(heuristic < other.heuristic) {
            return -1;
        } else if (heuristic < other.heuristic) {
            return 1;
        }
        return 0;
    }
    
    public boolean equals(BoardNode b) {
        boolean result = true;
        for(int col = 0; col < board.cells.length; col++) {
            for(int row = 0; row < board.cells.length; row++) {
                if(!board.cells[col][row].equals(b.board.cells[col][row])) {
                    return false;
                }
            }
        }
        return true;
    }
}
