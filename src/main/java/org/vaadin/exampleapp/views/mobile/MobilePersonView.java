package org.vaadin.exampleapp.views.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.data.Person;
import org.vaadin.exampleapp.views.AbstractPersonView;
import org.vaadin.exampleapp.views.PersonPresenter;

import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope("ui")
public class MobilePersonView extends AbstractPersonView<CssLayout> {

	@Autowired
	private MobilePersonPresenter presenter;

	private VerticalLayout mainArea = new VerticalLayout();
	private Toolbar toolbar = new Toolbar();

	private MobileBasicDetailsForm basicDetailsForm = new MobileBasicDetailsForm(
			saveButtonListener, cancelButtonListener);

	public MobilePersonView() {
		super(new CssLayout());
		setCompositionRoot(mainArea);
		mainArea.setSizeFull();

		mainArea.addComponent(personsTable);
		mainArea.setExpandRatio(personsTable, 1);
		createMainToolbar();
		mainArea.addComponent(toolbar);
	}

	private void createMainToolbar() {
		toolbar = new Toolbar();

		Button addButton = new Button(FontAwesome.PLUS);
		addButton.addClickListener(addButtonListener);
		toolbar.addComponent(addButton);

		Button removeButton = new Button(FontAwesome.MINUS);
		removeButton.addClickListener(removeButtonListener);
		toolbar.addComponent(removeButton);

		Button editButton = new Button(FontAwesome.PENCIL);
		editButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.editButtonClicked();
			}
		});
		toolbar.addComponent(editButton);
	}

	@Override
	protected void nullSelection() {
		super.nullSelection();
		setCompositionRoot(mainArea);
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

	public void showForm() {
		setCompositionRoot(basicDetailsForm);
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
