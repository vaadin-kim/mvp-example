package org.vaadin.exampleapp.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@SuppressWarnings("serial")
@Entity
@Table(name = "person")
public class Person implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Length(min = 1, max = 10)
	@Column(name = "firstname")
	private String firstname;

	@NotNull
	@Length(min = 1, max = 10)
	@Column(name = "lastname")
	private String lastname;

	@Email
	@Column(name = "email")
	private String email;

	@Column(name = "title")
	private String title;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "person_skill", joinColumns = { @JoinColumn(table = "person", name = "person_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(table = "skill", name = "skill_id", referencedColumnName = "id") })
	private List<Skill> skills;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	public Person() {
	}

	public Person(int id, String firstname, String lastname, String email,
			String title) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
