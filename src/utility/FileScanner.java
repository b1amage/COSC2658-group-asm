package utility;
import java.io.*;

public class FileScanner {

    private FileScanner() {}

    public static void readFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String content;
        while ((content = br.readLine()) != null) {
            // Print the string
            System.out.println(content);
        }
    }

    public static int[] getMazeSizes(String path) throws IOException {
        File file = new File(path);
        int colSize = 0;
        int rowSize = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));

        String content;
        while ((content = br.readLine()) != null) {
            if (rowSize == 0) rowSize = content.length(); // get row
            colSize++; // get col
        }

        return new int[] {colSize, rowSize};
    }

    public static char[][] getMazeFromFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        // get size for 2d array
        int[] sizes = getMazeSizes(path);
        int colSize = sizes[0];
        int rowSize = sizes[1];

        char[][] maze = new char[colSize][rowSize];

        String content;
        int i = 0;
        while ((content = br.readLine()) != null) {
            maze[i] = content.toCharArray(); // row
            i++; // move to next col
        }

        return maze;
    }
}
