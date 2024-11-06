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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import laitano.bruno.entities.Student;

public class NewStudent {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JTextField nameField, documentField, addressField;
    private JButton bAdd, bCancel;

    public NewStudent() {
        this.window = new JFrame();
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
        window.setResizable(false);
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

    private void resetFields() {
        nameField.setText("");
        documentField.setText("");
        addressField.setText("");
    }

    private boolean fieldsEmpty() {
        if (nameField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty()
            || documentField.getText().trim().isEmpty()) {
                return true;
            }
        return false;
    }

    private String registerStudent(Student student) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8081/student/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> request = new HttpEntity<>(student, headers);
        return restTemplate.postForObject(endpoint, request, String.class);
    }

    private void actions() {
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fieldsEmpty()){
                    Student newStudent = new Student(nameField.getText(),
                    addressField.getText(),documentField.getText());
                    String response = registerStudent(newStudent);
                    JOptionPane.showMessageDialog(window, response);
                    resetFields();
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