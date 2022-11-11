package proj;
import java.util.*;

public class GameEngine {

    public static Figure[][] Board;
    public static Player[] Users;
    public GameEngine() {
        Board = new Figure[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        Users = new Player[2];
        Users[0] = new Player();
        Users[1] = new Player();
    }

    public static boolean isSkipableFigureAt(Point A) {
        if(A.getX() < 0 || A.getX() >= 8)
            return false;
        if(A.getY() < 0 || A.getY() >= 8)
            return false;
        return Board[A.getX()][A.getY()].isSkipable;
    }


    public static boolean isEnemyFigureAtPosition(Player pl,Point A) {
        Figure temp = Board[A.getX()][A.getY()];
        if(temp.owner == null)
            return false;
        return temp.owner != pl;
    }

    public static boolean isDefeatableFigureAt(Point A) {
        return Board[A.getX()][A.getY()].name.equals(Constants.FigureNames.KING);
    }

    public static String getFigureNameAtPosition(Point A) {
        if(!Point.isPointInBoardRange(A))
            throw new RuntimeException("" + A.x + " " + A.y);
        return Board[A.getX()][A.getY()].name;
    }

    public static Point getFigureStartPositionAt(Point A) {
        if(!Point.isPointInBoardRange(A))
            throw new RuntimeException("" + A.x + " " + A.y);
        return Board[A.getX()][A.getY()].Start;
    }

    public static Point getFigureLastPositionAt(Point A) {
        if(!Point.isPointInBoardRange(A))
            throw new RuntimeException("" + A.x + " " + A.y);
        return Board[A.getX()][A.getY()].Last;
    }

    public static Player getOtherPlayer(Player a) {
        if(Users[0] == a)
            return Users[1];
        return Users[0];
    }

    public static boolean isMoveValid(Point A, Point B) { /// not implemented yet
        if(!Point.isPointInBoardRange(A) || !Point.isPointInBoardRange(B))
            return false;
        Player other = getOtherPlayer(Board[A.getX()][A.getY()].owner);
        Figure TempTab[][] = new Figure[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        return true;
    }
    
}
