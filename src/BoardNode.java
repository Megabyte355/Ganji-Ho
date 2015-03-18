import java.util.ArrayList;
import java.util.Collections;

public class BoardNode implements Comparable<BoardNode> {
    Board board;
    Integer heuristic;
    
    private BoardNode parent;
    private ArrayList<BoardNode> children;
    
    public BoardNode(Board b, Cell move) {
        parent = null;
        board = b;
        heuristic = null;
        
        children = new ArrayList<BoardNode>();
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setHeuristicValue(Integer h) {
        heuristic = h;
    }
    
    public Integer getHeuristicValue() {
        return heuristic;
    }
    
    public void addChild(BoardNode child) {
        children.add(child);
        child.parent = this;
    }

    public void setParent(BoardNode p) {
        parent = p;
    }
    
    public BoardNode getParent() {
        return parent;
    }
    
    public ArrayList<BoardNode> getChildren() {
        return children;
    }
    
    public boolean hasChild(BoardNode child) {
        for(BoardNode n : children) {
            if(n.isSameAs(child)) {
                return true;
            }
        }
        return false;
    }
    
    public BoardNode findBestChildBoard() {

      if(children.size() == 0) {
          return null;
      }
      
      BoardNode currentMaxNode = children.get(0);
      int currentMaxHeuristic = currentMaxNode.getHeuristicValue();

      for(BoardNode n : children) {
          if(n.getHeuristicValue() > currentMaxHeuristic) {
              currentMaxNode = n;
              currentMaxHeuristic = n.getHeuristicValue();
          }
      }
      
      return currentMaxNode;
    }
    
    public BoardNode getBoardNodeInChildren(Board b) {
        
        for(BoardNode child : children) {
            if(child.isSameAs(new BoardNode(b, null))) {
                return child;
            }
        }
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
    
    public boolean isSameAs(BoardNode b) {
        // Only verifies cell contents
        for(int col = 0; col < board.cells.length; col++) {
            for(int row = 0; row < board.cells[col].length; row++) {
                if(!board.cells[col][row].equals(b.board.cells[col][row])) {
                    return false;
                }
            }
        }
        return true;
    }
}
