import java.io.*;
import java.util.*;

public class Reader {

    public Reader(String file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] tokens = br.readLine().split(" ");
        Main.totalBooks = Integer.parseInt(tokens[0]);
        Main.totalLibraries = Integer.parseInt(tokens[1]);
        Main.totalScanningDays = Integer.parseInt(tokens[2]);

        tokens = br.readLine().split(" ");
        for(int i = 0; i < tokens.length; i++) {
            Main.scoreOfBooks.add(new Book(i, Integer.parseInt(tokens[i])));
        }
        String line;
        for(int id = 0; (line = br.readLine()) != null; id++) {

            tokens = line.split(" ");
            int numBooks = Integer.parseInt(tokens[0]);
            int signUpDays = Integer.parseInt(tokens[1]);
            int shipBooksPerDay = Integer.parseInt(tokens[2]);

            List<Book> books = new ArrayList<>();
            tokens = br.readLine().split(" ");
            for(int i = 0; i < tokens.length; i++) {
                books.add(new Book(Integer.parseInt(tokens[i]), Main.scoreOfBooks.get(Integer.parseInt(tokens[i])).getScore()));
            }

            Library library = new Library(id, numBooks, signUpDays, shipBooksPerDay, books);
            Main.libraries.add(library);
        }

        br.close();
    }
}
