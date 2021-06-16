package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {

    final int[][] mazeArray =
            {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1},
                    {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1},
                    {1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 3, 1, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1},
                    {1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1},
                    {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1},
                    {1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1},
                    {1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                    {1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1},
                    {1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1},
                    {1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1},
                    {1, 0, 1, 9, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1},
                    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1},
                    {1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                    {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1},
                    {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1}

            };

    /*
     * 0 - not visited node
     * 1 - wall(blocked) node
     * 2-  visited node
     * 3 - start node
     * 9 - destination node

     * */


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group labirent = new Group();
        MazeSolveModel mazeSolveModel = new MazeSolveModel();
        mazeSolveModel.drawMaze(labirent, mazeArray, mazeSolveModel.squadSize);//Maze have been  drawn
        ArrayList<Location> pureMazeList = new ArrayList<>();
        primaryStage.setTitle("MazeSolving");
        mazeSolveModel.setArrayList(mazeSolveModel.mazeLocations, mazeArray);
        mazeSolveModel.setArrayList(pureMazeList, mazeArray);
        mazeSolveModel.findStartAndFinishLocations(pureMazeList);
        ArrayList<Location> startLocations = mazeSolveModel.solveMaze(mazeSolveModel.mazeLocations, mazeArray);

        ArrayList<Location> reversedTempArrayList = mazeSolveModel.reverseMazeArray(pureMazeList, mazeSolveModel.startAndFiniskLocations);
        mazeSolveModel.findStartAndFinishLocations(reversedTempArrayList);
        ArrayList<Location> finishLocations = mazeSolveModel.solveMaze(reversedTempArrayList, mazeArray);
        if (startLocations.size() > finishLocations.size()) {
            Collections.reverse(finishLocations);
            mazeSolveModel.finalLocations = finishLocations;
        } else {
            mazeSolveModel.finalLocations = startLocations;
        }

        mazeSolveModel.drawPathInMaze(labirent, mazeSolveModel.finalLocations);
        primaryStage.setScene(new Scene(labirent, mazeArray[0].length * mazeSolveModel.squadSize, mazeArray.length * mazeSolveModel.squadSize));
        primaryStage.show();
        mazeSolveModel.startAnimation(labirent, mazeSolveModel.finalLocations, mazeSolveModel.squadSize);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
