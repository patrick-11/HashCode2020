import java.io.*;
import java.util.*;

public class Main {

    static int totalBooks, totalLibraries, totalScanningDays;
    static List<Book> scoreOfBooks = new ArrayList<>();
    static List<Library> libraries = new ArrayList<>();

    static List<Library> signedUpLibraries = new ArrayList<>();
    static LinkedHashMap<Integer, Integer> submittedBooks = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        //Choose file: a, b, c, d, e, f
        final String file = "a.txt";

        //Read input file
        new Reader("input/" + file);

        //Set totalScore and avgValuePerBook;
        for(Library lib : libraries) {
            lib.setTotalScore();
            lib.setAvgValuePerBook();
        }

        //Sort libraries by:
        //libraries.sort(Comparator.comparingDouble(Library::getAvgValuePerBook).reversed());
        //libraries.sort(Comparator.comparingInt(Library::getShipBooksPerDay).reversed());
        libraries.sort(Comparator.comparingInt(Library::getTotalScore).reversed());
        libraries.sort(Comparator.comparingInt(Library::getSignUpDays));

        //Start process of scanning
        int dayOfNextSignUp = 0;
        Library library = null;
        for(int i = 0; i < totalScanningDays; i++) {
            System.out.println("Day: " + i);

            if(i == dayOfNextSignUp) {
                //Add library to signedUpLibraries List after signUp
                if(library != null) {
                    library.getBooks().sort(Comparator.comparingInt(Book::getScore).reversed());
                    signedUpLibraries.add(library);
                    libraries.remove(library);
                }

                while(libraries.size() > 0) {
                    library = libraries.get(0);
                    libraries.remove(library);

                    //Check if library fits in the remaining scanning days
                    if(library.getSignUpDays() < (totalScanningDays - dayOfNextSignUp)) {
                        //Add days for the next library signUp
                        dayOfNextSignUp += library.getSignUpDays();
                        break;
                    }
                }
            }

            for(int j = 0; j < signedUpLibraries.size(); j++) {
                Library lib = signedUpLibraries.get(j);

                for(int d = 0; d < lib.getShipBooksPerDay(); d++) {
                    if(lib.getBooks().size() <= 0)
                        break;

                    Book book = lib.getBooks().get(0);

                    if(!submittedBooks.containsKey(book.getId())) {
                        //System.out.println(lib.getId() + " " + book.getId());
                        submittedBooks.put(book.getId(), lib.getId());
                        lib.setNumSubmittedBooks(lib.getNumSubmittedBooks() + 1);
                    }
                    else {
                        //Decrement by one "book" because we did not find a book to submit
                        d--;
                    }
                    lib.getBooks().remove(book);
                }
            }
        }

        //Write output file
        new Writer("output/" + file);

        //Prints score of the Algorithm
        int score = 0;
        for (Map.Entry<Integer, Integer> entry : submittedBooks.entrySet()) {
            score += scoreOfBooks.get(entry.getKey()).getScore();
        }
        System.out.println("Total Score: " + score);

    }
}
