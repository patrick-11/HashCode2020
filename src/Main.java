import java.io.*;
import java.util.*;

public class Main {

    static int totalBooks, totalLibraries, totalScanningDays;
    static List<Book> scoreOfBooks = new ArrayList<>();
    static List<Library> libraries = new ArrayList<>();

    static List<Library> signedUpLibraries = new ArrayList<>();
    static LinkedHashMap<Integer, Integer> submittedBooks = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        final String file = "b.txt";

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
            //System.out.println("Day: " + i);

            if(i == dayOfNextSignUp) {
                //Add library to signedUpLibraries List after signUp
                if(library != null) {
                    libraries.remove(library);
                    library.getBooks().sort(Comparator.comparingInt(Book::getScore).reversed());
                    signedUpLibraries.add(library);
                }

                //Get next library
                if(libraries.size() > 0) {
                    library = libraries.get(0);
                }

                //Find library that fits in the remaining scanning days
                while(library != null && library.getSignUpDays() > (totalScanningDays - dayOfNextSignUp)) {
                    libraries.remove(library);
                    if(libraries.size() > 0)
                        library = libraries.get(0);
                    else
                        library = null;
                }

                //Add days for the next library signUp
                if(library != null) {
                    dayOfNextSignUp += library.getSignUpDays();
                }
            }

            for(int j = 0; j < signedUpLibraries.size(); j++) {
                Library lib = signedUpLibraries.get(j);

                for(int d = 0; d < lib.getShipBooksPerDay(); d++) {
                    if(lib.getBooks().size() <= 0)
                        break;

                    Book book = lib.getBooks().get(0);

                    if(!submittedBooks.containsKey(book.getId())) {
                        submittedBooks.put(book.getId(), lib.getId());
                        lib.setNumSubmittedBooks();
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
