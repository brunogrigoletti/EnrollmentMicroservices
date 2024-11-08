package laitano.bruno.subjects.controller;

import laitano.bruno.entities.Student;
import laitano.bruno.entities.Subject;
import laitano.bruno.subjects.repository.SubjectManager;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			return "Successful!";
		else
			return "Unsuccessful!";
	}

	@GetMapping("/allsubjects")
	public List<Subject> getAllSubjects() {
		return sm.getSubjects();
	}

	@GetMapping("/subjectbycode/{code}")
	public Subject getSubjectByCode(@PathVariable("code") String code) {
		return sm.getSubjectCode(code);
	}

	@GetMapping("/allclasscodes")
	public List<String> getAllClassCodes() {
		return sm.getClassCodes();
	}

	@GetMapping("/student/{classCode}")
	public List<Student> getStudentsByClass(@PathVariable("classCode") String code) {
		return sm.getStudentsByClass(code);
	}

	@PostMapping("/enroll")
	public String registerStudentSubject(@RequestBody Map<String, Object> request) {
		ObjectMapper mapper = new ObjectMapper();
		Student student = mapper.convertValue(request.get("student"), Student.class);
		Subject subject = mapper.convertValue(request.get("subject"), Subject.class);
		if (sm.addStudent(student, subject))
			return "Successful!";
		else
			return "Unsuccessful!";
	}

	@DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllSubjects() {
        sm.deleteAllSubjects();
        return ResponseEntity.ok("Data deleted successfully!");
    }
}