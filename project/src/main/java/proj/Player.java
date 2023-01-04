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
            while(true) {
                int randx = (int) ((Math.random())*((Constants.SIZE_OF_BOARD)));
                int randy = (int) ((Math.random())*((Constants.SIZE_OF_BOARD)));
                if(GameEngine.Board[randx][randy].owner == this) {
                    List<Point> mv = GameEngine.Board[randx][randy].getValidMoves();
                    if(!mv.isEmpty()) {
                        int randMove = (int) ((Math.random())*((mv.size())));
                        GameEngine.move(new Point(randx, randy), mv.get(randMove),true);
                        if(GameEngine.ableToPromote(GameEngine.Board[mv.get(randMove).getX()][mv.get(randMove).getY()]) > 0) {
                            String imgL = "QueenBlack.png";
                            if(whoMove == 0) {
                                imgL = "QueenWhite.png";
                            }
                            GameEngine.Board[mv.get(randMove).getX()][mv.get(randMove).getY()] = new Queen(GameEngine.Users[whoMove], mv.get(randMove),imgL );
                           
                        }
                        break;
                    }
                }
            }
            whoMove = (whoMove+1)%2;
            return whoMove;
        }
    }
}
