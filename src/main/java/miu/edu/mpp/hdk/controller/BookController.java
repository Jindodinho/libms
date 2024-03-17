package miu.edu.mpp.hdk.controller;

import miu.edu.mpp.hdk.dao.DataAccess;
import miu.edu.mpp.hdk.dao.impl.DataAccessFactory;
import miu.edu.mpp.hdk.enums.DBCollection;
import miu.edu.mpp.hdk.enums.DataAccessType;
import miu.edu.mpp.hdk.model.Author;
import miu.edu.mpp.hdk.model.Book;
import miu.edu.mpp.hdk.model.BookCopy;

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
