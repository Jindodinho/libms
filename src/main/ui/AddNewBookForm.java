package main.ui;

import main.controller.AuthorController;
import main.controller.BookController;
import main.controller.SystemController;
import main.model.Author;
import main.model.Book;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddNewBookForm extends MainForm {
    private final BookController bookController = new BookController();
    private final AuthorController authorController = new AuthorController();

    private JPanel mainPanel;
    private JTextField txtBookIsbn;
    private JTextField txtBookTitle;
    private JButton btnAddBook;
    private JComboBox<Author> cbbAuthors;
    private JLabel lblErrorMsg;

    public AddNewBookForm(SystemController system) {
        super(system);

        refresh();
        if (btnAddBook == null) {
            btnAddBook = new JButton();
        }
        btnAddBook.addActionListener(e -> {
            String isbn = txtBookIsbn.getText().trim();
            String title = txtBookTitle.getText().trim();

            Author author = (Author) cbbAuthors.getSelectedItem();
            if (author == null)
                return;
            List<Author> lsAuthors = new ArrayList<>();
            lsAuthors.add(author);

            if (isbn.isBlank() || title.isBlank()) {
                system.error("All fields must be nonempty");
                return;
            }

            // check ISBN existence
            if (bookController.checkIsbn(isbn)) {
                system.error("Book with same ISBN is already in DB!");
                return;
            }

            // request controller to create Book & its copy
            Book book = bookController.createBook(isbn, title, lsAuthors);
            if (bookController.saveBook(book)) {
                system.info("Book is created successfully");
                refresh();
                system.refresh();
            } else {
                system.error("Could not create book");
            }
        });

        if(cbbAuthors==null){
            cbbAuthors = new JComboBox<>();
        }
        cbbAuthors.addActionListener(e -> {

        });
    }

    @Override
    public JPanel getContent() {
        return mainPanel;
    }

    @Override
    public void refresh() {
        txtBookIsbn.setText("");
        txtBookTitle.setText("");
        if (cbbAuthors != null) {
            cbbAuthors.removeAllItems();
            List<Author> authors = authorController.getListAuthors();
            authors.forEach(cbbAuthors::addItem);
        }
    }
}
