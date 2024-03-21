package miu.edu.mpp;

import miu.edu.mpp.controller.SystemController;
import miu.edu.mpp.enums.Auth;
import miu.edu.mpp.enums.MenuItem;
import miu.edu.mpp.ui.AddBookCopyForm;
import miu.edu.mpp.ui.AddNewBookForm;
import miu.edu.mpp.ui.AddNewMemberForm;
import miu.edu.mpp.ui.CheckoutBookForm;
import miu.edu.mpp.ui.CheckoutRecordForm;
import miu.edu.mpp.ui.LoginForm;
import miu.edu.mpp.ui.MainForm;
import miu.edu.mpp.ui.UpdateExistMemberForm;
import miu.edu.mpp.ui.Util;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.Serial;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryWorkshopApplication extends JFrame {

    @Serial
    private static final long serialVersionUID = -1L;

    JPanel cardDeck;
    JList<String> linkList;
    JSplitPane splitPane;
    JLabel message = new JLabel("Welcome to the MPP Library!");
    Map.Entry<Integer, String> currentMenu = Map.entry(0, MenuItem.LOGIN.getLabel());
    JPanel footer;
    SystemController mainController;
    LinkedHashMap<String, MainForm> menus;

    LibraryWorkshopApplication() {
        mainController = SystemController.INSTANCE;
        mainController.setApplication(this);
        menus = new LinkedHashMap<>() {{
            put(MenuItem.LOGIN.getLabel(), new LoginForm(mainController));
            put(MenuItem.CHECKOUT_BOOK.getLabel(), new CheckoutBookForm(mainController));
            put(MenuItem.ADD_NEW_BOOK.getLabel(), new AddNewBookForm(mainController));
            put(MenuItem.ADD_BOOK_COPY.getLabel(), new AddBookCopyForm(mainController));
            put(MenuItem.ADD_NEW_MEMBER.getLabel(), new AddNewMemberForm(mainController));
            put(MenuItem.UPDATE_EXIST_MEMBER.getLabel(), new UpdateExistMemberForm(mainController));
            put(MenuItem.CHECKOUT_RECORD.getLabel(), new CheckoutRecordForm(mainController));
        }};
        setTitle("MPP Library");
        initializeWindow();
        setSize(600, 450);
        setUpCards();
        initLeftMenu();
        splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,    //Orientation
                linkList,                       //left side
                cardDeck                        //right side
        );
        splitPane.setDividerLocation(200);
        splitPane.setBorder(BorderFactory.createCompoundBorder());
        this.add(splitPane, BorderLayout.CENTER);
        this.initFooter();
        centreOnDesktop(this);
    }

    private void initLeftMenu() {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(menus.keySet());
        this.linkList = new JList<>(model);
        this.linkList.setCellRenderer(this.renderLeftMenuList(mainController.currentAuth));
        this.linkList.addListSelectionListener((event) -> {
            this.message.setText("");
            String item = linkList.getSelectedValue();
            if (authMenu(mainController.currentAuth).contains(item)) {
                currentMenu = Map.entry(linkList.getLeadSelectionIndex(), item);
                selectMenu(item);
            } else {
                linkList.setSelectedIndex(currentMenu.getKey());
                selectMenu(currentMenu.getValue());
            }
        });
    }

    private DefaultListCellRenderer renderLeftMenuList(Auth role) {
        return new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof String item) {
                    this.setText(item);
                    if (authMenu(role).contains(item)) {
                        this.setForeground(Util.LINK_AVAILABLE);
                    } else {
                        this.setForeground(Util.LINK_NOT_AVAILABLE);
                    }
                    if (isSelected) {
                        this.setForeground(Color.BLACK);
                        this.setBackground(new Color(236, 243, 245));
                    }
                }
                return component;
            }
        };
    }

    private Set<String> authMenu(Auth role) {
        if (role.equals(Auth.BOTH)) {
            return menus.keySet();
        }
        if (role.equals(Auth.ADMIN)) {
            return menus.keySet().stream().filter(k ->
                    k.equals(MenuItem.LOGIN.getLabel()) ||
                            k.equals(MenuItem.ADD_NEW_BOOK.getLabel()) ||
                            k.equals(MenuItem.ADD_BOOK_COPY.getLabel()) ||
                            k.equals(MenuItem.ADD_NEW_MEMBER.getLabel()) ||
                            k.equals(MenuItem.UPDATE_EXIST_MEMBER.getLabel())
            ).collect(Collectors.toSet());
        }
        if (role.equals(Auth.LIBRARIAN)) {
            return menus.keySet().stream().filter(k ->
                    k.equals(MenuItem.LOGIN.getLabel()) ||
                            k.equals(MenuItem.CHECKOUT_BOOK.getLabel()) ||
                            k.equals(MenuItem.CHECKOUT_RECORD.getLabel()) ||
                            k.equals(MenuItem.ADD_NEW_MEMBER.getLabel())
            ).collect(Collectors.toSet());

        }
        return Collections.singleton(MenuItem.LOGIN.getLabel());
    }

    private void initFooter() {
        this.footer = new JPanel();
        this.message.setForeground(Util.DARK_BLUE);
        this.message.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.footer.add(message);
        this.footer.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(footer, BorderLayout.SOUTH);
    }

    public void error(String message) {
        this.message.setForeground(Util.ERROR_MESSAGE_COLOR);
        this.message.setText(message);
    }

    public void info(String message) {
        this.message.setForeground(Util.INFO_MESSAGE_COLOR);
        this.message.setText(message);
    }

    public void refresh() {
        menus.values().forEach(MainForm::refresh);
    }

    public void auth(Auth role) {
        this.linkList.setCellRenderer(this.renderLeftMenuList(role));
    }

    private void centreOnDesktop(Component component) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        component.setLocation(
                (toolkit.getScreenSize().width - component.getWidth()) / 2,
                (toolkit.getScreenSize().height - component.getHeight()) / 2
        );
    }

    private void setUpCards() {
        cardDeck = new JPanel(new CardLayout());
        menus.forEach((key, value) -> cardDeck.add(key, value.getContent()));
    }

    public void selectMenu(String name) {

        CardLayout cl = (CardLayout) (cardDeck.getLayout());
        cl.show(this.cardDeck, name);
    }

    private void initializeWindow() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(LibraryWorkshopApplication::new);
    }
}