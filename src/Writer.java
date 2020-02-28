import java.io.*;
import java.util.*;

public class Writer {

    public Writer(String file) throws IOException {

        final String space = " ";
        final String newLine = "\n";

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        bw.write(Main.signedUpLibraries.size() + newLine);
        for(Library lib : Main.signedUpLibraries) {
            bw.write(lib.getId() + space + lib.getNumSubmittedBooks() + newLine);

            for(Map.Entry<Integer, Integer> entry : Main.submittedBooks.entrySet()) {
                if(entry.getValue() == lib.getId())
                    bw.write(entry.getKey() + space);
            }
            bw.write(newLine);
        }
        bw.close();
    }
}
