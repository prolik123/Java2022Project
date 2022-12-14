package proj;


import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.*;

import org.json.*;
import javafx.util.*;


public class JsonParser {

    public static void setStartingPosition(Figure[][] Board, String Path) {
        try{
            JSONArray arr = getJsonArray(Path);
            for(int k=0;k<Constants.SIZE_OF_BOARD;k++) {
                for(int i=0;i<Constants.SIZE_OF_BOARD;i++) {
                    Board[k][i] = new Figure(new Point(k,i));
                }
            }
            /// for each element of Json Array
            for(int k=0;k<arr.length();k++) {
                /// Getting k-th Json value form the array
                JSONObject currentJsonValue = (JSONObject) arr.get(k);
                JSONObject currentJsonFigure = (JSONObject) currentJsonValue.get("figure");
                Class className = Class.forName(Constants.CLASS_PREFIX + getJsonStringAttribute(currentJsonFigure,"name"));
                Point temp = new Point(getJsonIntegerAttribute(currentJsonFigure,"xPosition"),getJsonIntegerAttribute(currentJsonFigure,"yPosition"));
                int numOfPlayer = getJsonIntegerAttribute(currentJsonFigure,"owner");
                Constructor Con = className.getConstructor(Player.class,Point.class,String.class);
                Board[temp.getX()][temp.getY()]  = (Figure) Con.newInstance(GameEngine.Users[numOfPlayer],temp,getJsonStringAttribute(currentJsonFigure,"link"));
                //System.out.println(Board[temp.getX()][temp.getY()]);
            }
        }catch(Exception e) {
            //System.out.println(Pawn.class.getName());
            e.printStackTrace();
        }
    }

    public static List<Pair<Point,Point>> getJsonMoves(String Path) {
        List<Pair<Point,Point>> result = new ArrayList<>();
        try{
            //System.out.println(Path);
            JSONArray arr = new JSONArray(new JSONTokener(new FileReader( new File(Path))));
            /// for each element of Json Array
            for(int k=0;k<arr.length();k++) {
                /// Getting k-th Json value form the array
                JSONObject currentJsonValue = (JSONObject) arr.get(k);
                JSONObject currentJsonMove = (JSONObject) currentJsonValue.get("move");
                JSONObject currentJsonPoint = (JSONObject) currentJsonMove.get("start");
                Point first = new Point(rawIntGet(currentJsonPoint,"x"),rawIntGet(currentJsonPoint, "y"));
                currentJsonPoint = (JSONObject) currentJsonMove.get("end");
                Point second = new Point(rawIntGet(currentJsonPoint,"x"),rawIntGet(currentJsonPoint, "y"));
                if(!Point.isPointInBoardRange(first) || !Point.isPointInBoardRange(second))
                    throw new RuntimeException();
                result.add(new Pair<Point,Point>(first,second));
            }
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /// You must be sure that the attribute is String
    public static String getJsonStringAttribute(JSONObject object,String attributeName) {
        return (String) object.get(attributeName);
    }

    /// You must be sure that the attribute is Integer
    public static Integer getJsonIntegerAttribute(JSONObject object,String attributeName) {
        return Integer.parseInt((String) object.get(attributeName));
    }

    public static Integer rawIntGet(JSONObject object,String attributeName) {
        return (Integer) object.get(attributeName);
    }

    /// Returns the json array from given path
    public static JSONArray getJsonArray(String path) throws JSONException, FileNotFoundException {
        return new JSONArray(new JSONTokener(new FileReader(
            App.class.getResource(path).getFile())));
    }
    
}
