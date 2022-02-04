import java.util.Random;

public class Food {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private final int xPos;
    private final int yPos;

    public Food() {
        this.xPos = (new Random().nextInt(30) + 5) * 10;
        this.yPos = (new Random().nextInt(30) + 5) * 10;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
