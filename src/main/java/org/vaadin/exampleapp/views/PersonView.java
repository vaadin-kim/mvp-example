package org.vaadin.exampleapp.views;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;

public interface PersonView {

	public void setModel(PersonModel model);

	public void discardChanges();

	public void saveChanges() throws CommitException;

}
