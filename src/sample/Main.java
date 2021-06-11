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

import java.util.ArrayList;

public class Main extends Application {
    int squadSize = 25;
    Direction direction;
    boolean backState = false;
    boolean solved = false;
    boolean foundStartPlaceState = false;
    ArrayList<Location> locations = new ArrayList<>();
    ArrayList<Location> startAndFiniskLocations = new ArrayList<>();

    private int[][] maze =
            {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 9, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                    {1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1},
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

        primaryStage.setTitle("Labirent Çözme");
        findStartAndFinishLocations(maze);
        solveMaze(maze);
        drawMaze(labirent, locations);
        primaryStage.setScene(new Scene(labirent, maze[0].length * squadSize, maze.length * squadSize));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    public void findStartAndFinishLocations(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                switch (array[i][j]) {
                    case 3:
                        direction = getDirection(i, j, maze);
                        startAndFiniskLocations.add(new Location(i, j, Direction.START));
                        break;
                    case 9:
                        startAndFiniskLocations.add(new Location(i, j, Direction.FINISH));
                        break;
                }

            }
        }
    }


    public void solveMaze(int[][] maze) {
        int start, finish;
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
                direction = getDirection(i, j, maze);
                for (Location finisLocation : startAndFiniskLocations) {
                    switch (finisLocation.getDirection()) {
                        case FINISH -> {
                            for (Location registerLocation : locations) {
                                switch (registerLocation.getDirection()) {
                                    case LEFT -> {
                                        if (registerLocation.getY() - 1 == finisLocation.getY() && registerLocation.getX() == finisLocation.getX())
                                            solved = true;
                                    }
                                    case RIGHT -> {
                                        if (registerLocation.getY() + 1 == finisLocation.getY() && registerLocation.getX() == finisLocation.getX())
                                            solved = true;
                                    }
                                    case UP -> {
                                        if (registerLocation.getY() == finisLocation.getY() && registerLocation.getX() - 1 == finisLocation.getX())
                                            solved = true;
                                    }
                                    case DOWN -> {
                                        if (registerLocation.getY() == finisLocation.getY() && registerLocation.getX() + 1 == finisLocation.getX())
                                            solved = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (direction != null) {
                    switch (direction) {
                        case LEFT -> {
                            j--;
                            maze[i][j] = 2;
                            locations.add(new Location(i, j, direction));

                        }
                        case RIGHT -> {
                            j++;
                            maze[i][j] = 2;
                            locations.add(new Location(i, j, direction));

                        }
                        case UP -> {
                            i--;
                            maze[i][j] = 2;

                            locations.add(new Location(i, j, direction));
                        }
                        case DOWN -> {
                            i++;
                            maze[i][j] = 2;
                            locations.add(new Location(i, j, direction));

                        }
                    }
                } else {

                    if (solved) break;
                    for (Location location : locations) {
                        if (location.getX() == i && location.getY() == j) {
                            maze[i][j] = 1;
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
                    for (int i1 = tempLocation.getX(); i1 < maze.length; i1++) {
                        for (int j1 = tempLocation.getY(); j1 < maze[0].length; j1++) {
                            locations.removeIf(location -> maze[location.getX()][location.getY()] == 1);
                        }
                    }
                    if (locations.size() == 0)
                        j++;
                }

            }

            if (direction == null && locations.size() > 0) i++;
            if (solved) break;
        }
        if (solved) {
            System.out.println("Labirent Çözüldü");
        }
    }

    public void drawMaze(Group group, ArrayList<Location> locations) {
        for (Location location : locations) {
            Line line = new Line();
            switch (location.getDirection()) {
                case UP -> {
                    line.setStartX(location.getY() * squadSize+squadSize/2);
                    line.setStartY((location.getX()) * squadSize+squadSize);
                    line.setEndX(location.getY() * squadSize+squadSize/2);
                    line.setEndY(location.getX() * squadSize+squadSize/2 );
                }
                case DOWN -> {
                    line.setStartX(location.getY() * squadSize + squadSize/2);
                    line.setStartY(location.getX() * squadSize);
                    line.setEndX(location.getY() * squadSize+squadSize/2);
                    line.setEndY(location.getX() * squadSize + squadSize/2);
                }
                case RIGHT -> {
                    line.setStartX(location.getY()*squadSize);
                    line.setStartY(location.getX()*squadSize+squadSize/2);
                    line.setEndX(location.getY() * squadSize + squadSize/2);
                    line.setEndY(location.getX() * squadSize+squadSize/2);
                }
                case LEFT -> {
                    line.setStartX(location.getY() * squadSize+squadSize);
                    line.setStartY((location.getX()) * squadSize+squadSize/2);
                    line.setEndX(location.getY() * squadSize+squadSize/2);
                    line.setEndY(location.getX() * squadSize+squadSize/2 );
                }

            }
            line.setStroke(Color.RED);
            line.setStrokeWidth(squadSize / 2);

            group.getChildren().add(line);
        }
    }

    public Direction getDirection(int i, int j, int[][] array) {
        Direction direction = null;
        try {
            if (array[i][j - 1] == 0) {
                if (direction == null)
                    direction = Direction.LEFT;
            }
        } catch (Exception e) {

        }
        try {
            if (array[i][j + 1] == 0) {
                if (direction == null)
                    direction = Direction.RIGHT;
            }
        } catch (Exception e) {

        }
        try {
            if (array[i - 1][j] == 0) {
                if (direction == null)
                    direction = Direction.UP;
            }
        } catch (Exception e) {

        }
        try {
            if (array[i + 1][j] == 0) {
                if (direction == null)
                    direction = Direction.DOWN;

            }
        } catch (Exception e) {

        }

        return direction;
    }
}
