package proj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static double maxHeight;
    public static double maxWidth;
    public static double squareSize;

    @Override
    public void start(Stage stage) throws IOException {
        
        GameEngine current = new GameEngine();
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        maxHeight = primaryScreenBounds.getHeight();
        maxWidth = primaryScreenBounds.getWidth();
        squareSize = (maxHeight/8.3);
        GameScreen curr = new GameScreen();
        stage.setScene(new Scene(curr));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}