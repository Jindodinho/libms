package main.enums;

public enum MenuItem {

    LOGIN("Login"),
    CHECKOUT_BOOK("Checkout Book"),
    ADD_NEW_BOOK("New Book"),
    ADD_BOOK_COPY("Book Copy"),
    ADD_NEW_MEMBER("New Member"),
    UPDATE_EXIST_MEMBER("Update Member"),
    CHECKOUT_RECORD("Checkout Record"),
    ;

    private final String label;

    MenuItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
