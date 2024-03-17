package main.model;

import java.time.LocalDate;
import java.util.Date;

public class CheckoutRecord {

    private LibraryMember member;
    private Book book;
    private String checkoutBy;
    private LocalDate checkoutDate;
    private LocalDate checkoutDueDate;

    public CheckoutRecord() {
    }

    public CheckoutRecord(LibraryMember member, Book book, String checkoutBy) {
        this.member = member;
        this.book = book;
        this.checkoutBy = checkoutBy;
        LocalDate localDate= LocalDate.now();
        this.checkoutDate = localDate;
        this.checkoutDueDate = localDate.plusDays(book.getMaxCheckoutLength());
    }

    public LibraryMember getMember() {
        return member;
    }

    public void setMember(LibraryMember member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getCheckoutDueDate() {
        return checkoutDueDate;
    }

    public void setCheckoutDueDate(LocalDate checkoutDueDate) {
        this.checkoutDueDate = checkoutDueDate;
    }

    public String print(){
        return "member=" + member.getMemberId() +
                ", book=" + book.getIsbn() +
                ",\ncheckoutDate=" + checkoutDate+
                ",\ndueDate=" + checkoutDueDate;
    }

    @Override
    public String toString() {
        return "CheckoutRecord{" +
                "member=" + member +
                ", book=" + book +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + checkoutDueDate +

                '}';
    }
}
