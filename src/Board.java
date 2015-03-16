import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Board {
    int totalColumns;
    int totalRows;
    String alphabet;    
    Cell[][] cells;
    
    String validInputRegex;
    Pattern validInputPatern;
    
    
    public Board() {
        // Default values
        totalColumns = 8;
        totalRows = 8;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        validInputRegex = "^([A-z])(\\d+)$";
        validInputPatern = Pattern.compile(validInputRegex, Pattern.CASE_INSENSITIVE);
        
        // Instantiate all Cells
        cells = new Cell[totalColumns][totalRows];
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }
    
    public Board(Board b) {
        totalColumns = b.totalColumns;
        totalRows = b.totalRows;
        alphabet = b.alphabet;
        
        validInputRegex = b.validInputRegex;
        validInputPatern = b.validInputPatern;
        
        cells = new Cell[totalColumns][totalRows];
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(b.cells[i][j]);
            }
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
                output += " " + cells[col][row];
            }
            output += "\n";
        }
        
        return output;
    }
    
    public boolean placeWhite(String input) {
        Matcher matcher = validInputPatern.matcher(input);
        
        if(matcher.find()) {
            int row = alphabet.indexOf(matcher.group(1).toUpperCase());
            int column = Integer.parseInt(matcher.group(2)) - 1;

            return placeWhite(column, row);
            // Validate input (White pieces are placed vertically)
//            if(canPlaceWhite(column, row)) {
//                cells[column][row].setWhite();
//                cells[column][row + 1].setWhite();
//                return true;
//            } else {
//                String pos1 = getCellPositionString(column, row);
//                String pos2 = getCellPositionString(column, row + 1);
//                System.out.println("Unable to place WHITE pieces on cells " + pos1 + " and " + pos2 + ". Try again.");
//            }
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
            // Validate input (Black pieces are placed vertically)
//            if(canPlaceBlack(column, row)) {
//                cells[column][row].setBlack();
//                cells[column + 1][row].setBlack();
//                return true;
//            } else {
//                String pos1 = getCellPositionString(column, row);
//                String pos2 = getCellPositionString(column + 1, row);
//                System.out.println("Unable to place BLACK pieces on cells " + pos1 + " and " + pos2 + ". Try again.");
//            }
        } else {
            System.out.println("Invalid input. Try again.");
        }
        return false;
    }
    
    public boolean placeWhite(int col, int row) {
        // Validate input (Black pieces are placed vertically)
        if(canPlaceWhite(col, row)) {
            cells[col][row].setWhite();
            cells[col][row + 1].setWhite();
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
            cells[col][row].setBlack();
            cells[col + 1][row].setBlack();
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
            output += getCellPositionString(list.get(i).getColumn(), list.get(i).getRow());
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
            output += getCellPositionString(list.get(i).getColumn(), list.get(i).getRow());
            if(i < list.size() - 1) {
                output += ", ";
            }
        }
        output += "]";
        System.out.println(output);
    }
    
    public ArrayList<Cell> getWhitePlaceableCells() {
        ArrayList<Cell> list = new ArrayList<Cell>();
        for(int col = 0; col < cells.length; col++) {
            for(int row = 0; row < cells[col].length; row++) {
                if(canPlaceWhite(col, row)) {
                    list.add(cells[col][row]);
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
                    list.add(cells[col][row]);
                }
            }
        }
        return list;
    }
    
    private boolean canPlaceWhite(int column, int row) {
        
        boolean validCoordinate = column >= 0 && column < cells.length && row >= 0 && (row + 1) < cells[column].length;
        return validCoordinate && cells[column][row].isEmpty() && cells[column][row + 1].isEmpty();
    }
    
    private boolean canPlaceBlack(int column, int row) {
        boolean validCoordinate = column >= 0 && (column + 1) < cells.length && row >= 0 && row < cells[column].length;
        return validCoordinate && cells[column][row].isEmpty() && cells[column + 1][row].isEmpty();
    }
    
    private String getCellPositionString(int column, int row) {
        if(row < alphabet.length()) {
            column++;
            String rowChar = Character.toString(alphabet.charAt(row));
            return rowChar + column;
        }
        return "INVALID POSITION";
    }
}
