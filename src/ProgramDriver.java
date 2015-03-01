
public class ProgramDriver {

    public static void main(String[] args) {

        Board b = new Board();
        System.out.println(b);
        
        //b.placeWhite("G6");
        
        System.out.println(b);
        
        b.placeBlack("C8");
        
        System.out.println(b);
        
        b.printWhitePlaceableCells();
        b.printBlackPlaceableCells();
    }

}
