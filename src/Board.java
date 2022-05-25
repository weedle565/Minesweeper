import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {

    private final int size;
    private final String[][] board;


    public Board(int size){

        this.size = size;

        //Only one variable needed as it's a square
        board = new String[size][size];

    }

    //Create the board
    public void createBoard(int mines){

        for(int i = 0; i < size; i++){

            fill(mines, i);

        }

    }

    /**
     * Fills the board with empty game objects
     * @param mines Number of mines
     * @param i iterator from createBoard(), encapsulated to be used elsewhere
     */
    private void fill(int mines, int i) {
        for(int z = 0; z < mines*2+1; z++){
            //Put the coordinate system on the outside
            if(i == 0){
                board[i][z] = String.valueOf(z);

            } else if (z == 0){
                board[i][z] = String.valueOf(i);
            } else {
                board[i][z] = "e";
            }

        }
    }

    //Print the board
    public void printBoard(){

        for (String[] strings : board) {

            for (int z = 0; z < board.length; z++) {

                System.out.printf("%2s ", strings[z]);

            }

            System.out.println();

        }
    }

    public int getSize() {
        return size;
    }

    public String[][] getBoard() {
        return board;
    }
}
