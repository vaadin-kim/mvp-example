package org.vaadin.exampleapp.views.mobile;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.exampleapp.views.PersonPresenter;

@SuppressWarnings("serial")
@Component("mobile")
@Scope("prototype")
public class MobilePersonPresenter extends PersonPresenter {

	public void editButtonClicked() {
		((MobilePersonView) view).showForm();
	}
	
	@Override
	public void addButtonClicked() {
		super.addButtonClicked();
		((MobilePersonView) view).showForm();
	}

}
