package proj;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.util.*;

public class GameScreen extends GridPane {
    
    Button boardView[][];
    public static List<Point> currLit;
    GameScreen()
    {
        currLit = new ArrayList<>();
        boardView = new Button[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        drawBoard();
        updateBoard();
    }

    void drawBoard()
    {
        for(int i=0;i<Constants.SIZE_OF_BOARD;i++){
            for(int j=0;j<Constants.SIZE_OF_BOARD;j++){

                boardView[i][j]=new Button("");
                add(boardView[i][j],i,j);
                boardView[i][j].setStyle(Constants.BOARD_STYLE[(i+j)%2]);
                boardView[i][j].setMaxSize((App.squareSize),(App.squareSize));
                boardView[i][j].setMinSize((App.squareSize),(App.squareSize));
                final int a = i;
                final int b = j;
                boardView[i][j].setOnAction(e -> {

                    updateBoard();
                    List<Point> arr = GameEngine.Board[a][b].getPossibleMoves();
                    for(Point temp:arr) {
                        boardView[temp.getX()][temp.getY()].setStyle(Constants.MOVE_STYLE[(temp.getX()+temp.getY())%2]);
                        final int x = temp.getX();
                        final int y = temp.getY();
                        boardView[temp.getX()][temp.getY()].setOnAction(ev -> {
                            GameEngine.move(new Point(a,b),new Point(x,y));
                            updateBoard();
                        });
                        
                    }
                });
            }
        }
    }

    void updateBoard() {
        for(int i=0;i<Constants.SIZE_OF_BOARD;i++) {
            for(int j=0;j<Constants.SIZE_OF_BOARD;j++) {

                boardView[i][j].setStyle(Constants.BOARD_STYLE[(i+j)%2]);
                if(GameEngine.Board[i][j].name.equals(Constants.FigureNames.NONE)) {
                    boardView[i][j].setGraphic(new ImageView());
                }
                else
                    boardView[i][j].setGraphic(new ImageView(new Image(App.class.getResource(GameEngine.Board[i][j].imgLink).toExternalForm())));
                final int a = i;
                final int b = j;
                boardView[i][j].setOnAction(e -> {
 
                    updateBoard();
                    List<Point> arr = GameEngine.Board[a][b].getPossibleMoves();
                    for(Point temp:arr) {
                        boardView[temp.getX()][temp.getY()].setStyle(Constants.MOVE_STYLE[(temp.getX()+temp.getY())%2]);
                        final int x = temp.getX();
                        final int y = temp.getY();
                        boardView[temp.getX()][temp.getY()].setOnAction(ev -> {
                            GameEngine.move(new Point(a,b),new Point(x,y));
                            updateBoard();
                        });
                    }
                });
            }
        }
    }

}