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
import org.springframework.web.client.RestTemplate;
import laitano.bruno.entities.Student;

public class StudentById {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JTextField codeField;
    private JButton bSearch, bCancel;

    public StudentById() {
        this.window = new JFrame();
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.codeField = new JTextField();
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
        window.setName("Student by ID");
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
        fieldsPanel.add(new JLabel("Registration Number:"));
        codeField.setColumns(15);
        fieldsPanel.add(codeField);
    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bSearch.setText("Search");
        buttonsPanel.add(bSearch);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private Student searchStudent(String regNum) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8081/student/studentbyid/" + regNum;
        return restTemplate.getForObject(endpoint, Student.class);
    }

    private void actions() {
        bSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!codeField.getText().trim().isEmpty()){
                    Student std = searchStudent(codeField.getText());
                    String studentInfo = String.format("Registration Number: %s\nName: %s\nAddress: %s",
                        std.getRn(), std.getName(), std.getAddress());
                    JOptionPane.showMessageDialog(window, studentInfo, "Student Information", JOptionPane.INFORMATION_MESSAGE);
                    codeField.setText("");
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