import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {

    private int size;
    private String[][] board;

    private ArrayList<Mine> mines;

    public Board(int size){

        this.size = size;

        //Only one variable needed as its a square
        board = new String[size][size];

        mines = new ArrayList<>();

    }

    public void createBoard(int mines){

        for(int i = 0; i < size; i++){

            fill(mines, i, board);

        }

    }

    private void fill(int mines, int i, String[][] board) {
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

    public void addMine(Mine m){

        mines.add(m);

    }

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
