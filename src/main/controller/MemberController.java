package main.controller;

import main.dao.DataAccess;
import main.dao.impl.DataAccessFactory;
import main.enums.DBCollection;
import main.enums.DataAccessType;
import main.model.CheckoutRecord;
import main.model.LibraryMember;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class MemberController {
    private final DataAccess da = DataAccessFactory.createDataAccess(DataAccessType.FACADE);

    public List<LibraryMember> getListMember() {
        return da.readMemberMap().values().stream().toList();
    }

    public void checkout(CheckoutRecord record) {
        da.saveToStorage(DBCollection.CHECKOUT_RECORD, record);
    }

    public String printCheckoutRecord() {
        StringJoiner joiner = new StringJoiner("\n");
        HashMap<String, Object> map = da.getDataCollection(DBCollection.CHECKOUT_RECORD);
        map.values().forEach(ob -> {
            CheckoutRecord record = (CheckoutRecord) ob;
            joiner.add(record.print());
            joiner.add("======================================");
        });
        return joiner.toString();
    }

    public boolean addNewMember(LibraryMember libraryMember) {
        try {
            da.saveToStorage(DBCollection.MEMBERS, libraryMember);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean updateMember(LibraryMember libraryMember) {
        try {
            da.updateToStorage(DBCollection.MEMBERS, libraryMember);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
}
