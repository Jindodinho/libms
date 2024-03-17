package miu.edu.mpp.hdk.controller;

import miu.edu.mpp.hdk.dao.DataAccess;
import miu.edu.mpp.hdk.dao.impl.DataAccessFactory;
import miu.edu.mpp.hdk.enums.DataAccessType;
import miu.edu.mpp.hdk.model.Author;
import miu.edu.mpp.hdk.model.Book;

import java.util.List;

public class AuthorController {
    private final DataAccess da = DataAccessFactory.createDataAccess(DataAccessType.MONGO);

    public AuthorController() {
    }

    public List<Author> getListAuthors(){
        return da.readAuthorsMap().values().stream().toList();
    }

}
