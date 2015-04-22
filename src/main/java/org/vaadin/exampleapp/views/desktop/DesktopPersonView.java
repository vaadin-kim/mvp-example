package org.vaadin.exampleapp.views.desktop;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vaadin.exampleapp.data.Person;
import org.vaadin.exampleapp.views.AbstractPersonView;
import org.vaadin.exampleapp.views.PersonPresenter;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.declarative.Design;

@UIScope
@SpringView(name = "")
@SuppressWarnings("serial")
public class DesktopPersonView extends AbstractPersonView<VerticalSplitPanel>
		implements View, PropertyChangeListener {

	@Autowired
	@Qualifier("desktop")
	private PersonPresenter presenter;

	@DesignRoot("DesktopPersonViewLayout.html")
	public static class DesktopPersonViewDesign extends VerticalSplitPanel {
		Button addBtn;
		Button removeBtn;
		Button saveBtn;
		Button cancelBtn;
		VerticalLayout formContent;
		TabSheet tabs;
		BasicDetailsForm basicDetailsForm;
		AddressForm addressForm;
		SkillsForm skillsForm;
		Table personsTable;

		public DesktopPersonViewDesign() {
			Design.read(this);
		}

	}

	private DesktopPersonViewDesign design;

	public DesktopPersonView() {
		super(new DesktopPersonViewDesign());
		design = (DesktopPersonViewDesign) getContent();
		design.addBtn.addClickListener(addButtonListener);
		design.removeBtn.addClickListener(removeButtonListener);
		design.saveBtn.addClickListener(saveButtonListener);
		design.cancelBtn.addClickListener(cancelButtonListener);
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
			design.skillsForm.setAvailableSkills(model.getAvailableSkills());
		}
	}

	private void showForm() {
		getContent().setSplitPosition(50);
		design.formContent.setVisible(true);
		design.tabs.setSelectedTab(0);
	}

	private void hideForm() {
		design.formContent.setVisible(false);
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
		Tab tab = design.tabs.getTab(f.getParent());
		return tab;
	}

	private void addErrorIndicatorToTabCaption(Tab tab) {
		if (!tab.getCaption().contains("(!)")) {
			tab.setCaption(tab.getCaption() + " (!)");
		}
	}

	private void clearTabCaptionsFromErrorIndicator() {
		design.tabs.getTab(design.basicDetailsForm).setCaption(
				design.tabs.getTab(design.basicDetailsForm).getCaption()
						.replace(" (!)", ""));
		design.tabs.getTab(design.addressForm).setCaption(
				design.tabs.getTab(design.addressForm).getCaption()
						.replace(" (!)", ""));
	}

	@Override
	protected PersonPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected void bindLayouts() {
		binder.bindMemberFields(design.basicDetailsForm);
		binder.bindMemberFields(design.addressForm);
		binder.bindMemberFields(design.skillsForm);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().enter();
	}

	@Override
	protected Table getPersonsTable() {
		return ((DesktopPersonViewDesign) getContent()).personsTable;
	}
}
