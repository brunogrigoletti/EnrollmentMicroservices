package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;

public class SubjectsList {
    private JFrame window;
    private JPanel buttonPanel;
    private JButton bClose;
    private JList<String> subjects;

    public SubjectsList() {
        this.window = new JFrame();
        this.buttonPanel = new JPanel();
        this.bClose = new JButton();
        this.subjects = new JList<>();
    }

    public void runAll() {
        setWindow();
        setListAll();
        setButton();
        actions();
    }

    public void runByStudent(Student std) {
        setWindow();
        setListByStudent(std);
        setButton();
        actions();
    }

    private void setWindow() {
        window.setName("Subjects List");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(new JScrollPane(subjects), BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.SOUTH);
        window.setResizable(false);
        window.setVisible(true);
    }

    private void setListAll() {
        List<Subject> subjectsList = fetchAllSubjects();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Subject subject : subjectsList) {
            model.addElement("(" + subject.getCode() + ") - " + subject.getName());
        }
        subjects.setModel(model);
        subjects.setVisibleRowCount(10);
    }

    private void setListByStudent(Student std) {
        List<Subject> subjectsList = fetchAllSubjects();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Subject subject : subjectsList) {
            for (Student student : subject.getStudents()) {
                if (student.getRn().equals(std.getRn())) {
                    System.out.println(subject.getCode() + subject.getName());
                    model.addElement("(" + subject.getCode() + ") - " + subject.getName());
                    break;
                }
            }
        }
        subjects.setModel(model);
        subjects.setVisibleRowCount(10);
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

    private void setButton() {
        bClose.setText("Close");
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 10));
        buttonPanel.add(bClose);
    }

    private void actions() {
        bClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
    }
}