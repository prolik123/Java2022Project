package proj;
import java.lang.reflect.Constructor;
import java.util.*;

import proj.Player.Bot;

public class GameEngine {

    public static Figure[][] tempBoard = null;
    public static Figure[][] Board;
    public static Player[] Users;
    public static volatile int whoHasMove;
    public static volatile int gameType;
    //public static GameScreen globalScreen;
    public GameEngine(int gameType) {
        this.gameType = gameType;
        Board = new Figure[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        Users = new Player[2];
        if(gameType == 0) {
            Users[0] = new Player(-1);
            Users[1] = new Player(1);
        }
        else if(gameType == 1) {
            Users[0] = new Player(-1);
            Users[1] = new Bot(1);
        }
        else if(gameType == 2) {
            Users[0] = new Bot(-1);
            Users[1] = new Player(1);
        }
        JsonParser.setStartingPosition(Board,Constants.DEFAULT_POSITION_PATH);
        /*for(Figure[] itt : Board) {
            for(Figure it : itt) {
                System.out.println(it);
                System.out.println("Possible moves: \n");
                System.out.println(it.getPossibleMoves());
                System.out.println("\n");
            }
        }*/
        whoHasMove = 0;
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
        return true;
        //return !Board[A.getX()][A.getY()].name.equals(Constants.FigureNames.KING);
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
        if(Users[0].equals(a))
            return Users[1];
        return Users[0];
    }

    public static Figure copyFigureWithType(Figure a) {
        if(a.isSkipable)
            return new Figure(a);
        try{
            Class<?> className = Class.forName(Constants.CLASS_PREFIX + a.name);
            Constructor Con = className.getConstructor(Player.class,Point.class,String.class);
            
            return (Figure)Con.newInstance(a.owner,a.position,a.imgLink);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Figure[][] copyBoard() {
        Figure TempTab[][] = new Figure[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        for(int k=0;k<Constants.SIZE_OF_BOARD;k++) {
            for(int i=0;i<Constants.SIZE_OF_BOARD;i++) {
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
        return !w;
    }

    public static Point getPositionOfPlayerKing(Player a) {//, Figure[][] TempBoard) {
        for(Figure[] iit:Board) {
            for(Figure it:iit) {
                //System.out.println("" + it.name + it.owner + " " + a + " " + it.position +"\n");
                if(it.name.equals(Constants.FigureNames.KING) && it.owner.equals(a)) {
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
                //System.out.println(other + " " + it.owner + " " + it.name);
                if(other.equals(it.owner)) {
                    List<Point> arr = it.getPossibleMoves();
                    for(Point temp:arr) {
                        //System.out.println(temp + " " + kingPosition);
                        if(temp.EqualTo(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void move(Point A,Point B) {
        Board[B.getX()][B.getY()] = copyFigureWithType(Board[A.getX()][A.getY()]);
        Board[A.getX()][A.getY()] = new Figure(A);
        Board[B.getX()][B.getY()].move(B);
        whoHasMove = (whoHasMove+1)%2;
    }

    public static int isMatOrPat() {
        boolean isAbleToMove = false;
        for(Figure[] iit:Board) {
            for(Figure it:iit) {
                if(Users[whoHasMove].equals(it.owner)) {
                    if(!it.getValidMoves().isEmpty()) {
                        isAbleToMove = true;
                        break;
                    }
                }
            }
        }
        if(!isAbleToMove && !isCheckForPlayer(Users[(whoHasMove)%2])) {
            return 4;
        }
        else if(!isAbleToMove && whoHasMove == 0)
            return 2;
        else if(!isAbleToMove && whoHasMove == 1) {
            return 1;
        }
        return 0;
    }

    public static int ableToPromote(Figure a) {
        if(a.name.equals(Constants.FigureNames.PAWN) && a.position.getY() == 0) {
            return 2;
        }
        else if(a.name.equals(Constants.FigureNames.PAWN) && a.position.getY() == Constants.SIZE_OF_BOARD-1) {
            return 1;
        }
        return 0;
    }
    
}
