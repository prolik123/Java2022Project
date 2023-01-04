package proj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.scene.*;
import javafx.util.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;



/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage stage;
    public static double maxHeight;
    public static double maxWidth;
    public static double squareSize;
    public static Parent root;
    public static GameScreen curGameScreen;

    @Override
    public void start(Stage stage) throws IOException {
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        maxHeight = primaryScreenBounds.getHeight();
        maxWidth = primaryScreenBounds.getWidth();
        squareSize = (maxHeight/9.5);
        stage.setScene( new Scene(new Menu()));
        stage.show();
        stage.setTitle("Chess Game");
        stage.setResizable(false);
        App.stage = stage;
    }


    public static void main(String[] args) {
        launch();
    }

    public static void switchScene(String Name)
    {
        try
        {
            if(Name.equals("Start Game COOP")) {
                GameEngine current = new GameEngine(0);
                stage.setScene(new Scene(new GameScreen(maxHeight, maxWidth,false)));
            }
            else if(Name.equals("Menu")) {
                stage.setScene( new Scene(new Menu()));
            }
            else if(Name.equals("Start Game as White")) {
                GameEngine current = new GameEngine(1);
                stage.setScene(new Scene(new GameScreen(maxHeight, maxWidth,false)));
            }
            else if(Name.equals("Start Game as Black")) {
                GameEngine current = new GameEngine(2);
                stage.setScene(new Scene(new GameScreen(maxHeight, maxWidth,false)));
            }
            else if(Name.equals("Watch")) {
                GameEngine current = new GameEngine(0);
                curGameScreen = new GameScreen(maxHeight, maxWidth, true);
                stage.setScene(new Scene(curGameScreen));
            }
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static class Menu extends GridPane {

        Button play;
        Button playAsWhite;
        Button playAsBlack;
        Button importFile;
        Menu()
        {
            play = new Button("PLAY COOP");
            play.setMinSize(3*squareSize, squareSize);
            play.setMaxSize(3*squareSize, squareSize);
            playAsWhite = new Button("PLAY AS WHITE");
            playAsWhite.setMinSize(3*squareSize, squareSize);
            playAsWhite.setMaxSize(3*squareSize, squareSize);
            playAsBlack = new Button("PLAY AS BLACK");
            playAsBlack.setMinSize(3*squareSize, squareSize);
            playAsBlack.setMaxSize(3*squareSize, squareSize);
            importFile = new Button("IMPORT FILE");
            importFile.setMinSize(3*squareSize, squareSize);
            importFile.setMaxSize(3*squareSize, squareSize);


            setStyle(
            "-fx-background-image: url('" + App.class.getResource("bg.png").toExternalForm() + "'); " +
            "-fx-background-position: center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: auto 100%; " +
            "-fx-font-size: 20; ");
            setVgap(squareSize/4);
            setHgap(squareSize);
            add(play,5,16);
            add(playAsWhite,5,17);
            add(playAsBlack,5,18);
            add(importFile,5,19);
            setMinWidth(12*squareSize);
            setMinHeight(9*squareSize);
            setMaxWidth(12*squareSize);
            setMaxHeight(9*squareSize);
            play.setOnAction(e -> {
                    switchScene("Start Game COOP");
                }
            );
            playAsWhite.setOnAction(e -> {
                    switchScene("Start Game as White");
                }
            );
            playAsBlack.setOnAction(e -> {
                    switchScene("Start Game as Black");
                }
            );
            importFile.setOnAction(e -> {
                FileChooser fc = new FileChooser();
                File selectedFile;
                fc.setTitle("Select file to open");
                selectedFile = fc.showOpenDialog(stage);
                List<Pair<Point,Point>> res = JsonParser.getJsonMoves(selectedFile.getAbsolutePath());
                if(res == null) {
                    Alert al = new Alert(AlertType.ERROR);
                    al.setTitle("This file is not supported");
                    al.setContentText("This file is not supported");
                    al.show();
                }
                else {
                    new WatchEngine(res);
                    switchScene("Watch");
                }
            });
        }
    
    }
}