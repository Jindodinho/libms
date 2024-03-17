package main.dao;

import main.enums.DBCollection;
import main.model.Author;
import main.model.Book;
import main.model.LibraryMember;
import main.model.User;

import java.util.HashMap;

public interface DataAccess {
	HashMap<String, Author> readAuthorsMap();
	HashMap<String, Book> readBooksMap();
	HashMap<String, User> readUserMap();
	HashMap<String, LibraryMember> readMemberMap();
	void saveToStorage(DBCollection type, Object ob);
	void updateToStorage(DBCollection type, Object ob);
	HashMap<String, Object> getDataCollection(DBCollection type);
}
