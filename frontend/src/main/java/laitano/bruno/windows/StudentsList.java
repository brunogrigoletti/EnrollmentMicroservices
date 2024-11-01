package laitano.bruno.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import laitano.bruno.entities.Student;

public class StudentsList {
    private JFrame window;
    private JPanel buttonPanel;
    private JButton bClose;
    private JList<Student> students;

    public StudentsList() {
        this.window = new JFrame("Object List Window");
        this.buttonPanel = new JPanel();
        this.bClose = new JButton();
        this.students = consumeStudent();
    }

    public void run() {
        setWindow();
        setList();
        setButton();
        actions();
    }

    private void setWindow() {
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(new JScrollPane(students), BorderLayout.CENTER);
        window.add(buttonPanel, BorderLayout.SOUTH);
        window.setVisible(true);
    }

    private void setList() {
        students.setVisibleRowCount(10);
    }

    private JList<Student> consumeStudent() { 
    RestTemplate restTemplate = new RestTemplate();
    String endpoint = "http://localhost:8081/allstudent";
    return restTemplate.exchange(
            endpoint,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<JList<Student>>() {}
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