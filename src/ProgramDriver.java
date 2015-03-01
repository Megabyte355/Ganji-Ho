
public class ProgramDriver {

    public static void main(String[] args) {

        Board b = new Board();
        System.out.println(b);
        
        b.placeWhite("B6");
        
        System.out.println(b);
        
        b.placeBlack("C5");
        
        System.out.println(b);
    }

}
