package miu.edu.mpp.hdk.ui;

import miu.edu.mpp.hdk.controller.MemberController;
import miu.edu.mpp.hdk.controller.SystemController;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CheckoutRecordForm extends MainForm {

    private JPanel mainPanel;
    private JTextArea areTitle;
    private final MemberController memberController;

    public CheckoutRecordForm(SystemController system) {
        super(system);
        memberController = new MemberController();
        refresh();
    }

    @Override
    public JPanel getContent() {
        return mainPanel;
    }

    public void refresh() {
        if(areTitle!=null){
            this.areTitle.setText(memberController.printCheckoutRecord());
        }
    }
}
