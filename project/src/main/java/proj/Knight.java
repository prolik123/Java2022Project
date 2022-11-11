package proj;

import java.util.*;
import java.lang.Math.*;

public class Knight extends Figure {

    Knight(Player A,Point Start) {
        super(A,Start);
        name = Constants.FigureNames.KNIGHT;
    }

    Knight(Figure a) {
        super(a);
    }
    
    /// add Validation
    @Override
    public List<Point> getPossibleMoves() {
        List<Point> res = new ArrayList<>();
        for(int i=-2;i<=2;i++) {
            for(int j=-2;j<=2;j++) {
                if(Math.abs(i) + Math.abs(j) == 3) {
                    Point newPoint = new Point(position.getX()+i, position.getY()+j);
                    if(Point.isPointInBoardRange(newPoint)) {
                        if(GameEngine.isSkipableFigureAt(newPoint)) {
                            res.add(newPoint);
                        }
                        else if(GameEngine.isEnemyFigureAtPosition(owner, newPoint) && GameEngine.isDefeatableFigureAt(newPoint)) {
                            res.add(newPoint);
                        }
                    }
                }
            }
        }
        
        return res;
    }
}
