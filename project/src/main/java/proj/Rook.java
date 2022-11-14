package proj;

import java.util.*;

public class Rook extends Figure {

    public Rook(Player A,Point Start, String imgLink) {
        super(A,Start,imgLink);
        name = Constants.FigureNames.ROOK;
    }

    public Rook(Rook a) {
        super(a);
    }

    /// add move validation
    @Override
    public List<Point> getPossibleMoves() {
        List<Point> res = new ArrayList<>();

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
        }
        return res;
    }
    
}
