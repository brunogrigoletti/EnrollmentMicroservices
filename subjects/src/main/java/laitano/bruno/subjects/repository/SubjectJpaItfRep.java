package laitano.bruno.subjects.repository;

import laitano.bruno.entities.Subject;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SubjectJpaItfRep extends CrudRepository<Subject,String> {
    @SuppressWarnings("null")
    List<Subject> findAll();
}