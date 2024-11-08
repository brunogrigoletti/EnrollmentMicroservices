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

public class StudentsList {
    private JFrame window;
    private JPanel buttonPanel;
    private JButton bClose;
    private JList<String> students;

    public StudentsList() {
        this.window = new JFrame();
        this.buttonPanel = new JPanel();
        this.bClose = new JButton();
        this.students = new JList<>();
    }

    public void runAll() {
        setWindow();
        setListAll();
        setButton();
        actions();
    }

    public void runBySubject(Subject sub) {
        setWindow();
        setListBySubject(sub);
        setButton();
        actions();
    }

    private void setWindow() {
        window.setName("Students List");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(new JScrollPane(students), BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.SOUTH);
        window.setResizable(false);
        window.setVisible(true);
    }

    private void setListAll() {
        List<Student> studentList = fetchAllStudents();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Student student : studentList) {
            model.addElement("(" + student.getRn() + ") - " + student.getName());
        }
        students.setModel(model);
        students.setVisibleRowCount(10);
    }

    private void setListBySubject(Subject sub) {
        List<Student> studentsList = fetchAllStudents();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Student student : studentsList) {
            for (Subject subject : student.getSubjects()) {
                if (subject.getCode().equals(sub.getCode())) {
                    model.addElement("(" + student.getRn() + ") - " + student.getName());
                    break;
                }
            }
        }
        students.setModel(model);
        students.setVisibleRowCount(10);
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