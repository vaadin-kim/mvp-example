package org.vaadin.exampleapp.views;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.backend.PersonService;
import org.vaadin.exampleapp.backend.SkillService;
import org.vaadin.exampleapp.data.Address;
import org.vaadin.exampleapp.data.Person;
import org.vaadin.exampleapp.data.Skill;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;

@SuppressWarnings("serial")
@Component("desktop")
@Scope("prototype")
public class PersonPresenter implements Serializable {

	protected PersonView view;
	protected PersonModel model = new PersonModel();

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SkillService skillService;

	public void setView(PersonView personView) {
		this.view = personView;
		view.setModel(model);
	}

	public void enter() {
		List<Person> persons = personService.getPersons();
		model.setPersons(persons);

		List<Skill> skills = skillService.getSkills();
		model.setAvailableSkills(skills);

	}

	public void personSelected(Person person) {
		model.setSelectedPerson(person);
	}

	public void cancelClicked() {
		view.discardChanges();
		model.setSelectedPerson(null);
	}

	public void saveClicked() {
		try {
			view.saveChanges();
		} catch (CommitException e) {
			return;
		}
		personService.savePerson(model.getSelectedPerson());

		model.setPersons(personService.getPersons());
		model.setSelectedPerson(null);
	}

	public void addButtonClicked() {
		Person person = new Person();
		person.setAddress(new Address());
		model.setSelectedPerson(person);
	}

	public void removeButtonClicked() {
		if (model.getSelectedPerson() != null) {
			personService.removePerson(model.getSelectedPerson());
			model.setPersons(personService.getPersons());
		}
	}

}
