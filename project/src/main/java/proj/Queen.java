package proj;

import java.util.*;

public class Queen extends Figure {
    
    Queen(Player A,Point Start) {
        super(A,Start);
        name = Constants.FigureNames.QUEEN;
    }

    Queen(Figure a) {
        super(a);
    }

    @Override
    public List<Point> getPossibleMoves() {
        List<Point> res = new ArrayList<>();

        /// ADD Validation
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

        for(int i=-1;i<=1;i++) {
            if(i == 0)
                continue;
            for(int k=1;k<Constants.SIZE_OF_BOARD;k++) {
                Point newMove = new Point(position.getX()+i*k, position.getY());
                if(Point.isPointInBoardRange(newMove) && GameEngine.isSkipableFigureAt(newMove)) {
                    res.add(newMove);
                }
                else if(Point.isPointInBoardRange(newMove) && GameEngine.isEnemyFigureAtPosition(owner, newMove)
                    && GameEngine.isDefeatableFigureAt(newMove)) {
                    res.add(newMove);
                    break;
                }
                else {
                    break;
                }
            }
            for(int k=1;k<Constants.SIZE_OF_BOARD;k++) {
                Point newMove = new Point(position.getX(), position.getY()+i*k);
                if(Point.isPointInBoardRange(newMove) && GameEngine.isSkipableFigureAt(newMove)) {
                    if(!res.contains(newMove)) {
                        res.add(newMove);
                    }
                }
                else if(Point.isPointInBoardRange(newMove) && GameEngine.isEnemyFigureAtPosition(owner, newMove)
                    && GameEngine.isDefeatableFigureAt(newMove)) {
                    if(!res.contains(newMove)) {
                        res.add(newMove);
                    }
                    break;
                }
                else {
                    break;
                }
            }
        }


        return res;
    }
}
