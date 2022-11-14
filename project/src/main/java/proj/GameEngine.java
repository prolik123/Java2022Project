package proj;
import java.lang.reflect.Constructor;
import java.util.*;

public class GameEngine {

    public static Figure[][] tempBoard = null;
    public static Figure[][] Board;
    public static Player[] Users;
    public GameEngine() {
        Board = new Figure[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        Users = new Player[2];
        Users[0] = new Player(-1);
        Users[1] = new Player(1);
        JsonParser.setStartingPosition(Board,Constants.DEFAULT_POSITION_PATH);
        for(Figure[] itt : Board) {
            for(Figure it : itt) {
                System.out.println(it);
                System.out.println("Possible moves: \n");
                System.out.println(it.getPossibleMoves());
                System.out.println("\n");
            }
        }
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

    public static Figure copyFigureWithType(Figure a) {
        if(a.isSkipable)
            return new Figure(a);
        try{
            Class<?> className = Class.forName(Constants.CLASS_PREFIX + a.name);
            Constructor<?> Con = className.getConstructor(className);
            
            return (Figure)Con.newInstance(a);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Figure[][] copyBoard() {
        Figure TempTab[][] = new Figure[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        for(int k=0;k<Constants.SIZE_OF_BOARD;k++) {
            for(int i=0;i<Constants.SIZE_OF_BOARD;k++) {
                TempTab[k][i] = copyFigureWithType(Board[k][i]);
            }
        }
        return TempTab;
    }


    public static boolean isMoveValid(Point A, Point B) { /// not implemented yet
        if(!Point.isPointInBoardRange(A) || !Point.isPointInBoardRange(B))
            return false;
        //Player other = getOtherPlayer(Board[A.getX()][A.getY()].owner);
        tempBoard = copyBoard();
        Figure[][] cpy = Board;
        Board = tempBoard;
        tempBoard = cpy;
        Board[B.getX()][B.getY()] = copyFigureWithType(Board[A.getX()][A.getY()]);
        Board[B.getX()][B.getY()].move(B);
        Board[A.getX()][A.getY()] = new Figure(A);
        boolean w = isCheckForPlayer(Board[B.getX()][B.getY()].owner);
        cpy = tempBoard;
        tempBoard = Board;
        Board = cpy;
        return w;
    }

    public static Point getPositionOfPlayerKing(Player a) {//, Figure[][] TempBoard) {
        for(Figure[] iit:Board) {
            for(Figure it:iit) {
                if(it.name.equals(Constants.FigureNames.KING) && it.owner == a) {
                    return new Point(it.position);
                }
            }
        }
        return null;
    }

    public static boolean isCheckForPlayer(Player a/* , Figure[][] TempBoard*/) {
        Point kingPosition = getPositionOfPlayerKing(a);//,TempBoard);
        Player other = getOtherPlayer(a);
        for(Figure[] iit:Board) {
            for(Figure it:iit) {
                if(it.owner == other) {
                    List<Point> arr = it.getPossibleMoves();
                    for(Point temp:arr) {
                        if(temp.EqualTo(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
}
