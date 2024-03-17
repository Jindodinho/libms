package main.ui;

import main.controller.MemberController;
import main.controller.SystemController;
import main.model.Address;
import main.model.LibraryMember;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewMemberForm extends MainForm {
    private final MemberController memberController;
    private JPanel mainPanel;
    private JButton btnAddMember;
    private JTextField txtFistName;
    private JTextField txtLastName;
    private JTextField txtTelephone;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtState;
    private JTextField txtZipCode;
    private JLabel lblErrorMsg;

    public AddNewMemberForm(SystemController system) {
        super(system);
        memberController = new MemberController();
        if(btnAddMember == null){
            btnAddMember = new JButton();
        }
        btnAddMember.addActionListener(e -> {
            String firstName = txtFistName.getText();
            String lastName = txtLastName.getText();
            String telephone = txtTelephone.getText();
            String street = txtStreet.getText();
            String city = txtCity.getText();
            String state = txtState.getText();
            String zipCode = txtZipCode.getText();
            if(isAnyEmpty(firstName, lastName,telephone, street,city, state,zipCode )){
                system.error("All fields must be nonempty");
                return;
            }
            Address address = new Address(street, city, state, zipCode);

            LibraryMember member = new LibraryMember(Util.generateId(), firstName, lastName, telephone, address);
            if (memberController.addNewMember(member)) {
                clearFields();
                system.refresh();
                system.info("Member added!");
            } else {
                system.error("Could not add member!");
            }
        });
    }

    private boolean isAnyEmpty(String ... strs){
        for (String s : strs){
            if(s.isBlank())
            return true;
        }
        return false;
    }

    public void clearFields() {
        txtFistName.setText("");
        txtLastName.setText("");
        txtTelephone.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtState.setText("");
        txtZipCode.setText("");
    }

    @Override
    public JPanel getContent() {
        return mainPanel;
    }

    @Override
    public void refresh() {

    }
}
