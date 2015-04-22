package org.vaadin.exampleapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.exampleapp.views.desktop.DesktopPersonView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

@SpringUI
@SuppressWarnings("serial")
@Theme("example")
public class ExampleUI extends UI {

	@Autowired
	private DesktopPersonView view;

	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addView("", view);
		// Placeholder
		setContent(new CssLayout());
	}

}
