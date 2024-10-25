package laitano.bruno.students.repository;

import laitano.bruno.entities.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StudentJpaItfRep extends CrudRepository<Student,String> {
    @SuppressWarnings("null")
    List<Student> findAll();
}