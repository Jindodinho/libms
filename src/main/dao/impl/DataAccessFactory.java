package main.dao.impl;

import main.dao.DataAccess;
import main.enums.DataAccessType;
import main.exception.DataAccessException;

public class DataAccessFactory {

    private static DataAccessFacade facade;

    public static DataAccess createDataAccess(DataAccessType accessType) {
        if (accessType.equals(DataAccessType.FACADE)) {
            if (facade == null) {
                facade = DataAccessFacade.INSTANCE;
            }
            return facade;
        }
        throw new DataAccessException("Data access not supported!");
    }

    public static DataAccessFacade defaultDataAccess() {
        return (DataAccessFacade) createDataAccess(DataAccessType.FACADE);
    }
}
