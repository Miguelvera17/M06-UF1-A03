import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Entrada {              // Class to allow reading user input from the console.
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static String readLine() {
        try {
            String line = reader.readLine();
            if (line == null) {
                throw new RuntimeException();
            }
            return line;
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }
}
