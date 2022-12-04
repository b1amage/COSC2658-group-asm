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

    public static int getColSize(String path) throws IOException {
        File file = new File(path);
        int colSize = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));

        String content;
        while ((content = br.readLine()) != null)
            colSize++; // get col


        return colSize;
    }

    public static String[] getMazeFromFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        int colSize = getColSize(path);

        String[] maze = new String[colSize];

        String content;
        int i = 0;
        while ((content = br.readLine()) != null) {
            maze[i] = content; // row
            i++; // move to next col
        }

        return maze;
    }

    public static void main(String[] args) throws IOException {
        String[] maze = getMazeFromFile("src/data/maze2.txt");
        for (String row : maze) {
            System.out.println(row);
        }
    }
}
