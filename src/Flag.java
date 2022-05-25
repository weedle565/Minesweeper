import java.util.Scanner;

public class Flag {

    private int flagX;
    private int flagY;
    private static int flags;

    /**
     * To remove an existing flag
     * @param f Flag to remove
     * @param b to interact with the current game board
     */
    public static void removeFlag(Flag f, Board b){

        //If there are no flags to remove, fail
        if(flags == 0){
            System.out.println("No flags left, please remove a flag first");
            return;
        }

        //Remove flag
        if(b.getBoard()[f.getFlagX()][f.getFlagY()].equalsIgnoreCase("f")){
            b.getBoard()[f.getFlagX()][f.getFlagY()] = "e";
            flags--;
        } else {
            System.out.println("No flag in location: " + f.getFlagX() + " " + f.getFlagY());
        }

        b.printBoard();

    }

    /**
     * place a flag
     * @param s to take user input
     * @param b to interact with the current game board
     */
    public void placeFlag(Scanner s, Board b){

        System.out.println("Flag coords? input as x,y");

        String[] coord = s.nextLine().split(",");

        flagX = Integer.parseInt(coord[1]);
        flagY = Integer.parseInt(coord[0]);

        //Fail if input is outside the size of the board
        if(flagX > b.getSize()){
            System.out.println("X Coord must be < " + b.getSize());
            placeFlag(s, b);
        } else if(flagY > b.getSize()){
            System.out.println("Y Coord must be < " + b.getSize());
            placeFlag(s, b);
        }

        //Remove a mine if there is a flag there
        Main.getMines().removeIf(m -> m.getMineX() == flagX && m.getMineY() == flagY);

        //If there are no mines left, win
        if(Main.getMines().size() == 0){

            Main.gameOver(false, b);
            return;
        }

        //Replace an empty board object with a flag
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
