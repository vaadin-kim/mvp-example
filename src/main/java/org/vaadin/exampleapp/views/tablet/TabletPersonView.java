package org.vaadin.exampleapp.views.tablet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.data.Person;
import org.vaadin.exampleapp.views.AbstractPersonView;
import org.vaadin.exampleapp.views.PersonPresenter;
import org.vaadin.exampleapp.views.mobile.MobileBasicDetailsForm;

import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope("ui")
public class TabletPersonView extends AbstractPersonView<HorizontalLayout> {

	@Autowired
	@Qualifier("desktop")
	private PersonPresenter presenter;

	private Toolbar toolbar;

	private MobileBasicDetailsForm basicDetailsForm = new MobileBasicDetailsForm(
			saveButtonListener, cancelButtonListener);

	private VerticalLayout mainContent;

	public TabletPersonView() {
		super(new HorizontalLayout());
		mainContent = new VerticalLayout();
		mainContent.addComponent(personsTable);
		mainContent.setExpandRatio(personsTable, 1);

		createMainToolbar();
		mainContent.addComponent(toolbar);
		mainContent.setSizeFull();
		addComponent(mainContent);

		basicDetailsForm.setVisible(false);
		addComponent(basicDetailsForm);
	}

	private void createMainToolbar() {
		toolbar = new Toolbar();

		Button addButton = new Button(FontAwesome.PLUS);
		addButton.addClickListener(addButtonListener);
		toolbar.addComponent(addButton);

		Button removeButton = new Button(FontAwesome.MINUS);
		removeButton.addClickListener(removeButtonListener);
		toolbar.addComponent(removeButton);
	}

	@Override
	protected void nullSelection() {
		super.nullSelection();
		basicDetailsForm.setVisible(false);
	}

	@Override
	protected void existingPersonSelected(Person person) {
		super.existingPersonSelected(person);
		basicDetailsForm.setVisible(true);
		basicDetailsForm.setName(person.getFirstname() + " "
				+ person.getLastname());
	}

	@Override
	protected void newPersonCreated(Person person) {
		super.newPersonCreated(person);
		basicDetailsForm.setVisible(true);
		basicDetailsForm.setName("");
	}

	@Override
	protected PersonPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected void bindLayouts() {
		binder.bindMemberFields(basicDetailsForm);
	}

}
