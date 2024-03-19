package miu.edu.mpp.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable {

    @Serial
    private static final long serialVersionUID = -63976228084869815L;
    private String isbn;
    private int copyNum;
    private boolean isAvailable;

    public BookCopy() {
    }

    BookCopy(Book book, int copyNum, boolean isAvailable) {
        this.isbn = book.getIsbn();
        this.copyNum = copyNum;
        this.isAvailable = isAvailable;
    }

    BookCopy(Book book, int copyNum) {
        this.isbn = book.getIsbn();
        this.copyNum = copyNum;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCopyNum(int copyNum) {
        this.copyNum = copyNum;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getCopyNum() {
        return copyNum;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void changeAvailability() {
        isAvailable = !isAvailable;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (!(ob instanceof BookCopy copy)) return false;
        return copy.getIsbn().equals(isbn) && copy.copyNum == copyNum;
    }

}
