package laitano.bruno.students.controller;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import laitano.bruno.students.repository.StudentManager;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/student")
public class StudentController {
	private StudentManager stds;
	//private SubjectManager sm;

	@Autowired
    public StudentController(StudentManager stds) {
        this.stds=stds;
		//this.sm=sm;
    }

	@GetMapping("/allstudent")
	public List<Student> getAllStudents() {
		return stds.getStudents();
	}

	@GetMapping("/studentbyid/{regNum}")
	public Student getSudentById(@PathVariable("regNum") String regNum) {
		return stds.getStudentId(regNum);
	}

	@GetMapping("/studentbynamepart/{namePart}")
	public List<Student> getStudentByNamePart(@PathVariable("namePart") String namePart) {
		return stds.getStudentNamePart(namePart);
	}

	@GetMapping("/subjects/{regNum}")
	public List<Subject> getSubjectByStudent(@PathVariable("regNum") String regNum) {
		return stds.getSubjectsByStudent(regNum);
	}

	@PutMapping("/update")
	public String updateStudentRecord(@RequestBody Student stdn) {
		return stds.updateStudent(stdn);
	}
	
	@PostMapping("/register")
	public String registerStudent(@RequestBody Student student) {
		if (stds.add(student))
			return "Successful";
		else
			return "Unsuccessful";
	}

	/* @PostMapping("/register/subject")
	public String registerStudentSubject(@RequestBody Map<String, String> request) {
		String studentId = request.get("studentId");
		String subjectId = request.get("subjectId");
		Student student = stds.getStudentId(studentId);
		Subject subject = sm.getSubjectId(subjectId);
		if (sm.addStudent(student, subject))
			return "Successful";
		else
			return "Unsuccessful";
	} */

	@DeleteMapping("/delete/{regdNum}")
	public String deleteStudentRecord(@PathVariable("regdNum") String regdNum) {
		return stds.deleteStudent(regdNum);
	}
}