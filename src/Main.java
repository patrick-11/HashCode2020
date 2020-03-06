import java.io.*;
import java.util.*;

public class Main {

    static int totalBooks, totalLibraries, totalScanningDays;
    static List<Book> scoreOfBooks = new ArrayList<>();
    static List<Library> libraries = new ArrayList<>();

    static List<Library> signedUpLibraries = new ArrayList<>();
    static LinkedHashMap<Integer, Integer> submittedBooks = new LinkedHashMap<>();

    private static int comparator1(Library l1, Library l2) {
        double v1 = (double) l1.getTotalScore()/l1.getSignUpDays();
        double v2 = (double) l2.getTotalScore()/l2.getSignUpDays();
        if(v1 == v2)
            return 0;
        else if(v1 < v2)
            return 1;
        else
            return -1;
    }

    private static int comparator2(Library l1, Library l2) {
        double v1 = (double) (l1.getTotalScore()*l1.getShipBooksPerDay())/l1.getSignUpDays();
        double v2 = (double) (l2.getTotalScore()*l2.getShipBooksPerDay())/l2.getSignUpDays();
        if(v1 == v2)
            return 0;
        else if(v1 < v2)
            return 1;
        else
            return -1;
    }

    public static void main(String[] args) throws IOException {

        //Choose file: a, b, c, d, e, f
        final String file = "a.txt";

        //Read input file
        new Reader("input/" + file);

        //Set totalScore and avgValuePerBook;
        for(Library lib : libraries) {
            for(Book book : lib.getBooks()) {
                lib.setTotalScore(lib.getTotalScore() + book.getScore());
            }
        }

        //Sort libraries by:
        //libraries.sort(Comparator.comparingInt(Library::getShipBooksPerDay).reversed());
        //libraries.sort(Comparator.comparingInt(Library::getSignUpDays));
        //libraries.sort(Comparator.comparingInt(Library::getTotalScore).reversed());

        //Best for file: a, b, c, d, f
        //libraries.sort(Main::comparator1);

        //Best for file: a, b, d, e,
        //libraries.sort(Main::comparator2);

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
                }

                while(!libraries.isEmpty()) {
                    library = libraries.get(0);
                    libraries.remove(0);

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
                    if(lib.getBooks().isEmpty())
                        break;

                    Book book = lib.getBooks().get(0);

                    if(!submittedBooks.containsKey(book.getId())) {
                        submittedBooks.put(book.getId(), lib.getId());
                        lib.setNumSubmittedBooks(lib.getNumSubmittedBooks() + 1);
                    }
                    else {
                        //Decrement by one "book" because we did not find a book to submit
                        d--;
                    }
                    lib.getBooks().remove(0);
                }
            }
        }

        //Remove libraries with no submitted books
        List<Library> librariesToRemove = new ArrayList<>();
        for(Library lib : signedUpLibraries) {
            if(lib.getNumSubmittedBooks() == 0)
                librariesToRemove.add(lib);
        }
        for(Library lib : librariesToRemove) {
            signedUpLibraries.remove(lib);
        }

        //Write output file
        new Writer("output/" + file);

        //Prints score of the algorithm
        int score = 0;
        for (Map.Entry<Integer, Integer> entry : submittedBooks.entrySet()) {
            score += scoreOfBooks.get(entry.getKey()).getScore();
        }
        System.out.println("Total Score: " + score);
    }
}
