package org.vaadin.exampleapp.views.desktop;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class AddressForm extends FormLayout {

	@PropertyId("address.street")
	private TextField streetField = new TextField("Street");

	@PropertyId("address.zip")
	private TextField zipField = new TextField("Zip");

	@PropertyId("address.city")
	private TextField cityField = new TextField("City");

	public AddressForm() {
		setMargin(true);

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

		addComponent(streetField);
		addComponent(zipField);
		addComponent(cityField);
	}

}
