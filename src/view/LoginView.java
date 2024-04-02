package view;

import core.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_password;

    public LoginView() {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(400, 400);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize()));

        this.setVisible(true);

        // Login butonuna basıldığında username ve password girilmiş mi kontrol eder, uyarı verir.
        btn_login.addActionListener(e -> {
            JTextField[] checkedList = {this.fld_username, this.fld_password};
            if (Helper.isFieldListEmpty(checkedList)) {
                Helper.showMessage("fill");
            } else {
                Helper.showMessage("done");
            }
        });
    }
}
