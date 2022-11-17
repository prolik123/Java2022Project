package proj;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.util.*;

public class GameScreen extends GridPane {
    
    Button boardView[][];
    public static List<Point> currLit;
    public static volatile int whoHasMove = 0;
    public static Result resVbox = null;
    public static double height = 0;
    public static double width = 0;
    public static Score WhiteScore;
    public static Score BlackScore;
    public static volatile boolean firstMove = false;
    GameScreen(double h, double w)
    {
        WhiteScore = new Score("White");
        BlackScore = new Score("Black");
        height = h;
        width = w;
        whoHasMove = 0;
        currLit = new ArrayList<>();
        boardView = new Button[Constants.SIZE_OF_BOARD][Constants.SIZE_OF_BOARD];
        if(GameEngine.Users[whoHasMove].isBot)
            firstMove = true;
        drawBoard();
    }

    void drawBoard()
    {
        //add(new Result("NAME",500,Color.RED),0,0);
        Button back = new Button("Back to Main Menu");
        back.setOnAction(e->{
            App.switchScene("Menu");
        });
        
        back.setMaxSize(2*(App.squareSize),(App.squareSize));
        back.setMinSize(2*(App.squareSize),(App.squareSize));
        Score white = new Score("White");
        add(back,0,8);
        add(white,0,0);
        add(new Score("Black"),2,0);
        for(int j=0;j<Constants.SIZE_OF_BOARD;j++){
            HBox temp = new HBox();
            for(int i=0;i<Constants.SIZE_OF_BOARD;i++){

                boardView[i][j]=new Button("");
                temp.getChildren().add(i,boardView[i][j]);
                boardView[i][j].setStyle(Constants.BOARD_STYLE[(i+j)%2]);
                boardView[i][j].setMaxSize((App.squareSize),(App.squareSize));
                boardView[i][j].setMinSize((App.squareSize),(App.squareSize));
            }
            add(temp,1,j+1);
            //this.setAlignment(temp, Pos.TOP_CENTER);
        }
        add(new Result("",Color.WHITE),1,0);
        updateBoard();
    }

    void updateBoard() {
        for(int i=0;i<Constants.SIZE_OF_BOARD;i++) {
            for(int j=0;j<Constants.SIZE_OF_BOARD;j++) {

                boardView[i][j].setStyle(Constants.BOARD_STYLE[(i+j)%2]);
                if(GameEngine.getFigureNameAtPosition(new Point(i,j)).equals(Constants.FigureNames.KING) && GameEngine.isCheckForPlayer(GameEngine.Board[i][j].owner)) {
                    boardView[i][j].setStyle(Constants.ATTACK_STYLE[(i+j)%2]);
                }
                if(GameEngine.Board[i][j].name.equals(Constants.FigureNames.NONE)) {
                    boardView[i][j].setGraphic(new ImageView());
                }
                else
                    boardView[i][j].setGraphic(new ImageView(new Image(App.class.getResource(GameEngine.Board[i][j].imgLink).toExternalForm())));
                //if(!GameEngine.Board[i][j].name.equals(Constants.FigureNames.NONE))
                    
                final int a = i;
                final int b = j;
                if(GameEngine.Users[whoHasMove] != GameEngine.Board[i][j].owner) {
                    boardView[i][j].setOnAction(null);
                    continue;
                }
                if(GameEngine.Users[whoHasMove].isBot){
                    boardView[i][j].setOnAction(null);
                    continue;
                }
                boardView[i][j].setOnAction(e -> {
                    firstMove = false;
                    updateBoard();
                    List<Point> arr = GameEngine.Board[a][b].getValidMoves();
                    for(Point temp:arr) {
                        final int x = temp.getX();
                        final int y = temp.getY();
                        if(GameEngine.isEnemyFigureAtPosition(GameEngine.Board[a][b].owner, temp)) {
                            boardView[x][y].setStyle(Constants.ATTACK_STYLE[(x+y)%2]);
                        }
                        else {
                            boardView[x][y].setStyle(Constants.MOVE_STYLE[(x+y)%2]);
                        }
                        if(GameEngine.getPositionOfPlayerKing(GameEngine.Users[0]).EqualTo(new Point(x,y)) || GameEngine.getPositionOfPlayerKing(GameEngine.Users[1]).EqualTo(new Point(x,y)))
                            boardView[x][y].setOnAction(null);
                        else {
                            boardView[x][y].setOnAction(ev -> {
                                firstMove = false;
                                GameEngine.move(new Point(a,b),new Point(x,y));
                                whoHasMove = (whoHasMove+1)%2;
                                updateBoard();
                                int result = GameEngine.isMatOrPat();
                                if(result != 0) {
                                    setResult(result);
                                    return;
                                }
                                whoHasMove = GameEngine.Users[whoHasMove].move(whoHasMove);
                                result = GameEngine.isMatOrPat();
                                if(result != 0) {
                                    setResult(result);
                                    return;
                                }
                                if(GameEngine.Users[(whoHasMove+1)%2].isBot)
                                    updateBoard();
                            });
                        }
                    }
                });
            }
        }
        if(GameEngine.Users[whoHasMove].isBot && firstMove) {
            firstMove = false;
            whoHasMove = GameEngine.Users[whoHasMove].move(whoHasMove);
            updateBoard();
        }
    }

    public void setResult(int res) {
        if(res == 4) {
            resVbox = new Result("STEALMATE!", Color.BLACK);
        }
        else if(res == 2) {
            resVbox = new Result("BLACK WON!", Color.RED);
        }
        else if(res == 1) {
            resVbox = new Result("WHITE WON!", Color.BLUE);
        }
        add(resVbox,1,0);
    }

    public static class Result extends HBox {
        public Result(String Name,Color Col) {
            setSpacing(20);
            Text res=new Text();
            res.setFill(Col);
            res.setText(Name);
            //res.setFont(Font.font("MedievalSharp",size));
            if(!Name.equals(""))
                setStyle("-fx-font: 48 arial");

            setAlignment(Pos.CENTER);
            //setMaxHeight((App.squareSize),(App.squareSize));
            setMaxSize((App.squareSize),(App.squareSize));
            setMinSize((App.squareSize),(App.squareSize));
            setMaxWidth(width);
            getChildren().add(res);
        }
        
    }

    public static class Score extends VBox {
        public Score(String Name) {
            setSpacing((App.squareSize/2));
            Text res=new Text();
            //res.setFill(Col);
            res.setText(Name);
            //res.setFont(Font.font("MedievalSharp",size));
            if(!Name.equals(""))
                setStyle("-fx-font: 48 arial");

            setAlignment(Pos.CENTER);
            //setMaxHeight((App.squareSize),(App.squareSize));
            setMaxSize(2*(App.squareSize),(App.squareSize));
            setMinSize(2*(App.squareSize),(App.squareSize));
            setMaxWidth(width);
            getChildren().add(res);
        }

        
    }
    public static class ButtonExtended extends Button {
        ButtonExtended(String imgLink) {
            setMaxSize(2*(App.squareSize)/20,(App.squareSize)/20);
            setMinSize(2*(App.squareSize)/20,(App.squareSize)/20);
            this.setGraphic(new ImageView(new Image(App.class.getResource(imgLink).toExternalForm())));
        }
    }
}