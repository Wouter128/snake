import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class ApplicationRunner extends JPanel implements ActionListener, KeyListener {

    private int delay = 100;
    private static final int DELAY_INTERVAL = 1;
    private Timer timer;
    private Game game;

    private JLabel scoreLabel;
    private JLabel delayLabel;

    public static void main(String[] args) {
        ApplicationRunner applicationRunner = new ApplicationRunner();
        applicationRunner.initBoard();
    }

    public void initBoard() {
        game = new Game();

        JFrame frame = new JFrame("### SNAKE ###");

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        scoreLabel = new JLabel("Score: " + game.getScore());
        delayLabel = new JLabel("Delay: " + delay);
        pane.add(scoreLabel, BorderLayout.NORTH);
        pane.add(this, BorderLayout.CENTER);
        pane.add(delayLabel, BorderLayout.SOUTH);
        frame.setSize(450, 450);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addKeyListener(this);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Square head = game.getSnake().getHead();
        List<Square> tail = game.getSnake().getTail();
        Food food = game.getFood();

        g.setColor(Color.RED);
        g.fillRect(head.getxPos(), head.getyPos(), head.getWidth(), head.getHeight());

        g.setColor(Color.BLACK);
        g.drawRect(Border.getxPos(), Border.getyPos(), Border.getWidth(), Border.getHeight());
        g.drawRect(Border.getxPos()-5, Border.getyPos()-5, Border.getWidth()+10, Border.getHeight()+10);

        for (Square square : tail) {
            g.fillRect(square.getxPos(), square.getyPos(), square.getWidth(), square.getHeight());
        }
        g.setColor(Color.GREEN);
        g.fillOval(food.getxPos(), food.getyPos(), food.getWidth(), food.getHeight());
    }

    @Override
    public void actionPerformed (ActionEvent e){
        if (!game.isGameOver()) {
            if (game.foodEaten()) {
                scoreLabel.setText("Score: " + game.getScore());
                delay -= DELAY_INTERVAL;
                delayLabel.setText("Delay: " + delay);
                timer.setDelay(delay);
                game.createFood();
            }
            game.checkCollision();
            game.moveSnake();
            repaint();
        } else {
            System.out.println("GAME OVER !");
            System.exit(1);
        }
    }

    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            game.changeDirection("up");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            game.changeDirection("down");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.changeDirection("left");
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.changeDirection("right");
        }
    }

    @Override
    public void keyTyped (KeyEvent e){}
    @Override
    public void keyReleased (KeyEvent e){}
}
