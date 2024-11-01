package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewStudent {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JTextField nameField, documentField, addressField;
    private JButton bAdd, bCancel;

    public NewStudent() {
        this.window = new JFrame("Enrollment System - New Student");
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.nameField = new JTextField();
        this.documentField = new JTextField();
        this.addressField = new JTextField();
        this.bAdd = new JButton();
        this.bCancel = new JButton();
    }

    public void run() {
        setWindow();
        setFields();
        setButtons();
        actions();
    }

    private void setWindow() {
        this.window = new JFrame("New Student");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(fieldsPanel, BorderLayout.CENTER);
        window.add(buttonsPanel, BorderLayout.SOUTH);
        window.setVisible(true);
    }

    private void setFields() {
        fieldsPanel.setLayout(new GridLayout(3, 2, 10, 10));
        fieldsPanel.add(new JLabel("Name:"));
        nameField.setColumns(10);
        fieldsPanel.add(nameField);
        fieldsPanel.add(new JLabel("Document:"));
        documentField.setColumns(10);
        fieldsPanel.add(documentField);
        fieldsPanel.add(new JLabel("Address:"));
        addressField.setColumns(10);
        fieldsPanel.add(addressField);
    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bAdd.setText("Add");
        buttonsPanel.add(bAdd);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private void actions() {
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
    }
}