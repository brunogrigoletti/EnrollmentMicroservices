package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;

public class Enroll {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JButton bAdd, bCancel;
    private JComboBox<String> studentsBox, codesBox, classesBox;

    public Enroll() {
        this.window = new JFrame();
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.studentsBox = new JComboBox<>();
        this.codesBox = new JComboBox<>();
        this.classesBox = new JComboBox<>();
        this.bAdd = new JButton();
        this.bCancel = new JButton();
    }

    public void run() {
        setWindow();
        setFields();
        setButtons();
        setBoxes();
        actions();
    }

    private void setWindow() {
        this.window = new JFrame("Enroll");
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
        fieldsPanel.add(new JLabel("Student:"));
        fieldsPanel.add(studentsBox);
        fieldsPanel.add(new JLabel("Subject Code:"));
        fieldsPanel.add(codesBox);
        fieldsPanel.add(new JLabel("Class Code:"));
        fieldsPanel.add(classesBox);
    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bAdd.setText("Add");
        buttonsPanel.add(bAdd);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private void setBoxes() {
        for (String c : fetchClassCodes()) {
            classesBox.addItem(c);
        }

        for (Student s : fetchAllStudents()) {
            studentsBox.addItem(s.getRn());
        }

        for (Subject s : fetchAllSubjects()) {
            codesBox.addItem(s.getCode());
        }

        studentsBox.setSelectedIndex(-1);
        codesBox.setSelectedIndex(-1);
        classesBox.setSelectedIndex(-1);
    }

    private void resetFields() {
        studentsBox.setSelectedIndex(-1);
        codesBox.setSelectedIndex(-1);
        classesBox.setSelectedIndex(-1);
    }

    private boolean fieldsEmpty() {
        if (studentsBox.getSelectedItem() == null || codesBox.getSelectedItem() == null
            || classesBox.getSelectedItem() == null) {
                return true;
            }
        return false;
    }

    private List<Student> fetchAllStudents() { 
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8081/student/allstudent";
        return restTemplate.exchange(
            endpoint,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Student>>() {}
        ).getBody();
    }

    private List<Subject> fetchAllSubjects() { 
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8082/subject/allsubjects";
        return restTemplate.exchange(
            endpoint,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Subject>>() {}
        ).getBody();
    }

    private List<String> fetchClassCodes() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/subject/allclasscodes";
        ResponseEntity<List<String>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<String>>() {}
        );
        return response.getBody();
    }
    
    private String enrollStudent(Student student, Subject subject) {
        String url = "http://localhost:8082/subject/enroll";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("student", student);
        body.put("subject", subject);

        String requestBody = null;
        try {
            requestBody = new ObjectMapper().writeValueAsString(body);
            System.out.println("Request Body: " + requestBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Erro ao processar o JSON: " + e.getMessage();
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, request, String.class);
    }

    private Student searchStudent(String regNum) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8081/student/studentbyid/" + regNum;
        return restTemplate.getForObject(endpoint, Student.class);
    }

    private Subject searchSubject(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8082/subject/subjectbycode/" + code;
        return restTemplate.getForObject(endpoint, Subject.class);
    }

    private void actions() {
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fieldsEmpty()){
                    Student std = searchStudent(studentsBox.getSelectedItem().toString());
                    Subject sub = searchSubject(codesBox.getSelectedItem().toString());
                    String response = enrollStudent(std,sub);
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