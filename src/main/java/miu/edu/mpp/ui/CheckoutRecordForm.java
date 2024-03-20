package miu.edu.mpp.ui;

import miu.edu.mpp.controller.MemberController;
import miu.edu.mpp.controller.SystemController;
import miu.edu.mpp.model.LibraryMember;

import javax.swing.*;
import java.util.List;

public class CheckoutRecordForm extends MainForm {

    private JPanel mainPanel;
    private JTextArea areTitle;
    private JComboBox<LibraryMember> memberIdDropdown;

    private final MemberController memberController;

    public CheckoutRecordForm(SystemController system) {
        super(system);
        memberController = new MemberController();
        memberIdDropdown.addActionListener(e -> {
            LibraryMember member = (LibraryMember) memberIdDropdown.getSelectedItem();
            if (areTitle != null) {
                this.areTitle.setText(memberController.printCheckoutRecord(member));
            }
        });
        refresh();
    }

    @Override
    public JPanel getContent() {
        return mainPanel;
    }

    public void refresh() {
        if (areTitle != null) {
            this.areTitle.setText(memberController.printCheckoutRecord());
        }
        if (memberIdDropdown != null) {
            memberIdDropdown.removeAllItems();
            List<LibraryMember> members = memberController.getListMember();
            members.forEach(memberIdDropdown::addItem);
        }
    }
}
