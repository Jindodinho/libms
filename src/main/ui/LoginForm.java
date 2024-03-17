package main.ui;

import main.controller.SystemController;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends MainForm {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPanel mainPanel;
    private JButton btnLogin;

    public LoginForm(SystemController system) {
        super(system);
        if(btnLogin == null){
            btnLogin = new JButton();
        }
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            char[] password = txtPassword.getPassword();
            if (username.isBlank() || password == null) {
                system.error("Id and Password fields must be nonempty");
                return;
            }
            system.login(username, new String(password));
        });
    }

    @Override
    public JPanel getContent() {
        return mainPanel;
    }

    @Override
    public void refresh() {

    }

}
