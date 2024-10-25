package laitano.bruno.subjects.controller;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import laitano.bruno.subjects.repository.SubjectManager;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/subject")
public class SubjectController {
	private SubjectManager sm;

    public SubjectController(SubjectManager sm) {
        this.sm=sm;
    }

	@PostMapping("/register")
	public String registerSubject(@RequestBody Subject subject) {
		if (sm.add(subject.getCode(), subject.getName(), subject.getSchedule(), subject.getCourse()))
			return "Successful";
		else
			return "Unsuccessful";
	}

	@GetMapping("/allsubjects")
	public List<Subject> getAllSubjects() {
		return sm.getSubjects();
	}

	@GetMapping("/student/{classCode}")
	public List<Student> getStudentsByClass(@PathVariable("classCode") String code) {
		return sm.getStudentsByClass(code);
	}

	private Student consumeStudent(String regNum) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "http://localhost:8081/studentbyid/{regNum}";
        return restTemplate.getForObject(endpoint, Student.class, regNum);
    }

	@PostMapping("/register/subject")
	public String registerStudentSubject(@RequestBody Map<String, String> request) {
		String studentId = request.get("studentId");
		String subjectId = request.get("subjectId");
		Student student = consumeStudent(studentId);
		Subject subject = sm.getSubjectId(subjectId);
		if (sm.addStudent(student, subject))
			return "Successful";
		else
			return "Unsuccessful";
	}
}