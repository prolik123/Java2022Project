package proj;

import java.lang.Math.*;
import java.util.*;

public class Player {
    
    Integer modyfier = 0;
    public volatile boolean isBot = false;
    public Player(int mod) {
        modyfier = mod;
    }
    public Integer getMoveModifier() {
        return modyfier;
    }
    public int move(int whoMove) {
        return whoMove;
    }

    public static class Bot extends Player {
        public Bot(int mod) {
            super(mod);
            isBot = true;
        }
        @Override
        public int move(int whoMove) {
            whoMove = (whoMove+1)%2;
            while(true) {
                int randx = (int) ((Math.random())*((Constants.SIZE_OF_BOARD)));
                int randy = (int) ((Math.random())*((Constants.SIZE_OF_BOARD)));
                if(GameEngine.Board[randx][randy].owner == this) {
                    List<Point> mv = GameEngine.Board[randx][randy].getValidMoves();
                    if(!mv.isEmpty()) {
                        GameEngine.move(new Point(randx, randy), mv.get(0));
                        break;
                    }
                }
            }
            return whoMove;
        }
    }
}
