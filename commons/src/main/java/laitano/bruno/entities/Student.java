package laitano.bruno.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Student implements Serializable {
	@Id
    private String rn;
    private String name;
    private String address;
	@ManyToMany (mappedBy="students")
	private List<Subject> subjects;

	protected Student() {
    }
    
	public Student(String rn, String name, String address) {
		this.rn = rn;
		this.name = name;
		this.address = address;
		this.subjects = new ArrayList<>();
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
		return "Student [rn=" + rn + ", name=" + name + ", address=" + address  + "subjects=" + subjects + "]";
	}
}