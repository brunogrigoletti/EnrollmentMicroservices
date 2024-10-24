package laitano.bruno.subjects.controller;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import laitano.bruno.subjects.repository.SubjectManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subject")
public class SubjectController {
	private SubjectManager sm;

	@Autowired
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
}