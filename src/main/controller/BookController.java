package main.controller;

import main.dao.DataAccess;
import main.dao.impl.DataAccessFactory;
import main.enums.DBCollection;
import main.enums.DataAccessType;
import main.model.Author;
import main.model.Book;
import main.model.BookCopy;

import java.util.ArrayList;
import java.util.List;

public class BookController {

    private final DataAccess da = DataAccessFactory.createDataAccess(DataAccessType.MONGO);

    public List<Book> getListBook(){
        return da.readBooksMap().values().stream().toList();
    }

    public void updateBook(Book book){
        da.updateToStorage(DBCollection.BOOKS, book);
    }

    public boolean checkIsbn(String isbn) {
        List<String> allBookIsbn = new ArrayList<>(da.readBooksMap().keySet());
        return allBookIsbn.contains(isbn);
    }

    public Book createBook(String isbn, String title, List<Author> lsAuthors) {
        Book book = new Book(isbn, title, 10, lsAuthors);
        book.addCopy();
        return book;
    }

    public boolean saveBook(Book book) {
        try {
            da.saveToStorage(DBCollection.BOOKS, book);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
}
