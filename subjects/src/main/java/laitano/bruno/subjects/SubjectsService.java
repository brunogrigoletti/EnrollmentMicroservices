package laitano.bruno.subjects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan (basePackages = {"laitano.bruno.subjects","laitano.bruno.entities"})

public class SubjectsService {
	public static void main(String[] args) {
		SpringApplication.run(SubjectsService.class, args);
	}
}