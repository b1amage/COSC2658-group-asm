import utility.ArrayStack;

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
            System.out.println("Steps to reach the Exit gate " + steps);
            return "win";
        } else if (map[currentRow].charAt(currentCol) == '.') {
            // Wall
            steps++;
            return "false";
        } else {
            // Space => update robot location
            steps++;
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
    ArrayStack<String> steps = new ArrayStack<>();

    private boolean isVisited(String direction) {
        int updatedRow = currentRow, updatedCol = currentCol;
        switch (direction) {
            case "UP" -> updatedRow = currentRow + 1;
            case "DOWN" -> updatedRow = currentRow - 1;
            case "LEFT" -> updatedCol = currentCol - 1;
            case "RIGHT" -> updatedCol = currentCol + 1;
        }

        if (visited[updatedRow][updatedCol] == 1) return true;

        currentRow = updatedRow;
        currentCol = updatedCol;
        visited[currentRow][currentCol] = 1;
        return false;
    }

    private String checkNextStep(Maze maze) {
        String result;
        if (!isVisited(currentDirection)) {
            result = maze.go(currentDirection);
            if (result.equals("win")) return result;
            if (result.equals("true")) {
                steps.push(currentDirection);
                return result;
            }
        }

        for (String direction : directions) {
            if (!direction.equals(currentDirection)) {
                if (isVisited(direction)) continue;
                result = maze.go(direction);
                if (result.equals("true")) {
                    steps.push(direction);
                    currentDirection = direction;
                    return result;
                }
            }
        }

        return "false";
    }

    private void backtrack() {

    }

    public void navigate() {
        Maze maze = new Maze();
        visited[currentRow][currentCol] = 1;

        String result = checkNextStep(maze);
        while (!result.equals("win")) {
            result = checkNextStep(maze);
            if (result.equals("false")) {

            }
        }
    }
}