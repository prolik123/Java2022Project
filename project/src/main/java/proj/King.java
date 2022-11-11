package proj;

import java.util.*;

public class King extends Figure{

    King(Player A,Point Start) {
        super(A,Start);
        name = Constants.FigureNames.KING;
    }

    King(Figure a) {
        super(a);
    }

    ///Add Validation
    @Override
    public List<Point> getPossibleMoves() {
        List<Point> res = new ArrayList<>();

        for(int i=-1;i<=1;i++) {
            for(int j=-1;j<=1;j++) {
                if(j ==0 && i == 0)
                    continue;
                Point newMove = new Point(position.getX() + i,position.getY()+j);
                if(Point.isPointInBoardRange(newMove)) {
                    if(GameEngine.isSkipableFigureAt(newMove)) {
                        res.add(newMove);
                    }
                    else if(GameEngine.isEnemyFigureAtPosition(owner, newMove) && GameEngine.isDefeatableFigureAt(newMove)) {
                        res.add(newMove);
                    }
                }
            }
        }
        return res;
    }
}
