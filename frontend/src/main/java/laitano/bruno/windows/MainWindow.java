package laitano.bruno.windows;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;

public class MainWindow {
    private JFrame window;
    private JPanel buttons;
    private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;

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
        this.b9 = new JButton();
        this.b10 = new JButton();
        this.b11 = new JButton();
        this.b12 = new JButton();
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
        buttons.setLayout(new GridLayout(6, 2, 10, 10));
        b1.setText("New student");
        buttons.add(b1);
        b2.setText("New subject");
        buttons.add(b2);
        b3.setText("Add student to subject");
        buttons.add(b3);
        b4.setText("Students list");
        buttons.add(b4);
        b5.setText("Subjects list");
        buttons.add(b5);
        b6.setText("Student by ID");
        buttons.add(b6);
        b7.setText("Student by name part");
        buttons.add(b7);
        b8.setText("Subjects by student");
        buttons.add(b8);
        b9.setText("Students by subject");
        buttons.add(b9);
        b10.setText("Load data");
        b10.setForeground(Color.WHITE);
        b10.setBackground(Color.GREEN);
        buttons.add(b10);
        b11.setText("Delete data");
        b11.setForeground(Color.WHITE);
        b11.setBackground(Color.RED);
        buttons.add(b11);
        b12.setText("Close");
        b12.setForeground(Color.WHITE);
        b12.setBackground(Color.darkGray);
        buttons.add(b12);
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

    private String registerStudent(Student student) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8081/student/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> request = new HttpEntity<>(student, headers);
        return restTemplate.postForObject(endpoint, request, String.class);
    }

    private String registerSubject(Subject subject) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8082/subject/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Subject> request = new HttpEntity<>(subject, headers);
        return restTemplate.postForObject(endpoint, request, String.class);
    }

    private String deleteAllStudents() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/student/deleteAll";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
            HttpEntity.EMPTY, String.class);
        return response.getBody();
    }

    private String deleteAllSubjects() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/subject/deleteAll";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE,
            HttpEntity.EMPTY, String.class);
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
                else if (fetchAllSubjects().isEmpty()) {
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
                if (fetchAllSubjects().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No subjects yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    SubjectsList sl = new SubjectsList();
                    sl.runAll();
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
                    StudentById sId = new StudentById();
                    sId.run();
                }
            }
        });

        b7.addActionListener(new ActionListener() {
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

        b8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fetchAllStudents().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No students yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    SubjectsByStudent sbs = new SubjectsByStudent();
                    sbs.run();
                }
            }
        });
        
        b9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        b10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student st1 = new Student("Bruno Laitano", "Rua Doutor Timoteo, 390", "02872118004");
                registerStudent(st1);
                Student st2 = new Student("Pietra Manzoli", "Avenida Osvaldo Gonçalves Cruz, 491", "7115220531");
                registerStudent(st2);
                Subject sb1 = new Subject("SUBJ101", "Matemática Básica", "A", "MB101");
                registerSubject(sb1);
                Subject sb2 = new Subject("SUBJ102", "Introdução à Física", "B", "IF102");
                registerSubject(sb2);
            }
        });

        b11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fetchAllStudents().isEmpty() && fetchAllSubjects().isEmpty()) {
                    JOptionPane.showMessageDialog(window, "No data yet!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    deleteAllStudents();
                    deleteAllSubjects();
                }
            }
        });

        b12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
    }
}