package proj;

import java.util.*;

public class Bishop extends Figure{

    public Bishop(Player A,Point Start, String imgLink) {
        super(A,Start,imgLink);
        name = Constants.FigureNames.BISHOP;
    }

    public Bishop(Figure a) {
        super(a);
    }

    @Override
    public List<Point> getPossibleMoves() {
        List<Point> res = new ArrayList<>();

        /// ADD validation
        for(int i=-1;i<=1;i++) {
            for(int j=-1;j<=1;j++) {
                if(i==0 || j == 0)
                    continue;
                for(int k=1;k<Constants.SIZE_OF_BOARD;k++) {
                    Point newMove = new Point(position.getX()+k*i, position.getY()+k*j);
                    if(Point.isPointInBoardRange(newMove)) {
                        if(GameEngine.isSkipableFigureAt(newMove)) {
                            res.add(newMove);
                        }
                        else if(GameEngine.isEnemyFigureAtPosition(owner, newMove) && GameEngine.isDefeatableFigureAt(newMove)) {
                            res.add(newMove);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }

        return res;
    }
}
