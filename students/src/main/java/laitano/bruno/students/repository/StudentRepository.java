package laitano.bruno.students.repository;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import java.util.List;

public interface StudentRepository {
    boolean add(Student std);
    String updateStudent(Student std);
    String deleteStudent(String registrationNumber);
    List<Student> getStudents();
    Student getStudentId(String id);
    List<Student> getStudentNamePart(String namePart);
    List<Subject> getSubjectsByStudent(String regNum);
}