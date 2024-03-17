package main.controller;

import main.dao.DataAccess;
import main.dao.impl.DataAccessFactory;
import main.enums.DataAccessType;
import main.model.Author;
import main.model.Book;

import java.util.List;

public class AuthorController {
    private final DataAccess da = DataAccessFactory.createDataAccess(DataAccessType.MONGO);

    public AuthorController() {
    }

    public List<Author> getListAuthors(){
        return da.readAuthorsMap().values().stream().toList();
    }

}
