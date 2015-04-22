package org.vaadin.exampleapp.views.desktop;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vaadin.exampleapp.data.Person;
import org.vaadin.exampleapp.views.AbstractPersonView;
import org.vaadin.exampleapp.views.PersonPresenter;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@UIScope
@SpringView(name = "")
@SuppressWarnings("serial")
public class DesktopPersonView extends AbstractPersonView<VerticalSplitPanel>
		implements View, PropertyChangeListener {

	@Autowired
	@Qualifier("desktop")
	private PersonPresenter presenter;

	private final VerticalLayout formContent = new VerticalLayout();
	private final BasicDetailsForm basicDetailsForm = new BasicDetailsForm();
	private final AddressForm addressForm = new AddressForm();
	private final SkillsForm skillsForm = new SkillsForm();

	private TabSheet tabs;

	private Button addBtn = new Button("+", addButtonListener);
	private Button removeBtn = new Button("-", removeButtonListener);

	public DesktopPersonView() {
		super(new VerticalSplitPanel());
		VerticalLayout top = new VerticalLayout();
		top.setSizeFull();

		HorizontalLayout toolbar = new HorizontalLayout();

		toolbar.addComponent(addBtn);
		toolbar.addComponent(removeBtn);
		toolbar.setSpacing(true);

		top.addComponent(toolbar);
		top.addComponent(personsTable);
		top.setExpandRatio(personsTable, 1);
		addComponent(top);

		tabs = new TabSheet();
		basicDetailsForm.setCaption("Basic details");
		tabs.addComponent(basicDetailsForm);

		addressForm.setCaption("Address");
		tabs.addComponent(addressForm);

		skillsForm.setCaption("Skills");
		tabs.addComponent(skillsForm);

		tabs.setSizeFull();
		formContent.addComponent(tabs);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setMargin(true);
		buttonLayout.setSpacing(true);

		Button saveBtn = new Button("Save changes", saveButtonListener);
		buttonLayout.addComponent(saveBtn);

		Button cancelBtn = new Button("Discard changes", cancelButtonListener);
		buttonLayout.addComponent(cancelBtn);
		formContent.addComponent(buttonLayout);
		formContent.setVisible(false);
		formContent.setSizeFull();
		formContent.setExpandRatio(tabs, 1);

		getContent().setSplitPosition(100);
		addComponent(formContent);
	}

	@Override
	protected void nullSelection() {
		super.nullSelection();
		hideForm();
	}

	@Override
	protected void newPersonCreated(Person person) {
		super.newPersonCreated(person);
		showForm();
	}

	@Override
	protected void existingPersonSelected(Person person) {
		super.existingPersonSelected(person);
		showForm();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if ("availableSkills".equals(evt.getPropertyName())) {
			skillsForm.setAvailableSkills(model.getAvailableSkills());
		}
	}

	private void showForm() {
		getContent().setSplitPosition(50);
		formContent.setVisible(true);
		tabs.setSelectedTab(0);
	}

	private void hideForm() {
		formContent.setVisible(false);
		getContent().setSplitPosition(100);
	}

	@Override
	protected Object[] getVisibleColumns() {
		return new Object[] { "firstname", "lastname", "email", "title",
				"skills" };
	}

	@Override
	public void saveChanges() throws CommitException {
		clearTabCaptionsFromErrorIndicator();

		for (Field<?> f : binder.getFields()) {
			try {
				f.validate();
				f.removeStyleName("invalid-value");
			} catch (InvalidValueException e) {
				f.addStyleName("invalid-value");
				Tab tab = findTabForField(f);
				addErrorIndicatorToTabCaption(tab);
			}
		}

		binder.commit();
	}

	private Tab findTabForField(Field<?> f) {
		Tab tab = tabs.getTab(f.getParent());
		return tab;
	}

	private void addErrorIndicatorToTabCaption(Tab tab) {
		if (!tab.getCaption().contains("(!)")) {
			tab.setCaption(tab.getCaption() + " (!)");
		}
	}

	private void clearTabCaptionsFromErrorIndicator() {
		tabs.getTab(basicDetailsForm).setCaption(
				tabs.getTab(basicDetailsForm).getCaption().replace(" (!)", ""));
		tabs.getTab(addressForm).setCaption(
				tabs.getTab(addressForm).getCaption().replace(" (!)", ""));
	}

	@Override
	protected PersonPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected void bindLayouts() {
		binder.bindMemberFields(basicDetailsForm);
		binder.bindMemberFields(addressForm);
		binder.bindMemberFields(skillsForm);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().enter();
	}
}
