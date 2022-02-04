public class Game {

    private final Snake snake;
    private Food food;

    private static final int SIZE_TO_MOVE = 10;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = true;
    private String direction;

    private boolean gameOver;

    public Game() {
        snake = new Snake();
        gameOver = false;
        food = new Food();
        direction = "right";
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public void createFood() {
        food = new Food();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void switchToGameOver() {
        gameOver = true;
    }

    public void moveSnake() {
        snake.move(direction, SIZE_TO_MOVE);
    }

    public void changeDirection(String direction) {
        if (direction.equals("up") && !down) {
            up = true;
            left = false;
            right = false;
            this.direction = "up";
        }
        if (direction.equals("down") && !up) {
            down = true;
            left = false;
            right = false;
            this.direction = "down";
        }
        if (direction.equals("left") && !right) {
            left = true;
            up = false;
            down = false;
            this.direction = "left";
        }
        if (direction.equals("right") && !left) {
            right = true;
            up = false;
            down = false;
            this.direction = "right";
        }
    }

    public void checkCollision() {
        if (snake.getHead().getxPos() == food.getxPos() && snake.getHead().getyPos() == food.getyPos()) {
            snake.growTail();
            createFood();
//            delay -= 3;
//            timer.setDelay(delay);
        }
        if (snake.getHead().getxPos() < Border.getxPos() ||
                snake.getHead().getyPos() < Border.getyPos() ||
                snake.getHead().getxPos() + 10 > Border.getxPos() + Border.getWidth() ||
                snake.getHead().getyPos() + 10 > Border.getyPos() + Border.getHeight()) {
            switchToGameOver();
        }
        for (Square square : snake.getTail()) {
            if (snake.getHead().getxPos() == square.getxPos() && snake.getHead().getyPos() == square.getyPos()) {
                switchToGameOver();
                break;
            }
        }
    }
}
