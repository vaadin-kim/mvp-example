package org.vaadin.exampleapp.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.data.Person;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
@Component
@Scope("ui")
public abstract class AbstractPersonView<A extends com.vaadin.ui.Component>
		extends CustomComponent implements PersonView, PropertyChangeListener {

	protected FieldGroup binder = new FieldGroup();

	protected PersonModel model;

	protected final ClickListener saveButtonListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			getPresenter().saveClicked();
		}
	};

	protected final ClickListener cancelButtonListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			getPresenter().cancelClicked();
		}
	};

	protected final ClickListener addButtonListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			getPresenter().addButtonClicked();
		}
	};

	protected final ClickListener removeButtonListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			getPresenter().removeButtonClicked();
		}
	};

	public AbstractPersonView(A rootComponent) {
		setSizeFull();
		rootComponent.setSizeFull();
		setCompositionRoot(rootComponent);
		getPersonsTable().setSizeFull();
		getPersonsTable().setSelectable(true);
		getPersonsTable().addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				getPresenter().personSelected((Person) getPersonsTable().getValue());
			}
		});
	}

	@PostConstruct
	private void init() {
		getPresenter().setView(this);
		getPresenter().enter();
	}

	@SuppressWarnings("unchecked")
	protected A getContent() {
		return (A) getCompositionRoot();
	}

	protected abstract PersonPresenter getPresenter();

	protected Object[] getVisibleColumns() {
		return new Object[] { "firstname", "lastname", "title" };
	}

	protected abstract void bindLayouts();

	protected void addComponent(com.vaadin.ui.Component component) {
		((ComponentContainer) getCompositionRoot()).addComponent(component);
	}

	public void setModel(PersonModel model) {
		this.model = model;
		model.addPropertyChangeListener(this);
	}

	public void discardChanges() {
		binder.discard();
	}

	public void saveChanges() throws CommitException {
		binder.commit();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (PersonModel.PROPERTY_PERSONS.equals(evt.getPropertyName())) {
			viewDataUpdated();
		} else if (PersonModel.PROPERTY_SELECTED_PERSON.equals(evt
				.getPropertyName())) {
			if (evt.getNewValue() == null) {
				nullSelection();
				return;
			}

			if (!getPersonsTable().containsId(evt.getNewValue())) {
				newPersonCreated((Person) evt.getNewValue());
				return;
			}

			if (evt.getNewValue() != null) {
				existingPersonSelected((Person) evt.getNewValue());
			}
		}
	}

	protected void existingPersonSelected(Person person) {
		getPersonsTable().setValue(person);
		Item item = getPersonsTable().getItem(person);
		binder.setItemDataSource(item);
		bindLayouts();
	}

	protected void newPersonCreated(Person person) {
		getPersonsTable().setValue(null);
		BeanItem<Person> item = new BeanItem<Person>(person);
		item.addNestedProperty("address.street");
		item.addNestedProperty("address.zip");
		item.addNestedProperty("address.city");
		binder.setItemDataSource(item);
		bindLayouts();
	}

	protected void nullSelection() {
		getPersonsTable().setValue(null);
	}

	protected void viewDataUpdated() {
		BeanItemContainer<Person> container = new BeanItemContainer<Person>(
				Person.class);
		container.addNestedContainerBean("address");
		container.addAll(model.getPersons());
		getPersonsTable().setContainerDataSource(container);
		getPersonsTable().setVisibleColumns(getVisibleColumns());
	}

	protected abstract Table getPersonsTable();


}
