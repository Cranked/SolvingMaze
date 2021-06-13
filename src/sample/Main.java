package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    int squadSize = 20;
    Direction direction;
    boolean backState = false;
    boolean solved = false;
    boolean foundStartPlaceState = false;
    ArrayList<Location> mazeLocations = new ArrayList<>();
    ArrayList<Location> startAndFiniskLocations = new ArrayList<>();

    final int[][] maze =
            {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                    {1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1},
                    {1, 1, 9, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1},
                    {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 1, 1, 1, 3, 0, 1, 0, 1},
                    {1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 30, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1},
                    {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1},
                    {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

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
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazeLocations.add(new Location(i, j, maze[i][j]));
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(squadSize);
                rectangle.setHeight(squadSize);
                rectangle.setX(j * squadSize);
                rectangle.setY(i * squadSize);
                switch (maze[i][j]) {
                    case 0:
                        rectangle.setFill(Color.WHITE);
                        break;
                    case 1:
                        rectangle.setFill(Color.BLACK);
                        break;
                    case 3:
                        rectangle.setFill(Color.GREEN);
                        break;
                    case 9:
                        rectangle.setFill(Color.RED);
                        break;
                }
                labirent.getChildren().add(rectangle);
            }
        }

        ArrayList<Location> pureMazeList = new ArrayList<>();
        primaryStage.setTitle("Labirent Çözme");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                pureMazeList.add(new Location(i, j, maze[i][j]));
            }
        }
        findStartAndFinishLocations(pureMazeList);
        ArrayList<Location> startLocations = solveMaze(mazeLocations);

        ArrayList<Location> reversedTempArrayList = reverseMazeArray(pureMazeList, startAndFiniskLocations);
        findStartAndFinishLocations(reversedTempArrayList);
        ArrayList<Location> finishLocations = solveMaze(reversedTempArrayList);

        drawMaze(labirent, startLocations.size() > finishLocations.size() ? finishLocations : startLocations);
        primaryStage.setScene(new Scene(labirent, maze[0].length * squadSize, maze.length * squadSize));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setValue(ArrayList<Location> locations, int x, int y, int value) {
        locations.forEach(location -> {
            if (location.getX() == x && location.getY() == y) {
                location.setValue(value);
                return;
            }
        });
    }

    public int getValue(ArrayList<Location> locations, int x, int y) {
        for (Location location : locations) {
            if (location.getX() == x && location.getY() == y) {
                return location.getValue();
            }
        }
        return -1;
    }

    public ArrayList<Location> reverseMazeArray(ArrayList<Location> mazeLocations, ArrayList<Location> tempStartAndFinishList) {
        for (Location location : tempStartAndFinishList) {
            switch (location.getDirection()) {
                case START -> {
                    setValue(mazeLocations, location.getX(), location.getY(), 9);
                }
                case FINISH -> {
                    setValue(mazeLocations, location.getX(), location.getY(), 3);
                }
            }
        }
        return mazeLocations;
    }


    public void findStartAndFinishLocations(ArrayList<Location> array) {
        startAndFiniskLocations.clear();
        for (Location location : array) {
            switch (location.getValue()) {
                case 3:
                    direction = getDirection(location.getX(), location.getY(), array);
                    startAndFiniskLocations.add(new Location(location.getX(), location.getY(), Direction.START));
                    break;
                case 9:
                    startAndFiniskLocations.add(new Location(location.getX(), location.getY(), Direction.FINISH));
                    break;
            }


        }
    }


    public ArrayList<Location> solveMaze(ArrayList<Location> tempArrayList) {
        ArrayList<Location> locations = new ArrayList<>();
        Location tempLocation = null;
        for (Location location : startAndFiniskLocations) {
            switch (location.getDirection()) {
                case START -> {
                    tempLocation = location;
                }
            }
        }
        for (int i = tempLocation.getX(); i < maze.length; ) {
            for (int j = tempLocation.getY(); j < maze[0].length; ) {
                if (solved) break;
                direction = getDirection(i, j, tempArrayList);
                for (Location finisLocation : startAndFiniskLocations) {
                    switch (finisLocation.getDirection()) {
                        case FINISH -> {
                            for (Location registerLocation : locations) {
                                if (registerLocation.getY() - 1 == finisLocation.getY() && registerLocation.getX() == finisLocation.getX()) {
                                    solved = true;
                                    direction = Direction.LEFT;
                                }
                                if (registerLocation.getY() + 1 == finisLocation.getY() && registerLocation.getX() == finisLocation.getX()) {
                                    solved = true;
                                    direction = Direction.RIGHT;
                                }
                                if (registerLocation.getY() == finisLocation.getY() && registerLocation.getX() - 1 == finisLocation.getX()) {
                                    solved = true;
                                    direction = Direction.DOWN;
                                }
                                if (registerLocation.getY() == finisLocation.getY() && registerLocation.getX() + 1 == finisLocation.getX()) {
                                    solved = true;
                                    direction = Direction.UP;
                                }


                            }
                        }
                    }
                }
                if (direction != null) {
                    switch (direction) {
                        case LEFT -> {
                            j--;
                            setValue(tempArrayList, i, j, 2);
                            locations.add(new Location(i, j, direction, 2));

                        }
                        case RIGHT -> {
                            j++;
                            setValue(tempArrayList, i, j, 2);
                            locations.add(new Location(i, j, direction, 2));

                        }
                        case UP -> {
                            i--;
                            setValue(tempArrayList, i, j, 2);

                            locations.add(new Location(i, j, direction, 2));
                        }
                        case DOWN -> {
                            i++;
                            setValue(tempArrayList, i, j, 2);
                            locations.add(new Location(i, j, direction, 2));

                        }
                    }
                } else {

                    for (Location location : locations) {
                        if (location.getX() == i && location.getY() == j) {
                            location.setValue(1);
                            setValue(tempArrayList, location.getX(), location.getY(), 1);
                            switch (location.getDirection()) {
                                case LEFT -> {
                                    j++;
                                }
                                case RIGHT -> {
                                    j--;
                                }
                                case DOWN -> {
                                    i--;
                                }
                                case UP -> {
                                    i++;
                                }
                            }
                        }
                    }
                    locations.removeIf(location -> location.getValue() == 1);
                    if (locations.size() == 0)
                        j++;
                }

            }

            if (direction == null && locations.size() > 0) i++;
            if (solved) break;
        }
        if (solved) {
            System.out.println("Labirent Çözüldü");
            solved = false;
        }
        return locations;
    }

    public void drawMaze(Group group, ArrayList<Location> locations) {
        for (Location location : locations) {
            Line line = new Line();
            switch (location.getDirection()) {
                case UP -> {
                    line.setStartX(location.getY() * squadSize + squadSize / 2);
                    line.setStartY((location.getX()) * squadSize + squadSize);
                    line.setEndX(location.getY() * squadSize + squadSize / 2);
                    line.setEndY(location.getX() * squadSize + squadSize / 2);
                }
                case DOWN -> {
                    line.setStartX(location.getY() * squadSize + squadSize / 2);
                    line.setStartY(location.getX() * squadSize);
                    line.setEndX(location.getY() * squadSize + squadSize / 2);
                    line.setEndY(location.getX() * squadSize + squadSize / 2);
                }
                case RIGHT -> {
                    line.setStartX(location.getY() * squadSize);
                    line.setStartY(location.getX() * squadSize + squadSize / 2);
                    line.setEndX(location.getY() * squadSize + squadSize / 2);
                    line.setEndY(location.getX() * squadSize + squadSize / 2);
                }
                case LEFT -> {
                    line.setStartX(location.getY() * squadSize + squadSize);
                    line.setStartY((location.getX()) * squadSize + squadSize / 2);
                    line.setEndX(location.getY() * squadSize + squadSize / 2);
                    line.setEndY(location.getX() * squadSize + squadSize / 2);
                }

            }
            line.setStroke(Color.RED);
            line.setStrokeWidth(squadSize / 2);

            group.getChildren().add(line);
        }
    }

    public Direction getDirection(int i, int j, ArrayList<Location> array) {
        Direction direction = null;
        ArrayList<Location> tempLocations = new ArrayList<>();
        int mValue;
        boolean leftPriority = false, rightPriority = false, upPriority = false, downPriority = false;
        try {
            mValue = getValue(array, i, j - 1);
            if (mValue == 0) {
                for (Location finishLocation : startAndFiniskLocations) {
                    switch (finishLocation.getDirection()) {
                        case FINISH -> {
                            if (finishLocation.getY() - j < 0) {
                                leftPriority = true;
                            }
                        }
                    }
                }

                if (leftPriority)
                    direction = Direction.LEFT;
            }
            if (mValue == 9) solved = true;
            if (mValue == 0 || mValue == 9) {
                tempLocations.add(new Location(i, j, Direction.LEFT));
            }
        } catch (Exception e) {

        }
        try {
            mValue = getValue(array, i, j + 1);
            if (mValue == 0) {
                for (Location finishLocation : startAndFiniskLocations) {
                    switch (finishLocation.getDirection()) {
                        case FINISH -> {
                            if (finishLocation.getY() - j > 0) {
                                rightPriority = true;
                            }
                        }
                    }
                }
                if (rightPriority)
                    direction = Direction.RIGHT;
            }
            if (mValue == 9) solved = true;
            if (mValue == 0 || mValue == 9) {
                tempLocations.add(new Location(i, j, Direction.RIGHT));
            }
        } catch (Exception e) {

        }
        try {
            mValue = getValue(array, i - 1, j);
            if (mValue == 0) {
                for (Location finishLocation : startAndFiniskLocations) {
                    switch (finishLocation.getDirection()) {
                        case FINISH -> {
                            if (finishLocation.getX() - i < 0) {
                                upPriority = true;
                            }
                        }
                    }
                }
                if (upPriority)
                    direction = Direction.UP;
            }
            if (mValue == 9) solved = true;
            if (mValue == 0 || mValue == 9) {
                tempLocations.add(new Location(i, j, Direction.UP));
            }
        } catch (Exception e) {

        }
        try {
            mValue = getValue(array, i + 1, j);
            if (mValue == 0) {
                for (Location finishLocation : startAndFiniskLocations) {
                    switch (finishLocation.getDirection()) {
                        case FINISH -> {
                            if (finishLocation.getX() - i > 0) {
                                downPriority = true;
                            }
                        }
                    }
                }
                if (downPriority)
                    direction = Direction.DOWN;
            }
            if (mValue == 9) solved = true;
            if (mValue == 0 || mValue == 9) {
                tempLocations.add(new Location(i, j, Direction.DOWN));
            }
        } catch (Exception e) {

        }
        if (direction == null) {
            for (Location location : tempLocations) {
                direction = location.getDirection();
            }
        }

        return direction;
    }
}
