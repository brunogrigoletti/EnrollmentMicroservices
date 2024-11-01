package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class StudentByNamePart {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JTextField nameField;
    private JButton bSearch, bCancel;

    public StudentByNamePart() {
        this.window = new JFrame();
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.nameField = new JTextField();
        this.bSearch = new JButton();
        this.bCancel = new JButton();
    }

    public void run() {
        setWindow();
        setFields();
        setButtons();
        actions();
    }

    private void setWindow() {
        window.setName("Student by name");
        window.setSize(300, 150);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(fieldsPanel, BorderLayout.CENTER);
        window.add(buttonsPanel, BorderLayout.SOUTH);
        window.setResizable(false);
        window.setVisible(true);
    }

    private void setFields() {
        fieldsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        fieldsPanel.add(new JLabel("Name:"));
        nameField.setColumns(15);
        fieldsPanel.add(nameField);
    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bSearch.setText("Search");
        buttonsPanel.add(bSearch);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private void actions() {
        bSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameField.getText().trim().isEmpty()){
                    NamePartList npl = new NamePartList(nameField.getText().toLowerCase());
                    npl.run();
                    nameField.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(window, "Field is empty!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
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