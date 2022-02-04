import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final List<Square> tail = new ArrayList<>();
    private final Square head;

    public Snake() {
        head = new Square(100, 100);
        tail.add(new Square(90, 100));
        tail.add(new Square(80, 100));
    }

    public Square getHead() {
        return head;
    }

    public List<Square> getTail() {
        return tail;
    }

    public void growTail() {
        int tailSize = tail.size();
        int x = tail.get(tailSize-1).getxPos();
        int y = tail.get(tailSize-1).getyPos();

        tail.add(new Square(x, y));
    }

    public void move(String direction, int sizeToMove) {
        for (int i = tail.size() - 1; i > 0; i--) {
            tail.get(i).setxPos(tail.get(i-1).getxPos());
            tail.get(i).setyPos(tail.get(i-1).getyPos());
        }

        tail.get(0).setxPos(head.getxPos());
        tail.get(0).setyPos(head.getyPos());

        switch (direction) {
            case "up" -> head.setyPos(head.getyPos() - sizeToMove);
            case "down" -> head.setyPos(head.getyPos() + sizeToMove);
            case "left" -> head.setxPos(head.getxPos() - sizeToMove);
            case "right" -> head.setxPos(head.getxPos() + sizeToMove);
        }
    }
}
