import java.util.ArrayList;
import java.util.Random;

public class BoardNode implements Comparable<BoardNode> {
    Board board;
    Integer heuristic;
    Color playerMove;
    boolean pruned;
    
    private BoardNode parent;
    private ArrayList<BoardNode> children;
    
    public BoardNode(Board b, Color color) {
        parent = null;
        board = b;
        heuristic = null;
        playerMove = color;
        pruned = false;
        
        children = new ArrayList<BoardNode>();
    }
    
    public Board getBoard() {
        return board;
    }
    
    public Color getPlayerColor()
    {
        return playerMove;
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
    
    public void setPruned(boolean p) {
        pruned = true;
    }
    
    public boolean isPruned() {
        return pruned;
    }
    
    public void pruneAllRemainingChildren() {
        for(BoardNode child : children) {
            if(child.heuristic == null) {
                child.pruned = true;
                child.pruneAllRemainingChildren();                
            }
        }
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
      
      int currentMaxHeuristic = findFirstUnpruned().getHeuristicValue();
      
      for(BoardNode n : children) {
          if(!n.isPruned() && n.getHeuristicValue() > currentMaxHeuristic) {
              currentMaxHeuristic = n.getHeuristicValue();
          }
      }
      
      ArrayList<BoardNode> bestChoices = new ArrayList<BoardNode>();
      for(BoardNode n : children) {
          if(!n.isPruned() && n.getHeuristicValue() == currentMaxHeuristic) {
              bestChoices.add(n);
          }
      }
      
      Random r = new Random();
      int chosenOne = r.nextInt(bestChoices.size());
      
      return bestChoices.get(chosenOne);
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
                if(board.cells[col][row] != b.board.cells[col][row]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private BoardNode findFirstUnpruned() {
        for(BoardNode n : children) {
            if(!n.isPruned()) {
                return n;
            }
        }
        return null;
    }
}
