public class Cell {
    public final int col;
    public final int row;
    
    public Cell(int i, int j) {
        col = i;
        row = j;
    }
    
    public Cell(Cell c) {
        col = c.col;
        row = c.row;
    }
}
