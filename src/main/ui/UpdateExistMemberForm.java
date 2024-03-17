package main.ui;

import main.controller.MemberController;
import main.controller.SystemController;
import main.model.LibraryMember;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdateExistMemberForm extends MainForm {
    private final MemberController memberController;

    private JPanel mainPanel;
    private JButton btnSaveMember;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtTelephone;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtState;
    private JTextField txtZipCode;
    private JComboBox<LibraryMember> cbbMemberList;
    private JLabel lblErrorMsg;

    public UpdateExistMemberForm(SystemController system) {
        super(system);

        memberController = new MemberController();

        this.refresh();
        if(btnSaveMember==null){
            btnSaveMember = new JButton();
        }
        btnSaveMember.addActionListener(e -> {
            LibraryMember member = (LibraryMember) cbbMemberList.getSelectedItem();
            if (member == null)
                return;

            member.setFirstName(txtFirstName.getText());
            member.setLastName(txtLastName.getText());
            member.setTelephone(txtTelephone.getText());
            member.getAddress().setStreet(txtStreet.getText());
            member.getAddress().setCity(txtCity.getText());
            member.getAddress().setState(txtState.getText());
            member.getAddress().setZip(txtZipCode.getText());

            if (memberController.updateMember(member)) {
                system.error("Member updated!");
                system.refresh();
            } else {
                system.error("Could not update member!");
            }
        });
        if(cbbMemberList==null){
            cbbMemberList = new JComboBox<>();
        }
        cbbMemberList.addActionListener(e -> {
            LibraryMember member = (LibraryMember) cbbMemberList.getSelectedItem();
            if (member == null)
                return;

            txtFirstName.setText(member.getFirstName());
            txtLastName.setText(member.getLastName());
            txtTelephone.setText(member.getTelephone());
            txtStreet.setText(member.getAddress().getStreet());
            txtCity.setText(member.getAddress().getCity());
            txtState.setText(member.getAddress().getState());
            txtZipCode.setText(member.getAddress().getZip());
        });
    }

    public void clearFields() {
        txtFirstName.setText("");
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
        if(cbbMemberList!=null){
            cbbMemberList.removeAllItems();
            List<LibraryMember> members = memberController.getListMember();
            members.forEach(cbbMemberList::addItem);

        }
    }
}
