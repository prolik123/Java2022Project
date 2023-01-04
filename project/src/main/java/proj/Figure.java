package proj;
import java.util.*;

interface PlayAble {
    List<Point> getValidMoves();
    List<Point> getPossibleMoves();
    boolean move(Point A);
}

public class Figure implements PlayAble {
    Point position;
    String name = "None";
    String imgLink = "None.png";
    Player owner = null;
    volatile boolean isSkipable = true;
    public volatile Point Start;
    public volatile Point Last;

    public Figure() {
        isSkipable = true;
        name = Constants.FigureNames.NONE;
    }

    public Figure(Player A,Point Start, String imgLink) {
        owner = A;
        this.isSkipable = false;
        this.Start = new Point(Start);
        this.position = new Point(Start);
        this.Last = null;
        this.name = Constants.FigureNames.NONE;
        this.imgLink = new String(imgLink);
    }

    public Figure(Point Start) {
        this.Start = new Point(Start);
        this.position = new Point(Start);
    }

    public Figure(Figure a) {
        this.position = new Point(a.position);
        this.owner = a.owner;
        this.Start = new Point(a.Start);
        if(a.Last == null)
            this.Last = null;
        else
            this.Last = new Point(a.Last);
        this.name = new String(a.name);
        this.isSkipable = a.isSkipable;
    }

    public List<Point> getValidMoves() {
        return new ArrayList<Point>();
    }

    public List<Point> getPossibleMoves() {
        return new ArrayList<Point>();
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

    public List<Point> validList(List<Point> res) {
        List<Point> result = new ArrayList<>();
        for(Point it:res) {
            if(GameEngine.isMoveValid(this.position, it)) {
                result.add(it);
            }
        }

        return result;
    }

    @Override
    public String toString(){
        return name + ":\n" + imgLink + "\n" + position.toString()  + "\n";
    }

}
