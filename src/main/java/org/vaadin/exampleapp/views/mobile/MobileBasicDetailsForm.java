package org.vaadin.exampleapp.views.mobile;

import org.vaadin.exampleapp.data.Person;

import com.vaadin.addon.touchkit.ui.EmailField;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MobileBasicDetailsForm extends VerticalLayout {
	
	@PropertyId("firstname")
	public final TextField firstnameField = new TextField("First Name");

	@PropertyId("lastname")
	private final TextField lastnameField = new TextField("Last Name");

	@PropertyId("email")
	private final EmailField emailField = new EmailField("Email");

	@PropertyId("title")
	private final NativeSelect titleSelection = new NativeSelect("Title");
	
	private final VerticalLayout formLayout = new VerticalLayout();
	
	private final HorizontalLayout buttonsLayout = new HorizontalLayout();

	private Label nameLabel;
	
	public MobileBasicDetailsForm(ClickListener saveListener, ClickListener cancelListener) {
		setMargin(true);
		setWidth("100%");
		
		initializeActionButtons(saveListener,
				cancelListener);
		
		initializeFields();
		addComponent(buttonsLayout);
		addComponent(formLayout);
	}
	
	public void setName(String name) {
		nameLabel.setValue(name);		
	}

	private void initializeActionButtons(
			ClickListener saveListener, ClickListener cancelListener) {
		buttonsLayout.setWidth("100%");
		Button saveButton = new Button("Save", saveListener);
		buttonsLayout.addComponent(saveButton);
		nameLabel = new Label();
		nameLabel.setWidth(null);
		buttonsLayout.addComponent(nameLabel);
		Button cancelButton = new Button("Cancel", cancelListener);
		buttonsLayout.addComponent(cancelButton);
		buttonsLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(nameLabel, Alignment.MIDDLE_CENTER);
		buttonsLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
	}

	private void initializeFields() {
		firstnameField.setNullRepresentation("");
		firstnameField.setRequired(true);
		firstnameField
				.addValidator(new BeanValidator(Person.class, "firstname"));
		firstnameField.setImmediate(true);
		firstnameField.setWidth("100%");

		lastnameField.setNullRepresentation("");
		lastnameField.setRequired(true);
		lastnameField.addValidator(new BeanValidator(Person.class, "lastname"));
		lastnameField.setWidth("100%");
		
		emailField.setNullRepresentation("");
		emailField.setWidth("100%");
		
		titleSelection.setWidth("100%");

		formLayout.addComponent(firstnameField);
		formLayout.addComponent(lastnameField);
		formLayout.addComponent(emailField);
		formLayout.addComponent(titleSelection);

		titleSelection.addItem("Architect");
		titleSelection.addItem("Developer");
		titleSelection.addItem("Project Manager");
		titleSelection.addItem("Quality Manager");
		titleSelection.addItem("Tester");
	}

}
