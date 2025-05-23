package laitano.bruno.subjects.repository;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import java.util.List;

public interface SubjectRepository {
    boolean add(String code, String name, String schedule, String classCode);
    boolean addStudent(Student std, Subject sub);
    boolean deleteSubject(String code);
    List<Subject> getSubjects();
    Subject getSubjectCode(String id);
    List<String> getClassCodes();
    List<Student> getStudentsByClass(String classCode);
    void deleteAllSubjects();
}