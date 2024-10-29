package laitano.bruno.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan (basePackages = {"laitano.bruno.students","laitano.bruno.entities"})
public class StudentsService {
	public static void main(String[] args) {
		SpringApplication.run(StudentsService.class, args);
	}
}