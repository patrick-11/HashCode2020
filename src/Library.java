import java.util.*;

public class Library {

    private int id, numBooks, signUpDays, shipBooksPerDay, totalScore, numSubmittedBooks;
    private List<Book> books;

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

    public int getNumSubmittedBooks() {
        return numSubmittedBooks;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setNumSubmittedBooks(int numSubmittedBooks) {
        this.numSubmittedBooks = numSubmittedBooks;
    }
}
