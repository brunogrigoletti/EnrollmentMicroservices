package laitano.bruno.windows;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;

public class MainWindow {
    private JFrame window;
    private JPanel buttons;
    private JButton b1, b2, b3, b4, b5, b6, b7, b8;

    public MainWindow() {
        this.window = new JFrame("Enrollment System");
        this.buttons = new JPanel();
        this.b1 = new JButton();
        this.b2 = new JButton();
        this.b3 = new JButton();
        this.b4 = new JButton();
        this.b5 = new JButton();
        this.b6 = new JButton();
        this.b7 = new JButton();
        this.b8 = new JButton();
    }

    public void run() {
        setWindow();
        setButtons();
        actions();
    }

    private void setWindow() {
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(buttons,BorderLayout.CENTER);
        window.setResizable(false);
        window.setVisible(true);
    }

    private void setButtons() {
        buttons.setLayout(new GridLayout(4, 2, 10, 10));
        b1.setText("New student");
        buttons.add(b1);
        b2.setText("New subject");
        buttons.add(b2);
        b3.setText("Add student to subject");
        buttons.add(b3);
        b4.setText("Students list");
        buttons.add(b4);
        b5.setText("Student by ID");
        buttons.add(b5);
        b6.setText("Student by name part");
        buttons.add(b6);
        b7.setText("Subjects by student");
        buttons.add(b7);
        b8.setText("Students by subject");
        buttons.add(b8);
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

    private List<Subject> fetchAllSubjects() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/subject/allsubjects";
        ResponseEntity<List<Subject>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Subject>>() {}
        );
        return response.getBody();
    }

    private void actions() {
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewStudent ns = new NewStudent();
                ns.run();
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewSubject ns = new NewSubject();
                ns.run();
            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fetchAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No students yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                if (fetchAllSubjects().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No subjects yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    Enroll en = new Enroll();
                    en.run();
                }
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fetchAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No students yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    StudentsList sl = new StudentsList();
                    sl.run();
                }
            }
        });

        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fetchAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No students yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    StudentById sId = new StudentById();
                    sId.run();
                }
            }
        });

        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fetchAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No students yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    StudentByNamePart sNp = new StudentByNamePart();
                    sNp.run();
                }
            }
        });
    }
}