import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static boolean playing;

    private static ArrayList<Flag> flags;
    private static ArrayList<Mine> mines;


    public Main() {

        playing = true;

        mines = new ArrayList<>();
        flags = new ArrayList<>();

    }

    private void gameInit(int size){

        Board b = new Board(size*2+1);

        b.createBoard(size);

        for(int i = 0; i <= size; i++){

            mines.add(new Mine(b));

        }

        b.printBoard();

        UI(b);

    }

    private void flagRemover(Scanner s, Board b){

        System.out.println("Enter the coord of the flag you want removed (x,y): ");

        String[] flagSplitter = s.nextLine().split(",");

        for(Flag f : flags){

            if(f.getFlagX() == Integer.parseInt(flagSplitter[0]) && f.getFlagY() == Integer.parseInt(flagSplitter[0])){

                Flag.removeFlag(f, b);
                flags.remove(f);

            }

        }

    }

    private void checkSpot(Scanner s, Board b){

        int checkX;
        int checkY;
        int counter = 0;

        System.out.println("Check coords? input as x,y");

        String[] coord = s.nextLine().split(",");

        checkX = Integer.parseInt(coord[1]);
        checkY = Integer.parseInt(coord[0]);

        for(Mine m : mines){
            if(checkX == m.getMineX() && checkY == m.getMineY()){

                gameOver(true, b);

            } else if(checkX + 1 == m.getMineX() && checkY + 1 == m.getMineY() || checkX + 1 == m.getMineX() && checkY == m.getMineY() || checkX - 1 == m.getMineX() && checkY == m.getMineY() || checkX - 1 == m.getMineX() && checkY - 1 == m.getMineY() || checkX == m.getMineX() && checkY + 1 == m.getMineY()|| checkX == m.getMineX() && checkY - 1 == m.getMineY()){
                counter++;
            }
        }

        b.getBoard()[checkX][checkY] = String.valueOf(counter);
        b.printBoard();

    }

    private static void testing(Board b){
        for(Mine m : mines){

            b.getBoard()[m.getMineX()][m.getMineY()] = "m";

        }

        b.printBoard();
    }

    public static void gameOver(boolean died, Board b){

        if(died) {
            for(Mine m : mines) {
                b.getBoard()[m.getMineX()][m.getMineY()] = "m";
            }
            System.out.println("You died!");
        } else {
            for(int i = 0; i < mines.size(); i++) {

                testing(b);

            }

            System.out.println("You win!");
        }

        playing = false;
        b.printBoard();
        System.exit(1);
    }

    private void UI(Board b){

        Scanner s = new Scanner(System.in);

        System.out.println("Flag, check, remove or exit?");

        String move = s.nextLine();

        while(playing){

            /*if(move.equalsIgnoreCase("flag")){
                if(flags.size() < mines.size()){

                    Flag f = new Flag();
                    f.placeFlag(s, b);
                    flags.add(f);

                }
            } else if(move.equalsIgnoreCase("check")){
                checkSpot(s, b);
            } else if(move.equalsIgnoreCase("exit")){
                System.exit(1);
            } else if(move.equalsIgnoreCase("remove")){

                flagRemover(s, b);

            } else if(move.equalsIgnoreCase("testing")){
                testing(b);
            }else {
                System.out.println("Unknown move");
            }*/

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

                case
            }

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
