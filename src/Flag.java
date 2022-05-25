import java.util.Scanner;

public class Flag {

    private int flagX;
    private int flagY;
    private static int flags;

    public Flag(){

    }


    public static void removeFlag(Flag f, Board b){

        if(flags == 0){
            System.out.println("No flags left, please remove a flag first");
            return;
        }

        if(b.getBoard()[f.getFlagX()][f.getFlagY()].equalsIgnoreCase("f")){
            b.getBoard()[f.getFlagX()][f.getFlagY()] = "e";
            flags--;
        } else {
            System.out.println("No flag in location: " + f.getFlagX() + " " + f.getFlagY());
        }

        b.printBoard();

    }

    public void placeFlag(Scanner s, Board b){

        int flagX;
        int flagY;

        System.out.println("Flag coords? input as x,y");

        String[] coord = s.nextLine().split(",");

        flagX = Integer.parseInt(coord[1]);
        flagY = Integer.parseInt(coord[0]);

        if(flagX > b.getSize()){
            System.out.println("X Coord must be < 5");
            placeFlag(s, b);
        } else if(flagY > b.getSize()){
            System.out.println("Y Coord must be < 5");
            placeFlag(s, b);
        }

        Main.getMines().removeIf(m -> m.getMineX() == flagX && m.getMineY() == flagY);

        if(Main.getMines().size() == 0){

            Main.gameOver(false, b);
            return;
        }

        b.getBoard()[flagX][flagY] = "f";
        b.printBoard();

    }

    public int getFlagX() {
        return flagX;
    }

    public int getFlagY() {
        return flagY;
    }
}
