package miu.edu.mpp.hdk.dao.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import miu.edu.mpp.hdk.dao.DataAccess;
import miu.edu.mpp.hdk.dao.DataConstant;
import miu.edu.mpp.hdk.enums.DBCollection;
import miu.edu.mpp.hdk.model.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.HashMap;

import static com.mongodb.client.model.Filters.eq;
import static miu.edu.mpp.hdk.enums.DBCollection.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public final class DataAccessMongo implements DataAccess {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "library-workshop";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    static final DataAccessMongo INSTANCE = new DataAccessMongo();

    private final MongoDatabase database;
//    private final CodecRegistry pojoCodecRegistry;

    private DataAccessMongo() {
        // setting pojo mapping
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // Creating a Mongo client
        MongoClient mongo = new MongoClient(HOST, PORT);

        // Creating Credentials
        MongoCredential credential = MongoCredential.createCredential(USER, DB_NAME, PASSWORD.toCharArray());
        System.out.println("Connected to the database successfully");
        // Accessing the database
        database = mongo.getDatabase(DB_NAME).withCodecRegistry(pojoCodecRegistry);
        System.out.println("Credentials ::" + credential);
        resetDB();
    }

    private void resetDB() {
        for (String name : database.listCollectionNames()) {
            // Retrieving a collection
            MongoCollection<Document> collection = database.getCollection(name);
            // Dropping a Collection
            collection.drop();
            System.out.printf("Collection %s dropped successfully%n", name);
        }
        for (DBCollection collection : DBCollection.values()) {
            database.createCollection(collection.name());
        }
        MongoCollection<Book> books = database.getCollection(BOOKS.name(), Book.class);
        books.insertMany(DataConstant.bookData());
        System.out.printf("Collection %s created successfully%n", BOOKS);

        MongoCollection<LibraryMember> members = database.getCollection(MEMBERS.name(), LibraryMember.class);
        members.insertMany(DataConstant.libraryMemberData());
        System.out.printf("Collection %s created successfully%n", MEMBERS);

        MongoCollection<User> users = database.getCollection(USERS.name(), User.class);
        users.insertMany(DataConstant.userData());
        System.out.printf("Collection %s created successfully%n", USERS);


        MongoCollection<Author> authors = database.getCollection(AUTHORS.name(), Author.class);
        authors.insertMany(DataConstant.getAllAuthors());
        System.out.printf("Collection %s created successfully%n", AUTHORS);
    }

    @Override
    public HashMap<String, Author> readAuthorsMap() {
        HashMap<String, Author> map = new HashMap<>();
        MongoCollection<Author> collections = database.getCollection(AUTHORS.name(), Author.class);
        FindIterable<Author> iterDoc = collections.find();
        for (Author i : iterDoc) {
            map.put(i.getFirstName(), i);
        }
        return map;
    }

    @Override
    public HashMap<String, Book> readBooksMap() {
        HashMap<String, Book> map = new HashMap<>();
        MongoCollection<Book> collections = database.getCollection(BOOKS.name(), Book.class);
        FindIterable<Book> iterDoc = collections.find();
        for (Book i : iterDoc) {
            map.put(i.getIsbn(), i);
        }
        return map;
    }

    @Override
    public HashMap<String, User> readUserMap() {
        HashMap<String, User> map = new HashMap<>();
        MongoCollection<User> collections = database.getCollection(USERS.name(), User.class);
        FindIterable<User> iterDoc = collections.find();
        for (User i : iterDoc) {
            map.put(i.getId(), i);
        }
        return map;
    }

    @Override
    public HashMap<String, LibraryMember> readMemberMap() {
        HashMap<String, LibraryMember> map = new HashMap<>();
        MongoCollection<LibraryMember> collections = database.getCollection(MEMBERS.name(), LibraryMember.class);
        FindIterable<LibraryMember> iterDoc = collections.find();
        for (LibraryMember i : iterDoc) {
            map.put(i.getMemberId(), i);
        }
        return map;
    }

    @Override
    public void saveToStorage(DBCollection type, Object ob) {
        switch (type) {
            case CHECKOUT_RECORD -> {
                MongoCollection<CheckoutRecord> collections = database.getCollection(type.name(), CheckoutRecord.class);
                collections.insertOne((CheckoutRecord) ob);
            }
            case USERS -> {
                MongoCollection<User> collections = database.getCollection(type.name(), User.class);
                collections.insertOne((User) ob);
            }
            case BOOKS -> {
                MongoCollection<Book> collections = database.getCollection(type.name(), Book.class);
                collections.insertOne((Book) ob);
            }
            case MEMBERS -> {
                MongoCollection<LibraryMember> collections = database.getCollection(type.name(), LibraryMember.class);
                collections.insertOne((LibraryMember) ob);
            }
            default -> {
            }
        }
    }

    @Override
    public void updateToStorage(DBCollection type, Object ob) {

        switch (type) {
            case BOOKS -> {
                Book newOb = (Book) ob;
                MongoCollection<Book> collections = database.getCollection(type.name(), Book.class);
                collections.replaceOne(eq("isbn", newOb.getIsbn()), newOb);

            }
            case MEMBERS -> {
                LibraryMember newOb = (LibraryMember) ob;
                MongoCollection<LibraryMember> collections = database.getCollection(type.name(), LibraryMember.class);
                collections.replaceOne(eq("memberId", newOb.getMemberId()), newOb);
            }
            default -> {
            }
        }
    }

    @Override
    public HashMap<String, Object> getDataCollection(DBCollection type) {
        HashMap<String, Object> map = new HashMap<>();
        switch (type) {
            case CHECKOUT_RECORD -> {
                MongoCollection<CheckoutRecord> collections = database.getCollection(type.name(), CheckoutRecord.class);
                FindIterable<CheckoutRecord> iterDoc = collections.find();
                for (CheckoutRecord i : iterDoc) {
                    map.put(i.toString(), i);
                }
                return map;
            }
            case USERS -> {
                MongoCollection<User> collections = database.getCollection(type.name(), User.class);
                FindIterable<User> iterDoc = collections.find();
                for (User i : iterDoc) {
                    map.put(i.getId(), i);
                }
                return map;
            }
            case BOOKS -> {
                MongoCollection<Book> collections = database.getCollection(type.name(), Book.class);
                FindIterable<Book> iterDoc = collections.find();
                for (Book i : iterDoc) {
                    map.put(i.getIsbn(), i);
                }
                return map;
            }
            case MEMBERS -> {
                MongoCollection<LibraryMember> collections = database.getCollection(type.name(), LibraryMember.class);
                FindIterable<LibraryMember> iterDoc = collections.find();
                for (LibraryMember i : iterDoc) {
                    map.put(i.getMemberId(), i);
                }
                return map;
            }
            default -> {
                return map;
            }
        }
    }
}
