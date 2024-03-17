package main.model;

import java.io.Serial;
import java.io.Serializable;

final public class Author extends Person implements Serializable {
    private String bio;

    public Author() {
        super();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Author(String f, String l, String t, Address a, String bio) {
        super(f, l, t, a);
        this.bio = bio;
    }

    @Serial
    private static final long serialVersionUID = 7508481940058530471L;

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
