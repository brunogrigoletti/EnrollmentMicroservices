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
import laitano.bruno.entities.Subject;

public class StudentsBySubject {
    private JFrame window;
    private JPanel fieldsPanel, buttonsPanel;
    private JButton bSearch, bCancel;
    private JComboBox<String> subjectsBox;

    public StudentsBySubject() {
        this.window = new JFrame();
        this.fieldsPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.bSearch = new JButton();
        this.bCancel = new JButton();
        this.subjectsBox = new JComboBox<>();
    }

    public void run() {
        setWindow();
        setFields();
        setButtons();
        setStudentBox();
        actions();
    }

    private void setWindow() {
        window.setName("Students by subject");
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
        fieldsPanel.add(new JLabel("Subject:"));
        fieldsPanel.add(subjectsBox);
    }

    private void setButtons() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bSearch.setText("Search");
        buttonsPanel.add(bSearch);
        bCancel.setText("Cancel");
        buttonsPanel.add(bCancel);
    }

    private void setStudentBox() {
        for (Subject s : fetchAllSubjects()) {
            subjectsBox.addItem(s.getCode());
        }
        subjectsBox.setSelectedIndex(-1);
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

    private Subject searchSubject(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8082/subject/subjectbycode/" + code;
        return restTemplate.getForObject(endpoint, Subject.class);
    }

    private void actions() {
        bSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (subjectsBox.getSelectedItem() != null){
                    StudentsList sl = new StudentsList();
                    Subject s = searchSubject(subjectsBox.getSelectedItem().toString());
                    if (!s.getStudents().isEmpty()){
                        sl.runBySubject(s);
                    }
                    else {
                        JOptionPane.showMessageDialog(window, "No students for this subject!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(window, "Choose a subject!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
                subjectsBox.setSelectedIndex(-1);
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