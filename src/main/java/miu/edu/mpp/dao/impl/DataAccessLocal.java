package miu.edu.mpp.dao.impl;

import miu.edu.mpp.dao.DataAccess;
import miu.edu.mpp.dao.DataConstant;
import miu.edu.mpp.enums.DBCollection;
import miu.edu.mpp.model.Author;
import miu.edu.mpp.model.Book;
import miu.edu.mpp.model.LibraryMember;
import miu.edu.mpp.model.User;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;


public final class DataAccessLocal implements DataAccess {

    static final DataAccessLocal INSTANCE = new DataAccessLocal();
    private static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/src/main/java/miu/edu/mpp/dao/storage"; //for Unix file system
    //			+ "\\src\\dataaccess\\storage"; //for Windows file system
    private static final String DATE_PATTERN = "MM/dd/yyyy";

    private DataAccessLocal() {
        this.loadBookMap(DataConstant.bookData());
        this.loadUserMap(DataConstant.userData());
        this.loadMemberMap(DataConstant.libraryMemberData());
    }

    @Override
    public HashMap<String, Author> readAuthorsMap() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Book> readBooksMap() {
        //Returns a Map with name/value pairs being
        //   isbn -> Book
        return (HashMap<String, Book>) readFromStorage(DBCollection.BOOKS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        //Returns a Map with name/value pairs being
        //   memberId -> LibraryMember
        return (HashMap<String, LibraryMember>) readFromStorage(
                DBCollection.MEMBERS);
    }


    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        //Returns a Map with name/value pairs being
        //   userId -> User
        return (HashMap<String, User>) readFromStorage(DBCollection.USERS);
    }


    @Override
    public void saveToStorage(DBCollection type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void updateToStorage(DBCollection type, Object ob) {
        saveToStorage(type, ob);
    }

    @Override
    public HashMap<String, Object> getDataCollection(DBCollection type) {
        return null;
    }
/////load methods - these place test data into the storage area
    ///// - used just once at startup


    private void loadBookMap(List<Book> bookList) {
        HashMap<String, Book> books = new HashMap<String, Book>();
        bookList.forEach(book -> books.put(book.getIsbn(), book));
        saveToStorage(DBCollection.BOOKS, books);
    }

    private void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(DBCollection.USERS, users);
    }

    private void loadMemberMap(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(DBCollection.MEMBERS, members);
    }

    private Object readFromStorage(DBCollection type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }


    final static class Pair<S, T> implements Serializable {

        @Serial
        private static final long serialVersionUID = 5399827794066637059L;
        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) return false;
            if (this == ob) return true;
            if (ob.getClass() != getClass()) return false;
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }
    }

}
