package org.vaadin.exampleapp.views.desktop;

import org.vaadin.exampleapp.data.Person;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class BasicDetailsForm extends FormLayout {

	@PropertyId("firstname")
	public final TextField firstnameField = new TextField("First Name");

	@PropertyId("lastname")
	private final TextField lastnameField = new TextField("Last Name");

	@PropertyId("email")
	private final TextField emailField = new TextField("Email");

	@PropertyId("title")
	private final ComboBox titleSelection = new ComboBox("Title");

	public BasicDetailsForm() {
		setMargin(true);
		firstnameField.setNullRepresentation("");
		firstnameField.setRequired(true);
		firstnameField
				.addValidator(new BeanValidator(Person.class, "firstname"));
		firstnameField.setImmediate(true);
		firstnameField.setValidationVisible(false);

		lastnameField.setNullRepresentation("");
		lastnameField.setRequired(true);
		lastnameField.addValidator(new BeanValidator(Person.class, "lastname"));
		lastnameField.setValidationVisible(false);
		emailField.setNullRepresentation("");
		emailField.setValidationVisible(false);

		addComponent(firstnameField);
		addComponent(lastnameField);
		addComponent(emailField);
		addComponent(titleSelection);

		titleSelection.addItem("Architect");
		titleSelection.addItem("Developer");
		titleSelection.addItem("Project Manager");
		titleSelection.addItem("Quality Manager");
		titleSelection.addItem("Tester");
	}

}
