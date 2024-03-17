package main.controller;

import main.LibraryWorkshopApplication;
import main.dao.DataAccess;
import main.dao.impl.DataAccessFactory;
import main.enums.Auth;
import main.enums.DataAccessType;
import main.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {

    public static final SystemController INSTANCE = new SystemController();
    private final DataAccess da = DataAccessFactory.createDataAccess(DataAccessType.MONGO);
    public Auth currentAuth = Auth.ANONYMOUS;
    public User user = new User();
    private LibraryWorkshopApplication application;

    public void login(String username, String password) {
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(username)) {
            this.error("ID " + username + " not found");
            return;
        }
        String passwordFound = map.get(username).getPassword();
        if (!passwordFound.equals(password)) {
            this.error("Password incorrect");
            return;
        }
        this.user = map.get(username);
        this.currentAuth = map.get(username).getAuthorization();
        this.info("Login successful");
        this.application.auth(currentAuth);
    }

    @Override
    public List<String> allMemberIds() {
        return new ArrayList<>(da.readMemberMap().keySet());
    }

    @Override
    public List<String> allBookIds() {
        return new ArrayList<>(da.readBooksMap().keySet());
    }

    public void setApplication(LibraryWorkshopApplication application) {
        this.application = application;
    }

//    public void refresh() {
//        if (currentAuth.equals(Auth.BOTH) || currentAuth.equals(Auth.LIBRARIAN)) {
//            StringJoiner joiner = new StringJoiner("\n");
//            HashMap<String, LibraryMember> members = da.readMemberMap();
//            members.values().forEach(m -> joiner.add(m.toString()));
//            printCheckoutForm.setTitleTxtArea(joiner.toString());
//        } else {
//            printCheckoutForm.setTitleTxtArea("");
//        }
//        addNewBookForm.cleanText();
//    }

    public void error(String msg) {
        this.application.error(msg);
    }

    public void info(String msg) {
        this.application.info(msg);
    }

    public void refresh() {
        this.application.refresh();
    }
}
