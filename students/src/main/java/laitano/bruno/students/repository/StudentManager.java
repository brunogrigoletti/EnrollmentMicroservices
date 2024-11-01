package laitano.bruno.students.repository;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentManager implements StudentRepository {
	private StudentJpaItfRep repository;

    public StudentManager(StudentJpaItfRep repository) {
        this.repository = repository;
    }

	@Override
	public boolean add(Student std) {
		List<Student> stds = repository.findAll();
		for(int i=0; i<stds.size(); i++){
			if(stds.get(i).getRn().equalsIgnoreCase(std.getRn())) {
				return false;
			}
		}
		repository.save(std);
		return true;
	}

	@Override
	public String updateStudent(Student std) {
		List<Student> stds = repository.findAll();
		for(int i=0; i<stds.size(); i++)
		{
			Student stdn = stds.get(i);
			if(stdn.getRn().equals(std.getRn())) {
				stds.set(i, std);
				return "Update successful";
			}
		}
		return "Update un-successful";
	}

	@Override
	public String deleteStudent(String registrationNumber) {
		List<Student> stds = repository.findAll();
		for(int i=0; i<stds.size(); i++)
		{
			Student stdn = stds.get(i);
			if(stdn.getRn().equals(registrationNumber)){
				stds.remove(i);
				return "Delete successful";
			}
		}
		return "Delete un-successful";
	}

	@Override
	public List<Student> getStudents() {
		List<Student> stds = repository.findAll();
        if (stds.size() == 0) {
            return new ArrayList<Student>();
        }
        else {
            return stds.stream().toList();
        }
	}

	@Override
	public Student getStudentId(String regNum) {
		List<Student> stds = repository.findAll();
		for (Student s : stds) {
			if (s.getRn().equalsIgnoreCase(regNum)) {
				return s;
			}
		}
		return null;
	}

	@Override
	public List<Student> getStudentNamePart(String namePart) {
		List<Student> stds = repository.findAll();
		List<Student> matchingStudents = new ArrayList<>();
		for (Student s : stds) {
			if (s.getName().toLowerCase().contains(namePart)) {
				matchingStudents.add(s);
			}
		}
		return matchingStudents;
	}

	@Override
	public List<Subject> getSubjectsByStudent(String regNum) {
		List<Student> stds = repository.findAll();
		for (Student s : stds) {
			if (s.getRn().equalsIgnoreCase(regNum)) {
				return s.getSubjects();
			}
		}
		return null;
	}
}