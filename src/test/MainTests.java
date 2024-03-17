package test;

import main.dao.DataAccess;
import main.dao.impl.DataAccessFactory;
import main.enums.DBCollection;
import main.enums.DataAccessType;
import main.model.Book;
import main.model.LibraryMember;
import main.model.User;
import main.ui.Util;
import org.junit.Test;

import java.util.HashMap;

public class MainTests {

    DataAccess data = DataAccessFactory.createDataAccess(DataAccessType.MONGO);

    @Test
    public void testConnectionDB(){
        HashMap<String, User> users = data.readUserMap();
        users.values().forEach(System.out::println);
        HashMap<String, LibraryMember> members = data.readMemberMap();
        members.values().forEach(System.out::println);
        HashMap<String, Book> books = data.readBooksMap();
        books.values().forEach(System.out::println);
    }
    @Test
    public void testUpdateBook(){
        HashMap<String, Book> books = data.readBooksMap();
        Book book = books.get("23-11451");
        book.setTitle(book.getTitle() + " updated");
//        data.updateToStorage(DBCollection.BOOKS, book);
    }
    @Test
    public void testCopyBook(){
        HashMap<String, Book> books = data.readBooksMap();
        Book book = books.get("23-11451");
        System.out.println(book.getCopies().size());
//        book.addCopy();
//        data.updateToStorage(DBCollection.BOOKS, book);
    }

    @Test
    public void testGenerateID(){
        for (int i=0; i< 1000; i++){
            System.out.println(Util.generateId());
        }
    }
}
