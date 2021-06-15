package sample;

import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;

public class MazeSolveModel {
    boolean solved = false;
    int squadSize = 20;
    Direction direction;
    boolean backState = false;
    boolean foundStartPlaceState = false;

    ArrayList<Location> finalLocations = new ArrayList<>();
    ArrayList<Location> mazeLocations = new ArrayList<>();
    ArrayList<Location> startAndFiniskLocations = new ArrayList<>();
    public void drawMaze(Group group, int[][] array, int size) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(size);
                rectangle.setHeight(size);
                rectangle.setX(j * size);
                rectangle.setY(i * size);
                switch (array[i][j]) {
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
                group.getChildren().add(rectangle);
            }
        }
    }

    public void setArrayList(ArrayList<Location> arrayList, int[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                arrayList.add(new Location(i, j, maze[i][j]));
            }
        }
    }

    public void setValue(ArrayList<Location> locations, int x, int y, int value) {
        locations.forEach(location -> {
            if (location.getX() == x && location.getY() == y) {
                location.setValue(value);
                return;
            }
        });
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
    public ArrayList<Location> solveMaze(ArrayList<Location> tempArrayList,int [][] mazeArray) {
        ArrayList<Location> locations = new ArrayList<>();
        Location tempLocation = null;
        for (Location location : startAndFiniskLocations) {
            switch (location.getDirection()) {
                case START -> {
                    tempLocation = location;
                }
            }
        }
        for (int i = tempLocation.getX(); i < mazeArray.length; ) {
            for (int j = tempLocation.getY(); j < mazeArray[0].length; ) {
                if (solved) break;
                direction = getDirection(i, j, tempArrayList);
                if (solved) {
                    for (Location finisLocation : startAndFiniskLocations) {
                        switch (finisLocation.getDirection()) {
                            case FINISH -> {
                                for (Location registerLocation : locations) {
                                    if (registerLocation.getY() - 1 == finisLocation.getY() && registerLocation.getX() == finisLocation.getX()) {
                                        direction = Direction.LEFT;
                                    }
                                    if (registerLocation.getY() + 1 == finisLocation.getY() && registerLocation.getX() == finisLocation.getX()) {
                                        direction = Direction.RIGHT;
                                    }
                                    if (registerLocation.getY() == finisLocation.getY() && registerLocation.getX() + 1 == finisLocation.getX()) {
                                        direction = Direction.DOWN;
                                    }
                                    if (registerLocation.getY() == finisLocation.getY() && registerLocation.getX() - 1 == finisLocation.getX()) {
                                        direction = Direction.UP;
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
            System.out.println("Maze Solved");
            solved = false;
        }
        return locations;
    }
    public int getValue(ArrayList<Location> locations, int x, int y) {
        for (Location location : locations) {
            if (location.getX() == x && location.getY() == y) {
                return location.getValue();
            }
        }
        return -1;
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
    public void drawPathInMaze(Group group, ArrayList<Location> locations) {
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
                tempLocations.add(new Location(i, j, Direction.LEFT));

                if (leftPriority && !rightPriority && !downPriority && !upPriority)
                    direction = Direction.LEFT;
            }
            if (mValue == 9) solved = true;

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
                tempLocations.add(new Location(i, j, Direction.RIGHT));

                if (rightPriority && !leftPriority && !upPriority && !downPriority)
                    direction = Direction.RIGHT;
            }
            if (mValue == 9) solved = true;
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
                tempLocations.add(new Location(i, j, Direction.UP));

                if (upPriority && !leftPriority && !rightPriority && !downPriority)
                    direction = Direction.UP;
            }
            if (mValue == 9) solved = true;

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
                tempLocations.add(new Location(i, j, Direction.DOWN));

                if (downPriority && !leftPriority && !rightPriority && !upPriority)
                    direction = Direction.DOWN;
            }
            if (mValue == 9) solved = true;

        } catch (Exception e) {

        }
        if (direction == null) {
            for (Location location : tempLocations) {
                direction = location.getDirection();
            }
        }
        return direction;
    }
    public void startAnimation(Group group, ArrayList<Location> locations, int circleSize) {
        var path = new Path();
        PathTransition pathTransition = new PathTransition();

        var circle = new Circle(Double.parseDouble(String.valueOf(locations.get(0).getY() * circleSize + circleSize / 2)), Double.parseDouble(String.valueOf(locations.get(0).getX() * squadSize + squadSize / 2)), circleSize / 2);

        circle.setFill(Color.ORANGE);
        MoveTo move = new MoveTo(locations.get(0).getY() * circleSize, locations.get(0).getX() * circleSize);
        path.getElements().add(move);
        for (Location location : locations) {
            LineTo line = new LineTo(location.getY() * circleSize + circleSize / 2, location.getX() * circleSize + circleSize / 2);
            path.getElements().add(line);
        }
        pathTransition.setNode(circle);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setPath(path);
        pathTransition.play();
        group.getChildren().addAll(circle);


    }
}
