package proj;

/// mainly using int so there is no seans of using generics

public class Point {
    public volatile int x,y;

    Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    Point(int x) {
        this.x = x;
    }

    Point(Point A) {
        this.x = A.x;
        this.y = A.y;
    }

    Point setX(int x) {
        this.x = x;
        return this;
    }

    Point setY(int y) {
        this.y = y;
        return this;
    }

    int getX() {
        return x;
    }

    int getY() {
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
}
