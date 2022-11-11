package proj;
import java.util.*;

interface PlayAble {
    Set<Point> getValidMoves();
    List<Point> getPossibleMoves();
    boolean move(Point A);
}

public class Figure implements PlayAble {
    Point position;
    String name = "None";
    Player owner = null;
    volatile boolean isSkipable = false;
    public volatile Point Start;
    public volatile Point Last;

    Figure() {
        isSkipable = true;
        name = Constants.FigureNames.NONE;
    }

    Figure(Player A,Point Start) {
        owner = A;
        this.isSkipable = false;
        this.Start = Start;
        this.position = Start;
        this.Last = null;
        this.name = Constants.FigureNames.NONE;
    }

    Figure(Figure a) {
        this.position = a.position;
        this.owner = a.owner;
        this.Start = a.Start;
        this.Last = a.Last;
        this.name = a.name;
        this.isSkipable = a.isSkipable;
    }

    public Set<Point> getValidMoves() {
        return new HashSet<Point>();
    }

    public List<Point> getPossibleMoves() {
        return new ArrayList<>();
    }

    public Figure setPosition(Point A) {
        Last = new Point(position);
        position = new Point(A);
        return this;
    }

    public boolean move(Point A) { 
        setPosition(A);
        return true;
    }

}
