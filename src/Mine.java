import java.util.Random;

public class Mine {

    private int mineX;
    private int mineY;

    public Mine(Board b){


        Random r = new Random();
        this.mineX = r.nextInt(1, b.getSize());
        this.mineY = r.nextInt(1, b.getSize());

    }

    public int getMineX() {
        return mineX;
    }

    public int getMineY() {
        return mineY;
    }
}
