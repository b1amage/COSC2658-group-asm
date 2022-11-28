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
}
