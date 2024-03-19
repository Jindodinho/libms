package miu.edu.mpp.ui;

import miu.edu.mpp.controller.SystemController;

import javax.swing.JPanel;

public abstract class MainForm {

    protected final SystemController system;

    protected MainForm(SystemController system) {
        this.system = system;
    }

    public abstract JPanel getContent();
    public abstract void refresh();

}
