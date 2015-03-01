import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Board {
    int totalColumns;
    int totalRows;
    String alphabet;    
    Cell[][] cells;
    
    
    public Board() {
        // Default values
        totalColumns = 8;
        totalRows = 8;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        // Instantiate all Cells
        cells = new Cell[totalColumns][totalRows];
        for(int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }
    
    public String toString() {
        String output = "";
        
        // Column headers
        output += "  ";
        for(int i = 1; i <= cells.length; i++) {
            output += i + " ";
        }
        output += "\n";
        
        for(int row = 0; row < cells.length; row++) {    

            // Row headers
            output += alphabet.charAt(row);

            // Cell contents
            for(int col = 0; col < cells[row].length; col++) {
                output += " " + cells[col][row];
            }
            output += "\n";
        }
        return output;
    }
    
    public boolean placeWhite(String input) {
        Pattern pattern = Pattern.compile("(\\w)(\\d)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        
        if(matcher.find()) {
            int row = alphabet.indexOf(matcher.group(1).toUpperCase());
            int column = Integer.parseInt(matcher.group(2)) - 1;
            
            // Validate input (White pieces are placed vertically)
            if(canPlaceWhite(column, row)) {
                cells[column][row].setWhite();
                cells[column][row + 1].setWhite();
                return true;
            } else {
                String pos1 = getCellPositionString(column, row);
                String pos2 = getCellPositionString(column, row + 1);
                System.out.println("Unable to place WHITE pieces on cells " + pos1 + " and " + pos2);
            }
        }
        return false;
    }
    
    public boolean placeBlack(String input) {
        Pattern pattern = Pattern.compile("(\\w)(\\d)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        
        if(matcher.find()) {
            int row = alphabet.indexOf(matcher.group(1).toUpperCase());
            int column = Integer.parseInt(matcher.group(2)) - 1;
            
            // Validate input (Black pieces are placed vertically)
            if(canPlaceBlack(column, row)) {
                cells[column][row].setBlack();
                cells[column + 1][row].setBlack();
                return true;
            } else {
                String pos1 = getCellPositionString(column, row);
                String pos2 = getCellPositionString(column + 1, row);
                System.out.println("Unable to place BLACK pieces on cells " + pos1 + " and " + pos2);
            }
        }
        return false;
    }
    
    public void printWhitePlaceableCells() {
        ArrayList<Cell> list = GetWhitePlaceableCells();
        
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
        ArrayList<Cell> list = GetBlackPlaceableCells();
        
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
    
    public ArrayList<Cell> GetWhitePlaceableCells() {
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

    public ArrayList<Cell> GetBlackPlaceableCells() {
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
        boolean columnValid = column >= 0 && column < cells.length;
        boolean rowValid = row >= 0 && (row + 1) < cells[column].length;
        return columnValid && rowValid && cells[column][row].isEmpty() && cells[column][row + 1].isEmpty();
    }
    
    private boolean canPlaceBlack(int column, int row) {
        boolean columnValid = column >= 0 && (column + 1) < cells.length;
        boolean rowValid = row >= 0 && row < cells[column].length;
        return columnValid && rowValid && cells[column][row].isEmpty() && cells[column + 1][row].isEmpty();
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
