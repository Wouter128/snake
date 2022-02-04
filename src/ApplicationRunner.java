import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRunner extends JPanel implements ActionListener, KeyListener {

    private final List<Square> tail = new ArrayList<>();
    private Square head;
    private Food food;

    private static final int SIZE_TO_MOVE = 10;

    private int delay = 100;
    private Timer timer;

    private boolean gameOver = false;

    private boolean upDir = false;
    private boolean downDir = false;
    private boolean leftDir = false;
    private boolean rightDir = true;

    public static void main(String[] args) {
        ApplicationRunner applicationRunner = new ApplicationRunner();
        applicationRunner.initBoard();
    }

    public void initBoard() {
        head = new Square(100, 100);
        tail.add(new Square(90, 100));
        tail.add(new Square(80, 100));
        createFood();

        JFrame frame = new JFrame("Snake");
        frame.getContentPane().add(this);
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(this);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void createFood() {
        food = new Food();
    }

    public void addSquare() {
        tail.add(new Square(tail.get(tail.size() - 1).getxPos(), tail.get(tail.size() - 1).getyPos()));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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

    public void move() {

        for (int i = tail.size() - 1; i > 0; i--) {
            tail.get(i).setxPos(tail.get(i-1).getxPos());
            tail.get(i).setyPos(tail.get(i-1).getyPos());
        }

        tail.get(0).setxPos(head.getxPos());
        tail.get(0).setyPos(head.getyPos());

        if (leftDir) {
            head.setxPos(head.getxPos() - SIZE_TO_MOVE);
        }
        if (rightDir) {
            head.setxPos(head.getxPos() + SIZE_TO_MOVE);
        }
        if (upDir) {
            head.setyPos(head.getyPos() - SIZE_TO_MOVE);
        }
        if (downDir) {
            head.setyPos(head.getyPos() + SIZE_TO_MOVE);
        }
    }

    public void checkCollision() {
        if (head.getxPos() == food.getxPos() && head.getyPos() == food.getyPos()) {
            addSquare();
            createFood();
            delay -= 3;
            timer.setDelay(delay);
        }
        if (head.getxPos() < Border.getxPos() ||
                head.getyPos() < Border.getyPos() ||
                head.getxPos() + 10 > Border.getxPos() + Border.getWidth() ||
                head.getyPos() + 10 > Border.getyPos() + Border.getHeight()) {
            gameOver = true;
        }
        for (Square square : tail) {
            if (head.getxPos() == square.getxPos() && head.getyPos() == square.getyPos()) {
                gameOver = true;
                break;
            }
        }
    }

    @Override
    public void actionPerformed (ActionEvent e){
        if (!gameOver) {
            checkCollision();
            move();
            repaint();
        } else {
            System.out.println("GAME OVER !");
            System.exit(1);
        }
    }

    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP && !downDir) {
            upDir = true;
            leftDir = false;
            rightDir = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !upDir) {
            downDir = true;
            leftDir = false;
            rightDir = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !rightDir) {
            upDir = false;
            downDir = false;
            leftDir = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !leftDir) {
            upDir = false;
            downDir = false;
            rightDir = true;
        }
    }

    @Override
    public void keyTyped (KeyEvent e){}
    @Override
    public void keyReleased (KeyEvent e){}
}
