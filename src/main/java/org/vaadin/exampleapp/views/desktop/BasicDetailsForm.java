package org.vaadin.exampleapp.views.desktop;

import org.vaadin.exampleapp.data.Person;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

@DesignRoot
@SuppressWarnings("serial")
public class BasicDetailsForm extends FormLayout {

	@PropertyId("firstname")
	public  TextField firstnameField;

	@PropertyId("lastname")
	private  TextField lastnameField;

	@PropertyId("email")
	private  TextField emailField ;

	@PropertyId("title")
	private  ComboBox titleSelection;

	public BasicDetailsForm() {
		Design.read(this);
		firstnameField
				.addValidator(new BeanValidator(Person.class, "firstname"));
		lastnameField.addValidator(new BeanValidator(Person.class, "lastname"));
	}

}
