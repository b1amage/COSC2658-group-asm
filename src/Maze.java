import utility.ArrayStack;
import utility.FileScanner;

public class Maze {
    int rows;
    int cols;
    String[] map;
    int robotRow;
    int robotCol;
    int steps;

    public Maze() {
        // Note: in my real test, I will create much larger
        // and more complicated map
        /*
        rows = 4;
        cols = 5;
        map = new String[rows];
        map[0] = ".....";
        map[1] = ".   X";
        map[2] = ".   .";
        map[3] = ".....";
        robotRow = 2;
        robotCol = 1;
        steps = 0;
        */

        /*
        rows = 5;
        cols = 5;
        map = new String[rows];
        map[0] = ".....";
        map[1] = ".   .";
        map[2] = ". X .";
        map[3] = ".   .";
        map[4] = ".....";
        robotRow = 3;
        robotCol = 1;
        steps = 0;
        */

        /*
        rows = 7;
        cols = 7;
        map = new String[rows];
        map[0] = ".......";
        map[1] = ".   . .";
        map[2] = ". ... X";
        map[3] = ". ... .";
        map[4] = ". .   .";
        map[5] = ".    ..";
        map[6] = ".......";
        robotRow = 4;
        robotCol = 1;
        steps = 0;
         */

        /*
        rows = 3;
        cols = 6;
        map = new String[rows];
        map[0] = "......";
        map[1] = ".   X.";
        map[2] = "......";
        robotRow = 1;
        robotCol = 1;
        steps = 0;
         */

        /*
        rows = 7;
        cols = 7;
        map = new String[rows];
        map[0] = ".......";
        map[1] = ".     .";
        map[2] = "..   ..";
        map[3] = "..   ..";
        map[4] = ".X    .";
        map[5] = ".     .";
        map[6] = ".......";
        robotRow = 4;
        robotCol = 2;
        steps = 0;
         */

        try {
            map = FileScanner.getMazeFromFile("src/data/maze14.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        robotRow = 1;
        robotCol = 1;
        steps = 0;
    }

    public String go(String direction) {
        if (!direction.equals("UP") &&
                !direction.equals("DOWN") &&
                !direction.equals("LEFT") &&
                !direction.equals("RIGHT")) {
            // invalid direction
            steps++;
            return "false";
        }
        int currentRow = robotRow;
        int currentCol = robotCol;
        if (direction.equals("UP")) {
            currentRow--;
        } else if (direction.equals("DOWN")) {
            currentRow++;
        } else if (direction.equals("LEFT")) {
            currentCol--;
        } else {
            currentCol++;
        }

        // check the next position
        if (map[currentRow].charAt(currentCol) == 'X') {
            // Exit gate
            steps++;
            System.out.println(direction); // print the direction taken
            System.out.println("Steps to reach the Exit gate " + steps);
            return "win";
        } else if (map[currentRow].charAt(currentCol) == '.') {
            // Wall
            steps++;
            System.out.println(direction); // print the direction taken
            return "false";
        } else {
            // Space => update robot location
            steps++;
            System.out.println(direction); // print the direction taken
            robotRow = currentRow;
            robotCol = currentCol;

            return "true";
        }
    }

    public static void main(String[] args) {
        (new Robot()).navigate();
    }
}

class Robot {
    final int MAX_COL = 1000;
    final int MAX_ROW = 1000;
    final String[] directions = new String[]{"UP", "DOWN", "LEFT", "RIGHT"};
    private final int[][] visited = new int[2 * MAX_ROW + 1][2 * MAX_COL + 1];
    private int currentRow = MAX_ROW;
    private int currentCol = MAX_COL;
    private String currentDirection = "UP";
    ArrayStack<String> steps = new ArrayStack<>(4 * MAX_ROW * MAX_COL);

    private boolean isVisited(String direction) {
        int checkedRow = currentRow, checkedCol = currentCol;
        switch (direction) {
            case "UP" -> checkedRow = currentRow - 1;
            case "DOWN" -> checkedRow = currentRow + 1;
            case "LEFT" -> checkedCol = currentCol - 1;
            case "RIGHT" -> checkedCol = currentCol + 1;
        }

        if (visited[checkedRow][checkedCol] == 1) return true;
        visited[checkedRow][checkedCol] = 1;
        return false;
    }

    private void updateLocation(String direction) {
        switch (direction) {
            case "UP" -> currentRow--;
            case "DOWN" -> currentRow++;
            case "LEFT" -> currentCol--;
            case "RIGHT" -> currentCol++;
        }
    }

    private String checkNextStep(Maze maze) {
        String result;
        if (!isVisited(currentDirection)) {
            result = maze.go(currentDirection);
            if (result.equals("win")) {
                return result;
            }
            if (result.equals("true")) {
                updateLocation(currentDirection);
                steps.push(currentDirection);
                return result;
            }
        }

        for (String direction : directions) {
            if (!direction.equals(currentDirection)) {
                if (isVisited(direction)) continue;
                result = maze.go(direction);
                if (result.equals("true")) {
                    currentDirection = direction;
                    updateLocation(currentDirection);
                    steps.push(direction);
                    return result;
                }

                if (result.equals("win")) return result;
            }
        }

        return "false";
    }

    private void backtrack(Maze maze) {
        currentDirection = steps.peek();
        steps.pop();
        String backDirection = "";
        switch (currentDirection) { // go opposite direction to backtrack
            case "UP" -> backDirection = "DOWN";
            case "DOWN" -> backDirection = "UP";
            case "LEFT" -> backDirection = "RIGHT";
            case "RIGHT" -> backDirection = "LEFT";
        }

        maze.go(backDirection);
        updateLocation(backDirection);
    }

    public void navigate() {
        Maze maze = new Maze();
        visited[currentRow][currentCol] = 1;

        String result = checkNextStep(maze);
        while (!result.equals("win")) {
            result = checkNextStep(maze);
            if (result.equals("false")) {
                System.out.println("backtrack");
                backtrack(maze);
            }
        }
    }
}