package miu.edu.mpp.dao;

import miu.edu.mpp.enums.DBCollection;
import miu.edu.mpp.model.Author;
import miu.edu.mpp.model.Book;
import miu.edu.mpp.model.LibraryMember;
import miu.edu.mpp.model.User;

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
