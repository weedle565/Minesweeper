import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    //Whether the player is in game or not
    private static boolean playing;

    //Hold all current flags and mines
    private static ArrayList<Flag> flags;
    private static ArrayList<Mine> mines;


    public Main() {

        playing = true;

        mines = new ArrayList<>();
        flags = new ArrayList<>();

    }

    /**
     * Initialises all game processors
     * @param size Number of mines, also used to calculate board size
     *
     */
    private void gameInit(int size){

        /*
        Setup the board so that there is one mine per 2*2 area, the + 1 is because [0][0] is not part of the board as it stores the coordinate numbers
        to make reading where you are entering easier
        */

        Board b = new Board(size*2+1);

        b.createBoard(size);

        //Add as many mines as specified
        for(int i = 0; i <= size; i++){

            mines.add(new Mine(b));

        }

        b.printBoard();

        //Begin the game
        UI(b);

    }

    /**
     * Helper method to locate a specific flag and remove it from the lists and game
     * @param s to take user input on location
     * @param b to interact with the current game board
     */
    private void flagRemover(Scanner s, Board b){

        System.out.println("Enter the coord of the flag you want removed (x,y): ");

        //Split the input to get the x and y coordinate of the flag
        String[] flagSplitter = s.nextLine().split(",");

        for(Flag f : flags){

            //If flag x,y from flagSplitter is the same x,y as a flag in the list, remove it
            if(f.getFlagX() == Integer.parseInt(flagSplitter[0]) && f.getFlagY() == Integer.parseInt(flagSplitter[0])){

                Flag.removeFlag(f, b);
                flags.remove(f);

            }

        }

        b.printBoard();

    }

    /**
     * Check if a given point in the board has a mine around it
     * @param s to take user input on location
     * @param b to interact with the current game board
     */
    private void checkSpot(Scanner s, Board b){

        int checkX;
        int checkY;
        int counter = 0;

        System.out.println("Check coords? input as x,y");

        String[] coord = s.nextLine().split(",");

        //Coordinates to check
        checkX = Integer.parseInt(coord[1]);
        checkY = Integer.parseInt(coord[0]);

        for(Mine m : mines){
            //If the position checked is a mine, kill player
            if(checkX == m.getMineX() && checkY == m.getMineY()){

                gameOver(true, b);

            //Long if statement to check if there is a mine within one coordinate around the checked area, diagonally is included
            //@TODO Make this better
            } else if(checkX + 1 == m.getMineX() && checkY + 1 == m.getMineY() || checkX + 1 == m.getMineX() && checkY == m.getMineY() || checkX - 1 == m.getMineX() && checkY == m.getMineY() || checkX - 1 == m.getMineX() && checkY - 1 == m.getMineY() || checkX == m.getMineX() && checkY + 1 == m.getMineY()|| checkX == m.getMineX() && checkY - 1 == m.getMineY()){
                counter++;
            }
        }

        //Replace the point of the board checked with the number of mines adjacent to it
        b.getBoard()[checkX][checkY] = String.valueOf(counter);
        b.printBoard();

    }

    //Testing method to show all mine locations
    private static void testing(Board b){
        for(Mine m : mines){

            b.getBoard()[m.getMineX()][m.getMineY()] = "m";

        }

        b.printBoard();
    }

    /**
     * End the game
     * @param died Whether the game ended from death or success
     * @param b to interact with the current game board
     */
    public static void gameOver(boolean died, Board b){

        if(died) {
            System.out.println("You died!");
        } else {
            System.out.println("You win!");
        }

        //Show all mine locations
        testing(b);

        playing = false;
        b.printBoard();
        System.exit(1);
    }

    //Main game controller, contains all commands and usages
    private void UI(Board b){

        Scanner s = new Scanner(System.in);

        System.out.println("Flag, check, remove or exit?");

        String move = s.nextLine();

        while(playing){

            //Test for each command and act accordingly
            switch (move.toLowerCase(Locale.ROOT)){
                case "flag":
                    if(flags.size() < mines.size()){

                        Flag f = new Flag();
                        f.placeFlag(s, b);
                        flags.add(f);

                    }
                    break;

                case "check":
                    checkSpot(s, b);
                    break;

                case "exit":
                    System.exit(1);

                case "remove":
                    flagRemover(s, b);
                    break;

                case "testing":
                    testing(b);
                    break;

                default:
                    System.out.println("Unknown Move");
            }

            //Reset
            System.out.println("Flag, check, remove or exit?");

            move = s.nextLine();

        }

    }


    public static ArrayList<Mine> getMines(){
        return mines;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("How many mines?");

        int mines = Integer.parseInt(s.nextLine());

        Main m = new Main();
        m.gameInit(mines);

    }
}
