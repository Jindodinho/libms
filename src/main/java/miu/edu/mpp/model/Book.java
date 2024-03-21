package miu.edu.mpp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
final public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 6110690276685962829L;
    private List<BookCopy> copies;
    private List<Author> authors;
    private String isbn;
    private String title;
    private int maxCheckoutLength;

    public Book() {
    }

    public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.maxCheckoutLength = maxCheckoutLength;
        this.authors = Collections.unmodifiableList(authors);
        this.copies = List.of(new BookCopy(this, 1, true));
    }

    public void updateCopies(BookCopy copy) {
        for (int i = 0; i < copies.size(); ++i) {
            BookCopy c = copies.get(i);
            if (c.equals(copy)) {
                copies.set(i, copy);
            }
        }
    }

    public List<Integer> getCopyNums() {
        List<Integer> retVal = new ArrayList<>();
        for (BookCopy c : copies) {
            retVal.add(c.getCopyNum());
        }
        return retVal;

    }

    public void addCopy() {
        BookCopy[] newArr = new BookCopy[copies.size() + 1];
        System.arraycopy(copies.toArray(), 0, newArr, 0, copies.size());
        newArr[copies.size()] = new BookCopy(this, copies.size() + 1, true);
        copies = Arrays.asList(newArr);
    }


    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Book b = (Book) ob;
        return b.isbn.equals(isbn);
    }


    public boolean isAvailable() {
        if (copies == null) {
            return false;
        }
        for (BookCopy bookCopy : copies) {
            if (bookCopy.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "isbn: " + isbn + ", title: " + title + ", available: " + isAvailable();
    }

    public int getNumCopies() {
        return copies.size();
    }

    public String getTitle() {
        return title;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookCopy getNextAvailableCopy() {
        Optional<BookCopy> optional
                = copies.stream()
                .filter(BookCopy::isAvailable).findFirst();
        return optional.orElse(null);
    }

    public BookCopy getCopy(int copyNum) {
        for (BookCopy c : copies) {
            if (copyNum == c.getCopyNum()) {
                return c;
            }
        }
        return null;
    }

    public int getMaxCheckoutLength() {
        return maxCheckoutLength;
    }


    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMaxCheckoutLength(int maxCheckoutLength) {
        this.maxCheckoutLength = maxCheckoutLength;
    }
}
