package miu.edu.mpp.controller;

import miu.edu.mpp.exception.LoginException;

import java.util.List;

public interface ControllerInterface {
    public void login(String id, String password) throws LoginException;

    public List<String> allMemberIds();

    public List<String> allBookIds();

}
