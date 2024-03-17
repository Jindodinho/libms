package miu.edu.mpp.hdk.model;

import java.io.Serial;
import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
    private String memberId;

    public LibraryMember() {
        super();
    }

    public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
        super(fname, lname, tel, add);
        this.memberId = memberId;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName();
    }

    @Serial
    private static final long serialVersionUID = -2226197306790714013L;
}
