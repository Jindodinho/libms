package miu.edu.mpp.hdk;

import miu.edu.mpp.hdk.dao.DataAccess;
import miu.edu.mpp.hdk.dao.impl.DataAccessFactory;
import miu.edu.mpp.hdk.enums.DBCollection;
import miu.edu.mpp.hdk.enums.DataAccessType;
import miu.edu.mpp.hdk.model.Book;
import miu.edu.mpp.hdk.model.LibraryMember;
import miu.edu.mpp.hdk.model.User;
import miu.edu.mpp.hdk.ui.Util;
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
