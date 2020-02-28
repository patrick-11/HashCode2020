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

    public void setNumSubmittedBooks() {
        numSubmittedBooks++;
    }

    public void setTotalScore() {
        for (int i = 0; i < books.size(); i++) {
            totalScore += books.get(i).getScore();
        }
    }

    public void setAvgValuePerBook() {
        avgValuePerBook = (double) totalScore/numBooks;
    }
}
