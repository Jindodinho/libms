package main.ui;

import main.controller.BookController;
import main.controller.MemberController;
import main.controller.SystemController;
import main.model.Book;
import main.model.BookCopy;
import main.model.CheckoutRecord;
import main.model.LibraryMember;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.util.List;

public class CheckoutBookForm extends MainForm {

    private final MemberController memberController;
    private final BookController bookController;
    private JPanel mainPanel;
    private JComboBox<LibraryMember> comboMember;
    private JComboBox<Book> comboBook;
    private JButton btnCheckout;

    public CheckoutBookForm(SystemController system) {
        super(system);
        memberController = new MemberController();
        bookController = new BookController();
        this.refresh();
        if(btnCheckout==null){
            btnCheckout = new JButton();
        }
        btnCheckout.addActionListener(e -> {
            LibraryMember member = (LibraryMember) comboMember.getSelectedItem();
            Book book = (Book) comboBook.getSelectedItem();
            if (book == null || !book.isAvailable()) {
                system.error("Book is not available!");
            } else {
                List<BookCopy> copies = book.getCopies();
                for (BookCopy copy : copies) {
                    if (copy.isAvailable()) {
                        copy.setAvailable(false);
                        break;
                    }
                }
                book.setCopies(copies);
                memberController.checkout(new CheckoutRecord(member, book, system.user.getId()));
                bookController.updateBook(book);
                system.info("Checkout Book Successfully!");
                system.refresh();
            }
        });
    }

    @Override
    public JPanel getContent() {
        return mainPanel;
    }

    @Override
    public void refresh() {
        if (comboMember != null) {
            comboMember.removeAllItems();
            List<LibraryMember> members = memberController.getListMember();
            members.forEach(comboMember::addItem);
        }
        if (comboBook != null) {
            comboBook.removeAllItems();
            List<Book> books = bookController.getListBook();
            books.forEach(comboBook::addItem);

        }
    }
}
