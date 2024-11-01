package laitano.bruno.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Student implements Serializable {
	@Id
    private String rn;
    private String name;
    private String address;
	private String document;
	private Random random;
	private Set<String> generatedNumbers;
	@ManyToMany (mappedBy="students")
	private List<Subject> subjects;
	
	protected Student() {
	}
					
	public Student(String name, String address, String document) {
		this.random = new Random();
		this.generatedNumbers = new HashSet<>();
		this.rn = generateRn();
		this.name = name;
		this.address = address;
		this.document = document;
		this.subjects = new ArrayList<>();
	}

	private String generateRn() {
        String regNum;
        do {
            int randomNumber = random.nextInt(1_000_000);
            regNum = String.format("%06d", randomNumber);
        } while (generatedNumbers.contains(regNum));
        generatedNumbers.add(regNum);
        return regNum;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getRn() {
		return rn;
	}
	
	public void setRn(String rn) {
		this.rn = rn;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@JsonProperty("subjects")
    public List<String> getSubjectNames() {
        return subjects.stream()
                       .map(Subject::getName)
                       .collect(Collectors.toList());
    }

	@Override
	public String toString() {
		return "Student [rn=" + rn + ", name=" + name + ", address=" + address + ", document=" + document
				+ ", subjects=" + subjects + "]";
	}
}