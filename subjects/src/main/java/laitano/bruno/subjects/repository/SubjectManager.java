package laitano.bruno.subjects.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;

@Service 
public class SubjectManager implements SubjectRepository {
    private SubjectJpaItfRep repository;
    private List<String> subCodes;

    public SubjectManager(SubjectJpaItfRep repository) {
        this.repository = repository;
        this.subCodes = new ArrayList<>();
    }

    @Override
    public boolean add(String code, String name, String schedule, String classCode) {
        if (subCodes.contains(code)) {
            return false;
        }
        Subject subject = new Subject(code, name, schedule, classCode);
        subCodes.add(code);
        repository.save(subject);
        return true;
    }

    @Override
    public boolean addStudent(Student std, Subject sub) {
		if (!sub.getStudents().contains(std) && !std.getSubjects().contains(sub)) {
			sub.getStudents().add(std);
			repository.save(sub);
			return true;
		}
		return false;
    }

    @Override
    public Subject getSubjectId(String code) {
        List<Subject> subj = repository.findAll();
        for (Subject subject : subj) {
            if (subject.getCode().equalsIgnoreCase(code)) {
                return subject;
            }
        }
        return null;
    }

    @Override
    public boolean deleteSubject(String code) {
        List<Subject> subjects = repository.findAll();
        for (Subject s : subjects) {
			if (s.getCode().equalsIgnoreCase(code)) {
                subjects.remove(s);
				return true;
			}
		}
        return false;
    }

    @Override
    public List<Subject> getSubjects() {
        List<Subject> subj = repository.findAll();
        if (subj.size() == 0) {
            return new ArrayList<Subject>();
        }
        else {
            return subj.stream().toList();
        }    
    }

    @Override
    public List<Student> getStudentsByClass(String code) {
        List<Subject> subjects = repository.findAll();
        for (Subject s : subjects) {
			if (s.getCode().equalsIgnoreCase(code)) {
				return s.getStudents();
			}
		}
		return null;
    }
}