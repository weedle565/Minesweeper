import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private final String[][] board;
    private final int[] mineX;
    private final int[] mineY;
    private boolean playing;
    private int flagsLeft;
    private int mines;

    public Main(int size) {

        board = new String[size*2+1][size*2+1];
        mineX = new int[size];
        mineY = new int[size];

        playing = true;

    }

    private void createBoard(int mines){

        for(int i = 0; i < mines*2+1; i++){

            for(int z = 0; z < mines*2+1; z++){
                if(i == 0){
                    board[i][z] = String.valueOf(z);

                } else if (z == 0){
                    board[i][z] = String.valueOf(i);
                } else {
                    board[i][z] = "e";
                }

            }

        }

    }

    private void printBoard(){

        for (String[] strings : board) {

            for (int z = 0; z < board.length; z++) {

                System.out.printf("%2s ", strings[z]);

            }

            System.out.println();

        }
    }

    private void placeMines(int mines){

        for (int i = 0; i < mines; i++) {
            Random r = new Random();
            mineX[i] = r.nextInt(1, mines*2+1);
            mineY[i] = r.nextInt(1, mines*2+1);
        }

        flagsLeft = mines;
        this.mines = mines;

    }

    private void flagBeginning(Scanner s, String command){

        int flagX;
        int flagY;

        System.out.println("Flag coords? input as x,y");

        String[] coord = s.nextLine().split(",");

        flagX = Integer.parseInt(coord[1]);
        flagY = Integer.parseInt(coord[0]);

        if(flagX > mines*2+1){
            System.out.println("X Coord must be < 5");
            flagBeginning(s, command);
        } else if(flagY > mines*2+1){
            System.out.println("Y Coord must be < 5");
            flagBeginning(s, command);
        }

        if(command.equals("remove")){
            removeFlag(flagX, flagY);
        } else {
            placeFlag(flagX, flagY);
        }
    }

    private void removeFlag(int flagX, int flagY){

        if(flagsLeft == 0){
            System.out.println("No flags left, please remove a flag first");
            return;
        }

        if(board[flagX][flagY].equalsIgnoreCase("f")){
            board[flagX][flagY] = "e";
        } else {
            System.out.println("No flag in location: " + flagX + " " + flagY);
        }

        printBoard();

    }

    private void placeFlag(int flagX, int flagY){

        for (int i = 0; i < mineX.length; i++) {

            if(mineX[i] == flagX && mineY[i] == flagY){

                mines--;

                if(mines == 0){

                    gameOver(false);
                    return;
                }

            }

        }

        board[flagX][flagY] = "f";
        printBoard();

    }

    private void checkSpot(Scanner s){

        int checkX;
        int checkY;
        int counter = 0;

        System.out.println("Check coords? input as x,y");

        String[] coord = s.nextLine().split(",");

        checkX = Integer.parseInt(coord[1]);
        checkY = Integer.parseInt(coord[0]);

        for(int i = 0; i < mineX.length; i++){
            if(checkX == mineX[i] && checkY == mineY[i]){

                gameOver(true);

            } else if(checkX + 1 == mineX[i] && checkY + 1 == mineY[i] || checkX + 1 == mineX[i] && checkY == mineY[i] || checkX - 1 == mineX[i] && checkY == mineY[i] || checkX - 1 == mineX[i] && checkY - 1 == mineY[i] || checkX == mineX[i] && checkY + 1 == mineY[i] || checkX == mineX[i] && checkY - 1 == mineY[i]){
                counter++;
            }
        }

        board[checkX][checkY] = String.valueOf(counter);
        printBoard();

    }

    private void UI(){

        Scanner s = new Scanner(System.in);

        System.out.println("Flag, check, remove or exit?");

        String move = s.nextLine();

        while(playing){

            if(move.equalsIgnoreCase("flag")){
                flagBeginning(s, "flag");
            } else if(move.equalsIgnoreCase("check")){
                checkSpot(s);
            } else if(move.equalsIgnoreCase("exit")){
                System.exit(1);
            } else if(move.equalsIgnoreCase("remove")){
                flagBeginning(s, "remove");
            } else if(move.equalsIgnoreCase("testing")){
                testing();
            }else {
                System.out.println("Unknown move");
            }

            System.out.println("Flag, check, remove or exit?");

            move = s.nextLine();

        }

    }

    private void testing(){
        for(int i = 0; i < mineX.length; i++){

            board[mineX[i]][mineY[i]] = "m";

        }

        printBoard();
    }

    private void gameOver(boolean died){

        if(died) {
            for(int i = 0; i < mineX.length; i++) {
                board[mineX[i]][mineY[i]] = "m";
            }
            System.out.println("You died!");
        } else {
            for(int i = 0; i < mineX.length; i++) {
                playing = false;
                board[mineX[i]][mineY[i]] = "m";

            }

            System.out.println("You win!");
        }

        printBoard();
        System.exit(1);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("How many mines?");

        int mines = Integer.parseInt(s.nextLine());

        Main m = new Main(mines);
        m.createBoard(mines);
        m.printBoard();
        m.placeMines(mines);
        m.UI();

    }
}
