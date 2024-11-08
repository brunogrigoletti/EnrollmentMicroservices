package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import laitano.bruno.entities.Student;

public class SubjectsByStudent {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JButton bSearch, bCancel;
    private JComboBox<String> studentsBox;

    public SubjectsByStudent() {
        this.window = new JFrame();
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.bSearch = new JButton();
        this.bCancel = new JButton();
        this.studentsBox = new JComboBox<>();
    }

    public void run() {
        setWindow();
        setFields();
        setButtons();
        setStudentBox();
        actions();
    }

    private void setWindow() {
        window.setName("Subjects by student");
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
        fieldsPanel.add(new JLabel("Student:"));
        fieldsPanel.add(studentsBox);
    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bSearch.setText("Search");
        buttonsPanel.add(bSearch);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private void setStudentBox() {
        for (Student s : fetchAllStudents()) {
            studentsBox.addItem(s.getRn());
        }
        studentsBox.setSelectedIndex(-1);
    }

    private List<Student> fetchAllStudents() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/student/allstudent";
        ResponseEntity<List<Student>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Student>>() {}
        );
        return response.getBody();
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
                if (studentsBox.getSelectedItem() != null){
                    SubjectsList sl = new SubjectsList();
                    Student s = searchStudent(studentsBox.getSelectedItem().toString());
                    System.out.println(s.getName() + s.getSubjects());
                    if (!s.getSubjects().isEmpty()){
                        sl.runByStudent(s);
                    }
                    else {
                        JOptionPane.showMessageDialog(window, "No subjects for this student!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(window, "Choose a student!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                studentsBox.setSelectedIndex(-1);
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