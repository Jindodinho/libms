package miu.edu.mpp.hdk.model;

import miu.edu.mpp.hdk.enums.Auth;

import java.io.Serial;
import java.io.Serializable;

final public class User extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 5147265048973262104L;

    private String id;

    private String password;
    private Auth authorization;

    public User() {
    }

    public User(String id, String pass, Auth auth) {
        this.id = id;
        this.password = pass;
        this.authorization = auth;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Auth getAuthorization() {
        return authorization;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorization(Auth authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "[" + id + ":" + password + ", " + authorization + "]";
    }

}
