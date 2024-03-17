package main.exception;

import java.io.Serial;
import java.io.Serializable;

public class DataAccessException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 3326915348398932420L;

    public DataAccessException(String msg) {
        super(msg);
    }

}
