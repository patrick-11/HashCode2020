import java.util.*;

public class Library {

    private int id, numBooks, signUpDays, shipBooksPerDay, totalScore, numSubmittedBooks;
    private List<Book> books;
    private double avgValuePerBook;

    public Library(int id, int numBooks, int signUpDays, int shipBooksPerDay, List<Book> books) {
        this.id = id;
        this.numBooks = numBooks;
        this.signUpDays = signUpDays;
        this.shipBooksPerDay = shipBooksPerDay;
        this.books = books;
    }


    public int getId() {
        return id;
    }

    public int getNumBooks() {
        return numBooks;
    }

    public int getSignUpDays() {
        return signUpDays;
    }

    public int getShipBooksPerDay() {
        return shipBooksPerDay;
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public double getAvgValuePerBook() {
        return avgValuePerBook;
    }

    public int getNumSubmittedBooks() {
        return numSubmittedBooks;
    }

    public void setNumBooks(int numBooks) {
        this.numBooks = numBooks;
    }

    public void setTotalScore() {
        totalScore = 0;
        for (int i = 0; i < books.size(); i++) {
            if(!Main.submittedBooks.containsKey(books.get(i).getId()))
                totalScore += books.get(i).getScore();
        }
    }

    public void setAvgValuePerBook() {
        avgValuePerBook = (double) totalScore/numBooks;
    }

    public void setNumSubmittedBooks(int numSubmittedBooks) {
        this.numSubmittedBooks = numSubmittedBooks;
    }
}
