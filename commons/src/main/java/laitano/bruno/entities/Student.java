package laitano.bruno.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rn")
public class Student implements Serializable {
	@Id
    private String rn;
    private String name;
    private String address;
	private String document;
	private Set<String> generatedNumbers = new HashSet<>();
	@ManyToMany (mappedBy="students")
	private List<Subject> subjects = new ArrayList<>();
	
	protected Student() {
	}
	
	public Student(String name, String address, String document) {
		this.name = name;
		this.address = address;
		this.document = document;
		this.rn = generateRn();
	}

	private String generateRn() {
        String regNum;
        do {
            int randomNumber = new Random().nextInt(1_000_000);
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

	@Override
	public String toString() {
		return "(" + rn + ") - " + name;
	}
}