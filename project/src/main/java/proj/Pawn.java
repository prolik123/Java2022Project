package proj;

import java.util.*;

public class Pawn extends Figure {

    Pawn(Player A,Point Start) {
        super(A,Start);
        name = Constants.FigureNames.PAWN;
    }

    Pawn(Figure a) {
        super(a);
    }

    @Override
    public List<Point> getPossibleMoves() {
        List<Point> res = new ArrayList<>();

        /// first type of move ( + 1 )
        Point checkMove = new Point(position.getX(), position.getY() + owner.getMoveModifier());
        if(Point.isPointInBoardRange(checkMove) && GameEngine.isSkipableFigureAt(checkMove)){
                res.add(checkMove);
        }

        /// second type ( Only at first move )
        checkMove = new Point(position.getX(),position.getY() + 2*owner.getMoveModifier());
        if(Point.isPointInBoardRange(checkMove) && position.EqualTo(Start) &&  GameEngine.isSkipableFigureAt(checkMove)) {
            ///is sth on way
            if(GameEngine.isSkipableFigureAt(new Point(position.getX(),position.getY()+owner.getMoveModifier())) ) {
                res.add(checkMove);
            }
        }
        
        /// fight +1x, -1x
        for(int k=-1;k<=1;k++) {
            if(k==0)
                continue;
            checkMove = new Point(position.getX()+k,position.getY()+owner.getMoveModifier());
            if(Point.isPointInBoardRange(checkMove) && GameEngine.isEnemyFigureAtPosition(owner, checkMove) && GameEngine.isDefeatableFigureAt(checkMove)) {
                res.add(checkMove);
                continue;
            }/// El Passanto
            /* 
            Point Neightbour = new Point(position.getX() + k, position.getY());
            if(Point.isPointInBoardRange(checkMove) && Point.isPointInBoardRange(Neightbour) && GameEngine.isEnemyFigureAtPosition(owner, Neightbour) && 
                GameEngine.getFigureNameAtPosition(Neightbour).equals(Constants.FigureNames.PAWN) && 
                GameEngine.getFigureLastPositionAt(Neightbour).EqualTo(GameEngine.getFigureStartPositionAt(Neightbour)) &&
                Point.sumPoint(GameEngine.getFigureStartPositionAt(Neightbour),new Point(0,-2*owner.getMoveModifier())).EqualTo(Neightbour)) {
                    res.add(checkMove);
            }*/
        }
        /// TODO IMPLEMENT BICIE W PRZELOCIE

        return res;
    }
}
