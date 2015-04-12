import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Board {
    int totalColumns;
    int totalRows;
    String alphabet;    
    int[][] cells;
    private Cell lastMove;
    
    String validInputRegex;
    Pattern validInputPatern;
    
    
    public Board() {
        // Default values
        totalColumns = 8;
        totalRows = 8;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        validInputRegex = "^([A-z])(\\d+)$";
        validInputPatern = Pattern.compile(validInputRegex, Pattern.CASE_INSENSITIVE);
        lastMove = null;
        
        // Instantiate all Cells
        cells = new int[totalColumns][totalRows];
    }
    
    public Board(Board b) {
        totalColumns = b.totalColumns;
        totalRows = b.totalRows;
        alphabet = b.alphabet;
        
        validInputRegex = b.validInputRegex;
        validInputPatern = b.validInputPatern;
        
        cells = new int[totalColumns][totalRows];
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = b.cells[i][j];
            }
        }
        if(b.lastMove != null) {
            lastMove = new Cell(b.lastMove);
        }
    }
    
    public String toString() {
        String output = "\n";
        
        // Column headers
        output += "  | ";
        for(int i = 1; i <= cells.length; i++) {
            output += i + " ";
        }
        
        // Splitter
        output += "\n--+";
        for(int i = 1; i <= cells.length; i++) {
            output += "--";
        }
        
        // Cells for each row
        output += "\n";
        for(int row = 0; row < cells[0].length; row++) {    

            // Row headers
            output += alphabet.charAt(row) + " |";

            // Cell contents
            for(int col = 0; col < cells.length; col++) {
                char character = '.';
                if(cells[col][row] == 1) {
                    character = 'w';
                } else if(cells[col][row] == 2) {
                    character = 'b';
                }
                output += " " + character;
            }
            output += "\n";
        }
        
        return output;
    }
    
    public Cell getLastMove() {
        return lastMove;
    }
    
    public boolean place(String input, Color color) {
        if(color == Color.WHITE) {
            return placeWhite(input);
        } else if (color == Color.BLACK) {
            return placeBlack(input);
        } else {
            return false;
        }
    }
    
    public boolean placeWhite(String input) {
        Matcher matcher = validInputPatern.matcher(input);
        
        if(matcher.find()) {
            int row = alphabet.indexOf(matcher.group(1).toUpperCase());
            int column = Integer.parseInt(matcher.group(2)) - 1;
            return placeWhite(column, row);
        } else {
            System.out.println("Invalid input. Try again.");
        }
        return false;
    }
    
    public boolean placeBlack(String input) {
        Matcher matcher = validInputPatern.matcher(input);
        
        if(matcher.find()) {
            int row = alphabet.indexOf(matcher.group(1).toUpperCase());
            int column = Integer.parseInt(matcher.group(2)) - 1;
            return placeBlack(column, row);
        } else {
            System.out.println("Invalid input. Try again.");
        }
        return false;
    }
    
    public boolean placeWhite(int col, int row) {
        // Validate input (Black pieces are placed vertically)
        if(canPlaceWhite(col, row)) {
            cells[col][row] = 1;
            cells[col][row + 1] = 1;
            lastMove = new Cell(col, row);
            return true;
        } else {
            String pos1 = getCellPositionString(col, row);
            String pos2 = getCellPositionString(col, row + 1);
            System.out.println("Unable to place WHITE pieces on cells " + pos1 + " and " + pos2 + ". Try again.");
            return false;
        }
    }
    
    public boolean placeBlack(int col, int row) {
        // Validate input (Black pieces are placed vertically)
        if(canPlaceBlack(col, row)) {
            cells[col][row] = 2;
            cells[col + 1][row] = 2;
            lastMove = new Cell(col, row);
            return true;
        } else {
            String pos1 = getCellPositionString(col, row);
            String pos2 = getCellPositionString(col + 1, row);
            System.out.println("Unable to place BLACK pieces on cells " + pos1 + " and " + pos2 + ". Try again.");
            return false;
        }
    }
    
    public void printWhitePlaceableCells() {
        ArrayList<Cell> list = getWhitePlaceableCells();
        
        String output = "Available options for WHITE: [";
        for(int i = 0; i < list.size(); i++) {
            output += getCellPositionString(list.get(i).col, list.get(i).row);
            if(i < list.size() - 1) {
                output += ", ";
            }
        }
        output += "]";
        System.out.println(output);
    }
    
    public void printBlackPlaceableCells() {
        ArrayList<Cell> list = getBlackPlaceableCells();
        
        String output = "Available options for BLACK: [";
        for(int i = 0; i < list.size(); i++) {
            output += getCellPositionString(list.get(i).col, list.get(i).row);
            if(i < list.size() - 1) {
                output += ", ";
            }
        }
        output += "]";
        System.out.println(output);
    }
    
    public ArrayList<Cell> getPlaceableCells(Color color)
    {
        if(color == Color.WHITE) {
            return getWhitePlaceableCells();
        } else if (color == Color.BLACK) {
            return getBlackPlaceableCells();
        } else {
            return null;
        }
    }
    
    public ArrayList<Cell> getWhitePlaceableCells() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        for(int col = 0; col < cells.length; col++) {
            for(int row = 0; row < cells[col].length; row++) {
                if(canPlaceWhite(col, row)) {
                    list.add(new Cell(col, row));
                }
            }
        }
        return list;
    }

    public ArrayList<Cell> getBlackPlaceableCells() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        for(int col = 0; col < cells.length; col++) {
            for(int row = 0; row < cells[col].length; row++) {
                if(canPlaceBlack(col, row)) {
                    list.add(new Cell(col, row));
                }
            }
        }
        return list;
    }
    
    private boolean canPlaceWhite(int column, int row) {
        
        boolean validCoordinate = column >= 0 && column < cells.length && row >= 0 && (row + 1) < cells[column].length;
        return validCoordinate && cells[column][row] == 0 && cells[column][row + 1] == 0;
    }
    
    private boolean canPlaceBlack(int column, int row) {
        boolean validCoordinate = column >= 0 && (column + 1) < cells.length && row >= 0 && row < cells[column].length;
        return validCoordinate && cells[column][row] == 0 && cells[column + 1][row] == 0;
    }
    
    public int readCell(int column, int row) {
        return cells[column][row];
    }
    
    public String getCellPositionString(int column, int row) {
        if(row < alphabet.length()) {
            column++;
            String rowChar = Character.toString(alphabet.charAt(row));
            return rowChar + column;
        }
        return "INVALID POSITION";
    }
}
