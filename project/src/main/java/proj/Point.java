package proj;

/// mainly using int so there is no seans of using generics

public class Point {
    public volatile Integer x,y;

    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x) {
        this.x = x;
    }

    public Point(Point A) {
        this.x = A.x;
        this.y = A.y;
    }

    public Point setX(int x) {
        this.x = x;
        return this;
    }

    public Point setY(int y) {
        this.y = y;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Point sumPoint(Point a,Point b) {
        return new Point(a.x+b.x,b.y+a.y);
    }

    public static Point minusPoint(Point a,Point b) {
        return new Point(a.x-b.x,a.y-b.y);
    }

    //@Override
    public boolean EqualTo(Point b) {
        return ((this.x == b.x) && (this.y == b.y));
    }

    public static boolean isPointInBoardRange(Point A) {
        return A.x >= 0 && A.x < Constants.SIZE_OF_BOARD && A.y >= 0 && A.y < Constants.SIZE_OF_BOARD; 
    }

    @Override
    public boolean equals(Object A) {
        if(!(A instanceof Point))
            return false;
        return this.EqualTo((Point)A);
    }

    @Override
    public String toString(){
        return "x = " + x + ", y =  " + y + "\n";
    }
}
