package proj;

import java.util.*;
import javafx.util.*;

public class WatchEngine {
    public static List<Pair<Point,Point>> moves;
    public static Figure Board[][][];
    public static Integer it = 0;
    WatchEngine(List<Pair<Point,Point>> arg) {
        moves = arg;
        it = 0;
        new GameEngine(0);
        Board = new Figure[arg.size()+1][Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        Board[0] = GameEngine.copyBoard();
        for(int k=0;k<arg.size();k++) {
            GameEngine.move(arg.get(k).getKey(), arg.get(k).getValue(),false);
            Board[k+1] =GameEngine.copyBoard();
        }
    }

    public static void next() {
        if(it < Board.length-1) {
            it++;
            GameEngine.Board = Board[it];
        }
    }

    public static void prev() {
        if(it > 0) {
            it--;
            GameEngine.Board = Board[it];
        }
    }

}
