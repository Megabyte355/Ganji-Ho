
public class Cell {
    public enum CellState {EMPTY, WHITE, BLACK};
    CellState state;
    int col;
    int row;
    
    public Cell() {
        state = CellState.EMPTY;
        col = 0;
        row = 0;
    }
    
    public Cell(int col, int row) {
        state = CellState.EMPTY;
        this.col = col;
        this.row = row;
    }
    
    public Cell(Cell c) {
        this.col = c.col;
        this.row = c.row;
        this.state = c.state;
    }
    
    public int getColumn() {
        return col;
    }
    
    public int getRow() {
        return row;
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
    
    public boolean equals(Cell other) {
        return (state == other.state) && (col == other.col) && (row == other.row);
    }
}
