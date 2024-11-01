package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import laitano.bruno.entities.Subject;

public class NewSubject {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JTextField codeField, nameField, classField;
    private JButton bAdd, bCancel;
    private JComboBox<String> scheduleBox;

    public NewSubject() {
        this.window = new JFrame();
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.codeField = new JTextField();
        this.nameField = new JTextField();
        this.scheduleBox = new JComboBox<>();
        this.classField = new JTextField();
        this.bAdd = new JButton();
        this.bCancel = new JButton();
    }

    public void run() {
        setWindow();
        setFields();
        setButtons();
        setScheduleBox();
        actions();
    }

    private void setWindow() {
        this.window = new JFrame("New Subject");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(fieldsPanel, BorderLayout.CENTER);
        window.add(buttonsPanel, BorderLayout.SOUTH);
        window.setResizable(false);
        window.setVisible(true);
    }

    private void setFields() {
        fieldsPanel.setLayout(new GridLayout(4, 2, 10, 10));
        fieldsPanel.add(new JLabel("Code:"));
        codeField.setColumns(10);
        fieldsPanel.add(codeField);
        fieldsPanel.add(new JLabel("Subject:"));
        nameField.setColumns(10);
        fieldsPanel.add(nameField);
        fieldsPanel.add(new JLabel("Schedule Code:"));
        fieldsPanel.add(scheduleBox);
        fieldsPanel.add(new JLabel("Class Code:"));
        classField.setColumns(10);
        fieldsPanel.add(classField);

    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bAdd.setText("Add");
        buttonsPanel.add(bAdd);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private void setScheduleBox() {
        scheduleBox.addItem("A");
        scheduleBox.addItem("B");
        scheduleBox.addItem("C");
        scheduleBox.addItem("D");
        scheduleBox.addItem("E");
        scheduleBox.addItem("F");
        scheduleBox.addItem("G");
        scheduleBox.setSelectedIndex(-1);
    }

    private void resetFields() {
        codeField.setText("");
        nameField.setText("");
        scheduleBox.setSelectedIndex(-1);
        classField.setText("");
    }

    private boolean fieldsEmpty() {
        if (codeField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()
            || scheduleBox.getSelectedItem() == null || classField.getText().trim().isEmpty()) {
                return true;
            }
        return false;
    }


    public String registerSubject(Subject subject) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8082/subject/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Subject> request = new HttpEntity<>(subject, headers);
        return restTemplate.postForObject(endpoint, request, String.class);
    }

    private void actions() {
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fieldsEmpty()){
                    String boxOption = scheduleBox.getSelectedItem().toString();
                    Subject newSubject = new Subject(codeField.getText(),
                    nameField.getText(),boxOption,classField.getText());
                    String response = registerSubject(newSubject);
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