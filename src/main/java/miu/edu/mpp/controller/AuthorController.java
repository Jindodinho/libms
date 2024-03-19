package miu.edu.mpp.controller;

import miu.edu.mpp.dao.impl.DataAccessFactory;
import miu.edu.mpp.dao.DataAccess;
import miu.edu.mpp.enums.DataAccessType;
import miu.edu.mpp.model.Author;

import java.util.List;

public class AuthorController {
    private final DataAccess da = DataAccessFactory.createDataAccess(DataAccessType.MONGO);

    public AuthorController() {
    }

    public List<Author> getListAuthors(){
        return da.readAuthorsMap().values().stream().toList();
    }

}
