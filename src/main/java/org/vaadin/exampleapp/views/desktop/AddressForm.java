package org.vaadin.exampleapp.views.desktop;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

@SuppressWarnings("serial")
@DesignRoot
public class AddressForm extends FormLayout {

	@PropertyId("address.street")
	private TextField streetField;

	@PropertyId("address.zip")
	private TextField zipField;

	@PropertyId("address.city")
	private TextField cityField;

	public AddressForm() {
		Design.read(this);
		
		streetField.setNullRepresentation("");
		zipField.setNullRepresentation("");
		cityField.setNullRepresentation("");
		streetField.setValidationVisible(false);
		zipField.setValidationVisible(false);
		cityField.setValidationVisible(false);

		DependentValidator dependentValidator = new DependentValidator();
		dependentValidator.addDependentField(streetField);
		streetField.addValidator(dependentValidator);
		dependentValidator.addDependentField(zipField);
		zipField.addValidator(dependentValidator);
		dependentValidator.addDependentField(cityField);
		cityField.addValidator(dependentValidator);
	}

}
