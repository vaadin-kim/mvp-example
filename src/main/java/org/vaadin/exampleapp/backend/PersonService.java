package org.vaadin.exampleapp.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.data.Person;

@Component
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public List<Person> getPersons() {
		return personRepository.findAll();
	}

	public void savePerson(Person person) {
		personRepository.save(person);
		personRepository.flush();
	}

	public void removePerson(Person person) {
		personRepository.delete(person);
	}

}
