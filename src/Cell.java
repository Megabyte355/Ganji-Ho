
public class Cell {
    enum CellState {EMPTY, WHITE, BLACK};
    CellState state;
    
    public Cell() {
        state = CellState.EMPTY;
    }
    
    public boolean isEmpty() {
        return state == CellState.EMPTY;
    }
    
    public boolean setWhite() {
        if(isEmpty()) {
            state = CellState.WHITE;
            return true;
        }
        return false;
    }
    
    public boolean setBlack() {
        if(isEmpty()) {
            state = CellState.BLACK;
            return true;
        }
        return false;
    }
    
    public boolean isWhite() {
        return state == CellState.WHITE;
    }
    
    public boolean isBlack() {
        return state == CellState.BLACK;
    }
    
    public void clear() {
        state = CellState.EMPTY;
    }
    
    public String toString() {
        if(state == CellState.WHITE) {
            return "w";
        } else if (state == CellState.BLACK) {
            return "b";
        } else {
            return ".";
        }
    }
}
